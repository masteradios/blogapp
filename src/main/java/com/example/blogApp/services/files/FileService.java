package com.example.blogApp.services.files;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(MultipartFile image);
    byte[] getImage(String postId);
}
