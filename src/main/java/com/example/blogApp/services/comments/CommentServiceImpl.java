package com.example.blogApp.services.comments;

import com.example.blogApp.exceptions.ResourceNotFoundException;
import com.example.blogApp.models.CommentModel;
import com.example.blogApp.models.PostModel;
import com.example.blogApp.models.UserModel;
import com.example.blogApp.payloads.CommentDto;
import com.example.blogApp.payloads.CommentResponse;
import com.example.blogApp.repositories.CommentRepo;
import com.example.blogApp.repositories.PostRepo;
import com.example.blogApp.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.blogApp.config.AppConstants.pageSetting;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepo commentRepo;
    @Override
    public CommentDto createComment(String userId, String postId, CommentDto commentDto) {
        PostModel postModel = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found !!"));
        UserModel userModel = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        CommentModel commentModel=modelMapper.map(commentDto, CommentModel.class);
        commentModel.setCommentId(UUID.randomUUID().toString());
        commentModel.setDateAdded(new Date());
        commentModel.setUser(userModel);
        commentModel.setPost(postModel);
        CommentModel saved = commentRepo.save(commentModel);

        return modelMapper.map(saved, CommentDto.class);
    }

    @Override
    public CommentResponse findAllByUser_Id(String userId, int pageNumber, int pageSize, String sortBy, String sortDir) {


        Pageable pageable = pageSetting(pageNumber, pageSize, sortBy, sortDir);

        Page<CommentModel> pageResponse = commentRepo.findAllByUser_Id(userId, pageable);
        List<CommentModel> commentModels = pageResponse.getContent();

        List<CommentDto> commentDtos = commentModels.stream().map((commentModel) -> modelMapper.map(commentModel, CommentDto.class)).collect(Collectors.toList());
        if (commentDtos.isEmpty()){
            throw new ResourceNotFoundException("No comments found!!");
        }
        CommentResponse commentResponse=new CommentResponse();
        commentResponse.setComments(commentDtos);
        commentResponse.setCurrentPage(pageResponse.getNumber());
        commentResponse.setLastPage(pageResponse.isLast());
        commentResponse.setPageSize(pageResponse.getSize());
        commentResponse.setTotalElements(pageResponse.getTotalElements());
        commentResponse.setTotalPages(pageResponse.getTotalPages());

        return commentResponse;

    }

    @Override
    public CommentResponse findAllByPost_PostId(String postId,int pageNumber, int pageSize,String sortBy,String sortDir) {

        Pageable pageable = pageSetting(pageNumber, pageSize, sortBy, sortDir);

        Page<CommentModel> pageResponse = commentRepo.findAllByPost_PostId(postId, pageable);
        List<CommentModel> commentModels = pageResponse.getContent();
        List<CommentDto> commentDtos = commentModels.stream().map((commentModel) -> modelMapper.map(commentModel, CommentDto.class)).collect(Collectors.toList());

        if (commentDtos.isEmpty()){
            throw new ResourceNotFoundException("No Comments found !!");
        }

        CommentResponse commentResponse=new CommentResponse();

        commentResponse.setComments(commentDtos);
        commentResponse.setTotalPages(pageResponse.getTotalPages());
        commentResponse.setTotalElements(pageResponse.getTotalElements());
        commentResponse.setPageSize(pageResponse.getSize());
        commentResponse.setLastPage(pageResponse.isLast());
         commentResponse.setCurrentPage(pageResponse.getNumber());

        return commentResponse;
    }

    @Override
    public CommentDto updateComment(String commentId,String comment,String userId) {
        CommentModel commentModel = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found !!"));

        System.out.println("User is "+commentModel.getUser().getId());
        commentModel.setComment(comment);
        CommentModel saved = commentRepo.save(commentModel);
        return modelMapper.map(saved, CommentDto.class);
    }
}
