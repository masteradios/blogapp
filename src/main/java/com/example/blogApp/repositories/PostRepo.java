package com.example.blogApp.repositories;

import com.example.blogApp.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  java.util.List;
@Repository
public interface PostRepo extends JpaRepository<PostModel,String> {

    List<PostModel> findAllByUser_Id(String userId);
    List<PostModel> findAllByCategory_CategoryId(Integer categoryId);
    List<PostModel> findByPostTitleContainingIgnoreCase(String keyword);

}
