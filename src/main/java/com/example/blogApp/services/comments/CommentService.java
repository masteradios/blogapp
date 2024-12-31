package com.example.blogApp.services.comments;

import com.example.blogApp.payloads.CommentDto;
import com.example.blogApp.payloads.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentDto createComment(String userId,String postId,CommentDto commentDto);

    CommentResponse findAllByUser_Id(String userId, int pageNumber, int pageSize, String sortBy, String sortDir);
    CommentResponse findAllByPost_PostId(String postId,int pageNumber, int pageSize,String sortBy,String sortDir);
    CommentDto updateComment(String commentId,String comment,String userId);
}
