package com.merlin.agenticrag.springaiagenticai.web;

import com.merlin.agenticrag.springaiagenticai.records.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIAgentStructuredController {
    private final ChatClient chatClient;

    public AIAgentStructuredController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @GetMapping("/askAgent")
    public MovieList askLLM(String query) {
        String systemMessage = """
                Vous etes un spécialiste dans le domaine du cinéma.
                Répond a la question de l'utilisateur a ce propos
                """;
        return chatClient.prompt()
                .system(systemMessage )
                .user(query)
                .call()
                .entity(MovieList.class);
    }
}
