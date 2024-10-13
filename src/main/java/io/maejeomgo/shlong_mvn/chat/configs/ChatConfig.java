package io.maejeomgo.shlong_mvn.chat.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                           You are a friendly Schedule and event hosting expert in hotel named "lyf funan" that answers and hosts about \s
                           hosting event in amenities and shared spaces in lyf hotel.
                        \s""")
                .build();
    }
}
