package cyber.data.regulation.controller;

import cyber.data.regulation.entity.AdjudicateVO;
import cyber.data.regulation.entity.Result;
import cyber.data.regulation.enums.AppKeyEnums;
import cyber.data.regulation.server.Http3Server;
import cyber.data.regulation.utils.ResultUtils;
import cyber.data.regulation.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;


@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ArrayList<String> sessionIds =  new ArrayList<>();

    @Autowired
    private Http3Server http3Server;

    //开始或者继续聊天
    @PostMapping("/adjudicate")
    public Result<String> adjudicate(@RequestBody AdjudicateVO adjudicateVO) throws IOException {
        String sessionId = adjudicateVO.getSessionId();
        String reply;
        if(sessionId == null || sessionId.isEmpty()) {
            sessionId = SessionUtils.createSessionId();
            sessionIds.add(sessionId);
            reply = http3Server.beginOrProceed(sessionId, adjudicateVO.getMessage(), AppKeyEnums.ADJUDICATE.getAppKey());
            return ResultUtils.success(reply);
        }
        reply = http3Server.beginOrProceed(sessionId, adjudicateVO.getMessage(), AppKeyEnums.ADJUDICATE.getAppKey());
        return ResultUtils.success(reply);
    }

    //清空聊天内容
    @PostMapping("/clear")
    public Result<Object> clear(String sessionId){
        return ResultUtils.result((Object)sessionIds.remove(sessionId));
    }

    @PostMapping("/similarPush")
    public void similarPush() {

    }

    @PostMapping("/supplyEvidence")
    public void supplyEvidence() {

    }

    @PostMapping("/computeAmount")
    public void computeAmount(){

    }

}
