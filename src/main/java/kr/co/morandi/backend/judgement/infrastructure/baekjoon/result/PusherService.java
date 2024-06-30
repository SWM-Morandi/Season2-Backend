package kr.co.morandi.backend.judgement.infrastructure.baekjoon.result;

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
    /*
     * 채널에 대한 이벤트를 구독하는 로직
     * 채널에 대한 이벤트가 발생하면 콜백함수를 실행하도록 구현
     * BiConsumer를 이용하여 ChannelName과 Data를 파라미터로 받아서 콜백함수를 실행하도록 구현
     * pusher에서 bind를 수행하면, 내부의 Channel이 생기는 거고, WebSocket 연결이 추가되는 건 아닙니다.
     * main WebSocket 연결은 Pusher 객체를 생성할 때 생성되는 것이고, Channel은 그 안에서 필터링 역할을 합니다.
     * */
    public void subscribeJudgement(final String channelName,
                                   final Long submitId,
                                   final BiConsumer<Long, String> judgementCallback) {
        Channel channel = pusher.subscribe(channelName);
        // TODO : 끝난 채점은 Channel이 회수되지만, 너무 늦게 channel이 구독되어 아무 응답을 받지 못한 경우 여전히 회수되지 못하는 문제가 발생함
        channel.bind("update", pusherEvent ->
                judgementCallback.accept(submitId, pusherEvent.getData()));
    }
    public void unsubscribeJudgement(final String channelName) {
        pusher.unsubscribe(channelName);

    }
}