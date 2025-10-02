package com.example.demo.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogCreationRequest {
    private String title;
    private String content;
}
