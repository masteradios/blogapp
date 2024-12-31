package com.example.blogApp.controllers;

import com.example.blogApp.config.AppConstants;
import com.example.blogApp.payloads.CommentDto;
import com.example.blogApp.payloads.CommentResponse;
import com.example.blogApp.services.comments.CommentService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment(
            @RequestParam(value = "comment") String comment,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "postId") String postId) {

        CommentDto commentDto = new CommentDto();
        commentDto.setComment(comment);
        CommentDto savedComment = commentService.createComment(userId, postId, commentDto);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(savedComment);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CommentResponse> getCommentsByUser(
            @PathVariable String userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        CommentResponse commentResponse = commentService.findAllByUser_Id(userId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(commentResponse);
    }

    @PostMapping("/update/user/{userId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable String userId,
            @PathVariable String commentId,
            @RequestParam(value = "comment", required = true) String comment) {
        CommentDto commentDto = commentService.updateComment(commentId, comment, userId);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<CommentResponse> getCommentByPost(
            @PathVariable String postId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        CommentResponse commentResponse = commentService.findAllByPost_PostId(postId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(commentResponse);
    }
}