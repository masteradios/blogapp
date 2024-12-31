package com.example.blogApp.services.post;

import com.example.blogApp.payloads.PostDto;
import com.example.blogApp.payloads.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, MultipartFile image,String userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,String postId);
    void deletePost(String postId);
    PostDto getPostById(String postId);
    PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir);

    List<PostDto> getAllPostsByUser(String userId);
    List<PostDto> getAllPostsByCategory(Integer categoryId);
    List<PostDto> searchUsingKeyword(String keyword);



}
