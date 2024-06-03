package kr.co.morandi.backend.judgement.infrastructure.config;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PusherConfig {

    @Value("${pusher.appId}")
    private String pusherAppId;
    @Bean
    public Pusher pusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher(pusherAppId, options);
        pusher.connect();
        return pusher;
    }
}
