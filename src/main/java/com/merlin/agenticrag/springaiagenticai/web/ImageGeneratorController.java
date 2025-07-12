package com.merlin.agenticrag.springaiagenticai.web;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGeneratorController {
    private final OpenAiImageModel openAiImageModel;

    public ImageGeneratorController(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    @GetMapping("/generateImage")
    public String generateImage(String prompt) {
        ImageOptions imageOptions = new OpenAiImageOptions.Builder()
                .quality("hd")
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
       return openAiImageModel.call(imagePrompt).getResult().getOutput().getUrl();
    }
}
