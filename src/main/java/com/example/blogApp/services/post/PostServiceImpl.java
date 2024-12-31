package com.example.blogApp.services.post;

import com.example.blogApp.exceptions.ResourceNotFoundException;
import com.example.blogApp.models.CategoryModel;
import com.example.blogApp.models.PostModel;
import com.example.blogApp.models.UserModel;
import com.example.blogApp.payloads.CategoryDto;
import com.example.blogApp.payloads.PostDto;
import com.example.blogApp.payloads.PostResponse;
import com.example.blogApp.payloads.UserDto;
import com.example.blogApp.repositories.CategoryRepo;
import com.example.blogApp.repositories.PostRepo;
import com.example.blogApp.repositories.UserRepo;
import com.example.blogApp.services.files.AwsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.blogApp.config.AppConstants.pageSetting;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private AwsService awsService;



    @Override
    public PostDto createPost(PostDto postDto, MultipartFile file,String userId,Integer categoryId) {



        UserModel user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        CategoryModel category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));

//        postDto.setUserDto(modelMapper.map(user, UserDto.class));
//        postDto.setCategoryDto(modelMapper.map(category, CategoryDto.class));

        if(file==null||file.isEmpty())
        {
            postDto.setPostImage("default.png");
        }
        else {
            String fileName= userId+"/"+StringUtils.cleanPath(file.getOriginalFilename());
            String contentType=file.getContentType();
            long fileSize=file.getSize();
            try {
                String uploadFileUrl = awsService.uploadFile(fileName, fileSize, contentType, file.getInputStream());
                postDto.setPostImage(uploadFileUrl);
                postDto.setImageContentType(contentType);
                postDto.setImageName(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        PostModel postModel = modelMapper.map(postDto, PostModel.class);

        postModel.setUser(user);
        postModel.setCategory(category);

        System.out.println(postModel.toString());

        PostModel savedPost = postRepo.save(postModel);
        System.out.println(savedPost);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto,String postId) {

        PostModel postModel = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with this ID not found !"));
        postModel.setPostTitle(postDto.getPostTitle());
        postModel.setPostImage(postDto.getPostImage());
        postModel.setPostContent(postDto.getPostContent());
        PostModel savedModel = postRepo.save(postModel);
        return modelMapper.map(savedModel, PostDto.class);
    }

    @Override
    public void deletePost(String postId) {
        PostModel postModel = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with this ID not found !"));
        postRepo.delete(postModel);

    }

    @Override
    public PostDto getPostById(String postId) {
        PostModel postModel = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with this ID not found !"));

        return modelMapper.map(postModel, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir) {

//        Sort sort=null;
//
//        if (sortDir.equalsIgnoreCase("desc")){
//            sort=Sort.by(sortBy).descending();
//        }else {
//            sort=Sort.by(sortBy).ascending();
//        }
//
//        Pageable pageable= PageRequest.of(pageNumber,pageSize, sort);


        Pageable pageable = pageSetting(pageNumber, pageSize, sortBy, sortDir);
        Page<PostModel> pageResponse = postRepo.findAll(pageable);
        List<PostModel> postList=pageResponse.getContent();

        if (postList.isEmpty()){
            throw new ResourceNotFoundException("Error getting Data");
        }

        List<PostDto> postDtoList = postList.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();

        postResponse.setPosts(postDtoList);
        postResponse.setCurrentPage(pageResponse.getNumber());
        postResponse.setTotalElements(pageResponse.getNumberOfElements());
        postResponse.setTotalPages(pageResponse.getTotalPages());
        postResponse.setLastPage(pageResponse.isLast());
        postResponse.setPageSize(pageResponse.getSize());


        return postResponse;
    }

    @Override
    public List<PostDto> getAllPostsByUser(String userId) {
        List<PostDto> postDtoList = postRepo.findAllByUser_Id(userId).stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        if (postDtoList.isEmpty()){
            throw new ResourceNotFoundException("No Posts by this User");
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Integer categoryId) {
        List<PostDto> postDtoList = postRepo.findAllByCategory_CategoryId(categoryId).stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        if (postDtoList.isEmpty()){
            throw new ResourceNotFoundException("No Posts in this Category");
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> searchUsingKeyword(String keyword) {
        List<PostDto> postDtoList = postRepo.findByPostTitleContainingIgnoreCase(keyword).stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        if (postDtoList.isEmpty()){
            throw new ResourceNotFoundException("No Posts Found");
        }

        return postDtoList;
    }
}
