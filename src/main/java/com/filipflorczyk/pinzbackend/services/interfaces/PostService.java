package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.NewPostDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.PostDto;
import com.filipflorczyk.pinzbackend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService extends BaseService<Post, PostDto>  {

    Page<PostDto> getAllPostsForMyClub(Pageable pageable);
    void makePost(NewPostDto postDto);
    void starPost(Long id);
}
