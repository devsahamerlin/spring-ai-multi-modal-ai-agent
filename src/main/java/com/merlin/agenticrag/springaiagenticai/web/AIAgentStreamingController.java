package com.merlin.agenticrag.springaiagenticai.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AIAgentStreamingController {
    private final ChatClient chatClient;

    String systemMessage = """
                Vous etes un spécialiste dans le domaine du cinéma.
                Répond a la question de l'utilisateur a ce propos
                """;

    public AIAgentStreamingController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> streamLLM(String query) {
        return chatClient.prompt()
                .system(systemMessage )
                .user(query)
                .stream()
                .content();
    }

    @GetMapping("/nostream")
    public String noStreamLLM(String query) {
        return chatClient.prompt()
                .system(systemMessage )
                .user(query)
                .call()
                .content();
    }
}
