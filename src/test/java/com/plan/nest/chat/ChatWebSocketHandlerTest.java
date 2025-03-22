package com.plan.nest.chat;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;

public class ChatWebSocketHandlerTest {
    private final ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();

    @Test
    void testWebSocketConnection() {
        String uri = "ws://localhost:8080/chat";

        Mono<Void> result = client.execute(
                URI.create(uri),
                session -> session.send(Mono.just(session.textMessage("Hello WebSocket")))
                        .thenMany(session.receive().map(WebSocketMessage::getPayloadAsText))
                        .doOnNext(msg -> System.out.println("Received from server"+msg))
                        .then()
        );

    }
}
