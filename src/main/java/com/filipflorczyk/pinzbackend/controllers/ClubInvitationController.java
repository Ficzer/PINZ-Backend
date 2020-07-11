package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.BooleanDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubInfoDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.NewClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.ClubInvitationDto;
import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.NewClubInvitationDto;
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubInvitationService;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
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
public class ClubInvitationController {

    private ClubInvitationService clubInvitationService;

    public ClubInvitationController(ClubInvitationService clubInvitationService){
        this.clubInvitationService = clubInvitationService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/club-invitations")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<ClubInvitationDto> clubInvitationDtoPage = clubInvitationService.findAll(pageable);

        return new ResponseEntity<>(clubInvitationDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/club-invitations/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        ClubInvitationDto clubInvitationDto = clubInvitationService.findById(id);

        return new ResponseEntity(clubInvitationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/club-invitations/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        clubInvitationService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/my-club/invitations")
    public ResponseEntity<?> getMyClubInvitations(Pageable pageable){

        return new ResponseEntity(clubInvitationService.getMyClubInvitations(pageable), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/clubs/my-club/invitations")
    public ResponseEntity<?> makeMyClubInvitation(@RequestBody @Valid NewClubInvitationDto newClubInvitationDto){

        clubInvitationService.makeInvitation(newClubInvitationDto);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/clubs/my-club/invitations/{id}")
    public ResponseEntity<?> removeMyClubInvitation(@PathVariable Long id){

        clubInvitationService.removeMyInvitation(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/users/me/player/invitations")
    public ResponseEntity<?> getMyInvitations(Pageable pageable){

        return new ResponseEntity(clubInvitationService.getMyInvitations(pageable), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/users/me/player/invitations/{id}")
    public ResponseEntity<?> executeMyInvitations(@PathVariable Long id, @RequestBody @Valid  BooleanDto booleanDto){

        clubInvitationService.executeMyInvitation(id, booleanDto.isValue());

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}

