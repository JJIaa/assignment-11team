package com.sparta.innovationcamp.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {
    private String title;
    private String content;
    private String url;
    private MultipartFile file;
}
