package com.example.intermediate.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponseDto {
    private String imageUrl;

    public ImageResponseDto(String imageUrl){
        this.imageUrl = imageUrl;
    }
}