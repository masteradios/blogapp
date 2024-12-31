package com.example.blogApp;

import com.example.blogApp.models.PostModel;
import com.example.blogApp.payloads.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    //    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper Modelmapper = new ModelMapper();
//        // Configure nested mappings
//        Modelmapper.typeMap(PostModel.class, PostDto.class).addMappings(mapper -> {
//            mapper.map(src -> src.getUser(), PostDto::setUserDto);
//            mapper.map(src -> src.getCategory(), PostDto::setCategoryDto);
//        });
//        return Modelmapper;
//    }

}
