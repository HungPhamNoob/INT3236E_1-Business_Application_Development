package com.example.demo.service;


import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.entity.Blog;
import com.example.demo.entity.User;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.repository.BlogRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BlogResponse createBlog(BlogCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Blog blog = blogMapper.toBlog(request);
        blog.setAuthor(author);

        return blogMapper.toBlogResponse(blogRepository.save(blog));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<BlogResponse> getBlogs() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();

        if (userRole.equals("ROLE_ADMIN")) {
            return blogRepository.findAll().stream()
                    .map(blogMapper::toBlogResponse)
                    .collect(Collectors.toList());
        } else {
            return blogRepository.findByAuthorUsername(username).stream()
                    .map(blogMapper::toBlogResponse)
                    .collect(Collectors.toList());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BlogResponse getBlog(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();

        Blog blog;
        if (userRole.equals("ROLE_ADMIN")) {
            blog = blogRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Blog not found"));
        } else {
            blog = blogRepository.findByIdAndAuthorUsername(id, username)
                    .orElseThrow(() -> new RuntimeException("Blog not found or access denied"));
        }

        return blogMapper.toBlogResponse(blog);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BlogResponse updateBlog(String id, BlogUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();

        Blog blog;
        if (userRole.equals("ROLE_ADMIN")) {
            blog = blogRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Blog not found"));
        } else {
            blog = blogRepository.findByIdAndAuthorUsername(id, username)
                    .orElseThrow(() -> new RuntimeException("Blog not found or access denied"));
        }

        blogMapper.updateBlog(blog, request);
        return blogMapper.toBlogResponse(blogRepository.save(blog));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deleteBlog(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();

        if (userRole.equals("ROLE_ADMIN")) {
            blogRepository.deleteById(id);
        } else {
            Blog blog = blogRepository.findByIdAndAuthorUsername(id, username)
                    .orElseThrow(() -> new RuntimeException("Blog not found or access denied"));
            blogRepository.delete(blog);
        }
    }
}
