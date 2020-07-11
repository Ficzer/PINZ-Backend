package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.CommentDtos.CommentDto;
import com.filipflorczyk.pinzbackend.dtos.CommentDtos.NewCommentDto;
import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService extends BaseService<Comment, CommentDto> {

    Page<CommentDto> getAllCommentsOfPost(Long id, Pageable pageable);
    void makeComment(NewCommentDto newCommentDto);
    void starComment(Long id, IdentificationDto identificationDto);
}
