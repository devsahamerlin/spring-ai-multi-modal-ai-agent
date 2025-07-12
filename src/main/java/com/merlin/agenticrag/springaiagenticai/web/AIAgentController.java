package com.merlin.agenticrag.springaiagenticai.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AIAgentController {
    private final ChatClient chatClient;

    public AIAgentController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping("/chat")
    public String askLLM(String query) {
        List<Message> fewShotPromptExemple = List.of(
                new UserMessage("6+4"),
                new AssistantMessage("Le résultat est: 10")
        );
        return chatClient.prompt()
                .system("Répond toujours en majuscule")
                .user(query)
                .messages(fewShotPromptExemple)
                .call()
                .content();
    }
}
