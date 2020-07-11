package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.NewPostDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.PostDto;
import com.filipflorczyk.pinzbackend.entities.*;
import com.filipflorczyk.pinzbackend.repositories.ClubRepository;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.PostRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class PostServiceImpl extends BaseServiceImpl<PostRepository, Post, PostDto> implements PostService {

    UserRepository userRepository;
    PlayerRepository playerRepository;
    ClubRepository clubRepository;

    public PostServiceImpl(PostRepository repository, UserRepository userRepository,
                           PlayerRepository playerRepository,
                           ClubRepository clubRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Post");
    }

    @Override
    public Post convertToEntity(PostDto postDto) {

        Post post = modelMapper.map(postDto, Post.class);

        return post;
    }

    @Override
    public PostDto convertToDto(Post post) {

        PostDto postDto = modelMapper.map(post, PostDto.class);

        return postDto;
    }

    @Override
    public Page<PostDto> getAllPostsForMyClub(Pageable pageable) {
        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Club club = user.getPlayer().getClub();

        return repository.findAllByClub_Id(club.getId(), pageable).map(this::convertToDto);
    }

    @Override
    public void makePost(NewPostDto postDto) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Player player = user.getPlayer();

        Club club = user.getPlayer().getClub();

        if(club.getId() != postDto.getClubId().getId())
            throw new IllegalArgumentException("Player is not in this club");

        Post post = Post.builder()
                .author(user.getPlayer())
                .club(club)
                .content(postDto.getContent())
                .creationDateTime(LocalDateTime.now())
                .stars(0)
                .build();

        player.getPosts().add(post);
        club.getPostList().add(post);

        repository.save(post);

        playerRepository.save(player);
        clubRepository.save(club);
    }

    @Override
    public void starPost(Long id) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Post post = repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id, "post"));

        for (Player player: post.getPlayersWhoGiveStar()
        ) {
            if (player.getId() == user.getPlayer().getId()) {
                throw new IllegalArgumentException("Player already stared this post");
            }
        }

        post.setStars(post.getStars() + 1);
        post.getPlayersWhoGiveStar().add(user.getPlayer());

        repository.save(post);
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
}
