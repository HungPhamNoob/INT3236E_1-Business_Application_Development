package com.example.demo.mapper;

import com.example.demo.dto.request.BlogCreationRequest;
import com.example.demo.dto.request.BlogUpdateRequest;
import com.example.demo.dto.response.BlogResponse;
import com.example.demo.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    Blog toBlog(BlogCreationRequest request);
    @Mapping(source = "author.username", target = "authorUsername")
    BlogResponse toBlogResponse(Blog blog);
    void updateBlog(@MappingTarget Blog blog, BlogUpdateRequest request);
}
