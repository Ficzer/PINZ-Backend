package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.BooleanDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubInfoDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.NewClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.ClubInvitationDto;
import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.NewClubInvitationDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.ClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.NewClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.UpdateClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.EventsDto.EventDto;
import com.filipflorczyk.pinzbackend.dtos.EventsDto.NewEventDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.NewMatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.UpdateMatchDto;
import com.filipflorczyk.pinzbackend.dtos.MessageDto.MessageDto;
import com.filipflorczyk.pinzbackend.dtos.MessageDto.NewMessageDto;
import com.filipflorczyk.pinzbackend.entities.*;
import com.filipflorczyk.pinzbackend.services.interfaces.*;
import com.filipflorczyk.pinzbackend.tools.rsql_parsers.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/messages", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<Message> spec = rootNode.accept(new CustomRsqlVisitor<>());
        Page<MessageDto> messageDtoPage = messageService.findAll(spec, pageable);

        return new ResponseEntity<>(messageDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/messages")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<MessageDto> messageDtos = messageService.findAll(pageable);

        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/messages/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        MessageDto messageDto = messageService.findById(id);

        return new ResponseEntity(messageDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/messages/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        messageService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/users/me/player/received-messages")
    public ResponseEntity<?> getAllMyReceivedMessages(Pageable pageable){

        Page<MessageDto> messageDtoPage = messageService.getAllMyReceivedMessages(pageable);

        return new ResponseEntity<>(messageDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/users/me/player/sent-messages")
    public ResponseEntity<?> getAllMySentMessages(Pageable pageable){

        Page<MessageDto> messageDtoPage = messageService.getAllMySentMessages(pageable);

        return new ResponseEntity<>(messageDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/users/player/{id}/messages")
    public ResponseEntity<?> getAllMyReceivedMessagesFromPlayer(@PathVariable Long id, Pageable pageable){

        Page<MessageDto> messageDtoPage = messageService.getAllMyReceivedMessagesFromPlayer(id, pageable);

        return new ResponseEntity<>(messageDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/users/me/player/messages")
    public ResponseEntity<?> sendMessage(@PathVariable Long id, @RequestBody @Valid NewMessageDto newMessageDto){

        messageService.sendMessage(newMessageDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/users/me/player/messages")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id){

        messageService.deleteMessage(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

