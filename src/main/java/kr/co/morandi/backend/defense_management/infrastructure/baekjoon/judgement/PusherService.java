package kr.co.morandi.backend.defense_management.infrastructure.baekjoon.judgement;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class PusherService {

    private final Pusher pusher;

    public void subscribeJudgement(String channelName, BiConsumer<String, String> judgementCallback) {
        Channel channel = pusher.subscribe(channelName);
        channel.bind("update", pusherEvent ->
                judgementCallback.accept(pusherEvent.getChannelName(), pusherEvent.getData()));

    }
}