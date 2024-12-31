package com.example.blogApp.controllers;

import com.example.blogApp.config.AppConstants;
import com.example.blogApp.config.FileType;
import com.example.blogApp.payloads.PostDto;
import com.example.blogApp.payloads.ApiResponse;
import com.example.blogApp.payloads.PostResponse;
import com.example.blogApp.services.files.AwsService;
import com.example.blogApp.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/post")

public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AwsService awsService;

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam (value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse postResponse = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(
            @RequestParam(value = "postTitle") String postTitle,
            @RequestParam(value = "postContent") String postContent,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "categoryId") Integer categoryId,
            @RequestParam(value = "image",required = false) MultipartFile image){

        PostDto postDto= new PostDto();
        postDto.setPostTitle(postTitle);
        postDto.setPostContent(postContent);

        postDto.setPostId(UUID.randomUUID().toString());

        postDto.setDateAdded(new Date());
        PostDto savedPost = postService.createPost(postDto,image,userId,categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable String postId){
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/postById/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId){
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId){
        postService.deletePost(postId);
        ApiResponse apiResponse=new ApiResponse("Deleted Successfully!!",true);
        return  ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable String userId){
        List<PostDto> postsByUser = postService.getAllPostsByUser(userId);
        return ResponseEntity.ok(postsByUser);}


    @GetMapping("/getByCategory/{categoryId}")
    public ResponseEntity<?> getAllByCategory(@PathVariable Integer categoryId){
        List<PostDto> postsByCategory = postService.getAllPostsByCategory(categoryId);

        return ResponseEntity.ok(postsByCategory);
    }

    @GetMapping("/searchByKeyword/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable String keyword){
        List<PostDto> postDtos = postService.searchUsingKeyword(keyword);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/image")
    public ResponseEntity<?> downloadImage(@RequestParam String fileName) {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = awsService.downloadFile(fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(FileType.fromFilename(fileName))
                .body(byteArrayOutputStream.toByteArray());
    }


}
