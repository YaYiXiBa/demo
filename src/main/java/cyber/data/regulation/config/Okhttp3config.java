package cyber.data.regulation.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class Okhttp3config {

    //创建全局唯一客户端对象
    @Bean(name = "customOkHttpClient")
    public OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//连接超时(单位:秒)
                .readTimeout(60, TimeUnit.SECONDS)//读取超时(单位:秒)
                .writeTimeout(60, TimeUnit.SECONDS)//写入超时(单位:秒)
                .callTimeout(120, TimeUnit.SECONDS)//整个流程耗费的超时时间(单位:秒)--很少人使用
                .pingInterval(5, TimeUnit.SECONDS)//websocket轮训间隔(单位:秒)
                .build();
    }
}
