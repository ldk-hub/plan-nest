package com.plan.nest.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {
    //Sinks.many().multicast() 를 활용해서 여러 사용자가 동시에 메세지 받을 수 있도록 구성
    private final Sinks.Many<String> chatSink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // 클라이언트로부터 수신된 메시지를 Sinks에 전달
        Mono<Void> input = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> {
                    log.info("Received: {}", message);
                    chatSink.tryEmitNext(message);
                })
                .then();

        // Sinks에서 데이터를 구독하여 클라이언트에 전송
        Flux<String> outputMessages = chatSink.asFlux().doOnCancel(() -> log.info("Client disconnected"));
        Mono<Void> output = session.send(outputMessages.map(session::textMessage));

        return Mono.zip(input, output).then();
    }
}
