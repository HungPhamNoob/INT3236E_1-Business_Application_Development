package com.example.demo.controller;

import com.example.demo.dto.request.BlogCreationRequest;
import com.example.demo.dto.request.BlogUpdateRequest;
import com.example.demo.dto.response.BlogResponse;
import com.example.demo.service.BlogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogController {
    BlogService blogService;

    @PostMapping
    public BlogResponse createBlog(@RequestBody BlogCreationRequest request) {
        return blogService.createBlog(request);
    }

    @GetMapping
    public List<BlogResponse> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/{id}")
    public BlogResponse getBlog(@PathVariable String id) {
        return blogService.getBlog(id);
    }

    @PutMapping("/{id}")
    public BlogResponse updateBlog(@PathVariable String id, @RequestBody BlogUpdateRequest request) {
        return blogService.updateBlog(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
        return "Blog deleted";
    }
}
