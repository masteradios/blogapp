package com.example.blogApp.repositories;

import com.example.blogApp.models.CommentModel;
import com.example.blogApp.payloads.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepo extends JpaRepository<CommentModel,String> {

   Page<CommentModel> findAllByUser_Id(String userId, Pageable pageable);
    Page<CommentModel> findAllByPost_PostId(String postId,Pageable pageable);
}
