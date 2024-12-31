package com.example.blogApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AppConstants {
    public static final String PAGE_NUMBER="0";
    public static final String PAGE_SIZE="5";
    public static final String SORT_BY="dateAdded";
    public static final String SORT_DIR="asc";


    public static Pageable pageSetting(int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort=null;

        if (sortDir.equalsIgnoreCase("desc")){
            sort=Sort.by(sortBy).descending();
        }else {
            sort=Sort.by(sortBy).ascending();
        }

        Pageable pageable= PageRequest.of(pageNumber,pageSize, sort);
        return pageable;
    }
}
