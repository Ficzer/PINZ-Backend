package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.CommentDtos.CommentDto;
import com.filipflorczyk.pinzbackend.dtos.CommentDtos.NewCommentDto;
import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.NewPostDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.PostDto;
import com.filipflorczyk.pinzbackend.services.interfaces.CommentService;
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
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/comments")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<CommentDto> commentDtoPage = commentService.findAll(pageable);

        return new ResponseEntity<>(commentDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/comment/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        CommentDto commentDto = commentService.findById(id);

        return new ResponseEntity(commentDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/comment/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        commentService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/my-club//posts/{id}/comments")
    public ResponseEntity<?> getAllCommentsOfPost(@PathVariable Long id, Pageable pageable){

        Page<CommentDto> commentDtoPage = commentService.getAllCommentsOfPost(id, pageable);

        return new ResponseEntity<>(commentDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/clubs/my-club/posts/comments")
    public ResponseEntity<?> addComment(@RequestBody @Valid NewCommentDto newCommentDto){

        commentService.makeComment(newCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/clubs/my-club/posts/{id}/comments/{commentId}")
    public ResponseEntity<?> starPost(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId){
        IdentificationDto identificationDto = new IdentificationDto();
        identificationDto.setId(commentId);

        commentService.starComment(id, identificationDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

