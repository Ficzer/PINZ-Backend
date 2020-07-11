package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.PostDtos.NewPostDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.PostDto;
import com.filipflorczyk.pinzbackend.services.interfaces.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/posts")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<PostDto> postDtoPage = postService.findAll(pageable);

        return new ResponseEntity<>(postDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/posts/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        PostDto postDto = postService.findById(id);

        return new ResponseEntity(postDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/posts/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        postService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/my-club/posts")
    public ResponseEntity<?> getAllPostsOfMyClub(Pageable pageable){

        Page<PostDto> postDtos = postService.getAllPostsForMyClub(pageable);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/clubs/my-club/posts")
    public ResponseEntity<?> addPost(@RequestBody @Valid NewPostDto newPostDto){

        postService.makePost(newPostDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/clubs/my-club/posts/{id}/stars")
    public ResponseEntity<?> starPost(@PathVariable Long id){

        postService.starPost(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

