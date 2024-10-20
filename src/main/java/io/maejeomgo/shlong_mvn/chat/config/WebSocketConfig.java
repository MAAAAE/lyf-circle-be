package io.maejeomgo.shlong_mvn.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 엔드포인트를 설정합니다.
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") // 필요한 도메인으로 제한하세요.
                .withSockJS();

        registry.addEndpoint("ws-chat-websocket")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커를 구성합니다.
        registry.enableSimpleBroker("/topic"); // 구독 경로
        registry.setApplicationDestinationPrefixes("/app"); // 송신 경로
    }
}
