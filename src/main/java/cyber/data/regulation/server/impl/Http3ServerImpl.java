package cyber.data.regulation.server.impl;

import cyber.data.regulation.server.Http3Server;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Log4j2
public class Http3ServerImpl implements Http3Server {
    @Autowired
    private OkHttpClient customOkHttpClient;

    private final String sseUrl = "https://wss.lke.cloud.tencent.com/v1/qbot/chat/sse"; // SSE 服务器的 URL
    private static String visitorBizId = "202403180001"; //   访客 ID（外部系统提供，需确认不同的访客使用不同的 ID）


    @Override
    public String beginOrProceed(String sessionId, String content, String appKey) {
        CompletableFuture<HashMap<String, String>> completableFuture = new CompletableFuture<>();
        HashMap<String, String> conversation = new HashMap<>();
        // 创建 JSON 请求体
        MediaType mediaType = MediaType.get("application/json");
        JSONObject reqBody = new JSONObject();
        try {
            reqBody.put("content", content);
            reqBody.put("bot_app_key", appKey);
            reqBody.put("visitor_biz_id", visitorBizId);
            reqBody.put("session_id", sessionId);
        } catch (Exception e) {
            log.error(e);
        }

        //创建请求
        Request request = new Request.Builder()
                .url(sseUrl)
                .post(RequestBody.create(reqBody.toString(),mediaType))
                .build();

        //制作监听器，监听流式response
        EventSourceListener listener = new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                System.out.println("SSE connection opened");
            }

            @Override
            public void onClosed(EventSource eventSource) {
                completableFuture.complete(conversation);
                System.out.println("SSE connection closed");
            }

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                if (type.equals("reply")) {
                    try {
                        JSONObject dataObj = new JSONObject(data);
                        JSONObject payload = dataObj.getJSONObject("payload");
                        if (payload.length() > 0) {
                            boolean isFromSelf = payload.getBoolean("is_from_self");
                            boolean isFinal = payload.getBoolean("is_final");
                            String content = payload.getString("content");
                            if (isFromSelf) {
                                conversation.put("is_from_self", content);
                                System.out.println("is_from_self, event: " + type + ", content: " + content);
                            } else if (isFinal){
                                conversation.put("model_reply", content);
                                System.out.println("is_final, event: " + type + ", content: " + content);
                            }
                        }
                        System.out.println("Received SSE reply: " + dataObj);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Received SSE event: " + type);
                }

            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                System.err.println("Error occurred: " + t.getMessage());
            }
        };
        Map<String, String> map = null;
        //基于customOkHttpClient创建事件工厂
        EventSource.Factory factory = EventSources.createFactory(customOkHttpClient);
        //开始监听
        factory.newEventSource(request, listener);
        try {
            map = completableFuture.get(30L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException | TimeoutException e) {
            log.error(e);
        }

        return map.toString();
    }


}
