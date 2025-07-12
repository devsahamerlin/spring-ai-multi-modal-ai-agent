package com.merlin.agenticrag.springaiagenticai.web;

import com.merlin.agenticrag.springaiagenticai.records.CinModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultiModalController {
    private final ChatClient chatClient;
    @Value("classpath:/static/images/DIS-IBS-Swiss-ID-Front_0.png")
    private Resource image;

    @Value("classpath:/static/images/manuscrit.png")
    private Resource imageManuscrit;

    public MultiModalController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @GetMapping("/describe")
    public CinModel describeImage() {
        return chatClient.prompt()
                .system("Donne une une description de cet image")
                .user(u ->
                        u.text("Décrire cet image")
                                .media(MediaType.IMAGE_JPEG, image))
                .call()
                .entity(CinModel.class);
    }

    @PostMapping(value = "/askImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String askImage(String query, @RequestParam(name = "file") MultipartFile file) throws IOException {
        byte[] imageBytes = file.getBytes();
        return chatClient.prompt()
                .system("Répond a la queston de l'utilisateur a propos de l'image fourni, et tu reponds toujour en français")
                .user(u ->
                        u.text(query)
                                .media(MediaType.IMAGE_JPEG, new ByteArrayResource(imageBytes)))
                .call()
                .content();
    }
}
