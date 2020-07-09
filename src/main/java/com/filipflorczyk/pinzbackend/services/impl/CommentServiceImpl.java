package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.CommentDtos.CommentDto;
import com.filipflorczyk.pinzbackend.dtos.CommentDtos.NewCommentDto;
import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.NewPostDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.PostDto;
import com.filipflorczyk.pinzbackend.entities.*;
import com.filipflorczyk.pinzbackend.repositories.CommentRepository;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.PostRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.CommentService;
import com.filipflorczyk.pinzbackend.services.interfaces.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentRepository, Comment, CommentDto> implements CommentService {

    UserRepository userRepository;
    PostRepository postRepository;

    public CommentServiceImpl(CommentRepository repository, UserRepository userRepository, PostRepository postRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Comment");
    }

    @Override
    public Comment convertToEntity(CommentDto commentDto) {

        Comment comment = modelMapper.map(commentDto, Comment.class);

        return comment;
    }

    @Override
    public CommentDto convertToDto(Comment comment) {

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        return commentDto;
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if(userDetails instanceof UserPrincipal){
            username = ((UserPrincipal) userDetails).getUsername();
        }

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username not found"));
    }

    @Override
    public Page<CommentDto> getAllCommentsOfPost(Long id, Pageable pageable) {

        return repository.getAllByPost_Id(id, pageable).map(this::convertToDto);
    }

    @Override
    public CommentDto makeComment(NewCommentDto newCommentDto) {
        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Club club = user.getPlayer().getClub();

        if(club.getId() != newCommentDto.getClubId().getId())
            throw new IllegalArgumentException("Player is not in this club");

        Post post = postRepository.findById(newCommentDto.getPostId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Post with given id not found"));

        Comment comment = Comment.builder()
                .author(user.getPlayer())
                .content(newCommentDto.getContent())
                .creationDateTime(LocalDateTime.now())
                .post(post)
                .stars(0)
                .build();

        post.getCommentList().add(comment);

        CommentDto commentDto = convertToDto(repository.save(comment));

        postRepository.save(post);

        return commentDto;
    }

    @Override
    public CommentDto starComment(Long id, IdentificationDto identificationDto) {
        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Comment comment = repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id, "comment"));

        for (Player player: comment.getPlayersWhoGiveStar()
        ) {
            if (player.getId() == user.getPlayer().getId()) {
                throw new IllegalArgumentException("Player already stared this comment");
            }
        }

        comment.setStars(comment.getStars() + 1);
        comment.getPlayersWhoGiveStar().add(user.getPlayer());

        return convertToDto(repository.save(comment));
    }
}

