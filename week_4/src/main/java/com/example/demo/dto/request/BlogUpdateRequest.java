package com.example.demo.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateRequest {
    private String title;
    private String content;
}
