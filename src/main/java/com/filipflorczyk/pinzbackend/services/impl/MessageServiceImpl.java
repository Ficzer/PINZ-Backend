package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.MessageDto.MessageDto;
import com.filipflorczyk.pinzbackend.dtos.MessageDto.NewMessageDto;
import com.filipflorczyk.pinzbackend.entities.Match;
import com.filipflorczyk.pinzbackend.entities.Message;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.repositories.*;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.MatchService;
import com.filipflorczyk.pinzbackend.services.interfaces.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

public class MessageServiceImpl extends BaseServiceImpl<MessageRepository, Message, MessageDto> implements MessageService {

    PlayerRepository playerRepository;
    UserRepository userRepository;

    public MessageServiceImpl(MessageRepository repository, UserRepository userRepository,
                              PlayerRepository playerRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "League");
    }

    @Override
    public Message convertToEntity(MessageDto matchDto) {

        Message message = modelMapper.map(matchDto, Message.class);

        return message;
    }

    @Override
    public MessageDto convertToDto(Message message) {

        MessageDto messageDto = modelMapper.map(message, MessageDto.class);

        return messageDto;
    }


    @Override
    public Page<MessageDto> getAllMyReceivedMessages(Pageable pageable) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Player player = user.getPlayer();

        return repository.findAllByReceiverPlayer_Id(player.getId(), pageable).map(this::convertToDto);
    }

    @Override
    public Page<MessageDto> getAllMySentMessages(Pageable pageable) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Player player = user.getPlayer();

        return repository.findAllBySenderPlayer_Id(player.getId(), pageable).map(this::convertToDto);
    }

    @Override
    public Page<MessageDto> getAllMyReceivedMessagesFromPlayer(Long id, Pageable pageable) {
        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Player player = user.getPlayer();

        return repository.findAllByReceiverPlayerIdAndSenderPlayerId(player.getId(), id, pageable).map(this::convertToDto);
    }

    @Override
    public void sendMessage(NewMessageDto newMessageDto) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Player sender = user.getPlayer();

        Player receiver = playerRepository.findById(newMessageDto.getReceiverPlayer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver player with given id not found"));

        Message message = Message.builder()
                .content(newMessageDto.getContent())
                .creationDateTime(LocalDateTime.now())
                .senderPlayer(sender)
                .receiverPlayer(receiver)
                .build();

        sender.getSentMessages().add(message);
        receiver.getReceivedMessages().add(message);

        repository.save(message);
        playerRepository.save(sender);
        playerRepository.save(receiver);
    }

    @Override
    public void deleteMessage(Long id) {

        Message message = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message with given id not found"));

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if(user.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player does not have a club");

        Player receiver = user.getPlayer();

        if(message.getReceiverPlayer().getId() != receiver.getId())
            throw new IllegalArgumentException("You can delete only your messages");

        repository.delete(message);
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if(userDetails instanceof UserPrincipal){
            UserPrincipal userPrincipal = ((UserPrincipal) userDetails);
            username = ((UserPrincipal) userDetails).getUsername();
        }

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username not found"));
    }
}
