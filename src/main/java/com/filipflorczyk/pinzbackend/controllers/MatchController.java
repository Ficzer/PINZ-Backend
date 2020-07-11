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
public class MatchController {

    private MatchService matchService;

    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/matches", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<Match> spec = rootNode.accept(new CustomRsqlVisitor<>());
        Page<MatchDto> matchDtoPage = matchService.findAll(spec, pageable);

        return new ResponseEntity<>(matchDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/matches")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<MatchDto> matchDtoPage = matchService.findAll(pageable);

        return new ResponseEntity<>(matchDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/matches/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        MatchDto matchDtoPage = matchService.findById(id);

        return new ResponseEntity(matchDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/matches/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        matchService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/{id}/matches")
    public ResponseEntity<?> getAllMatchesOfClub(@PathVariable Long id, Pageable pageable){

        Page<MatchDto> matchDtoPage = matchService.getAllMatchesOfClub(id, pageable);

        return new ResponseEntity<>(matchDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/league/{id}/matches")
    public ResponseEntity<?> getAllMatchesOfLeague(@PathVariable Long id, Pageable pageable){

        Page<MatchDto> matchDtoPage = matchService.getAllMatchesOfLeague(id, pageable);

        return new ResponseEntity<>(matchDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/matches")
    public ResponseEntity<?> addMatch(@RequestBody @Valid NewMatchDto newMatchDto){

        matchService.addNewMatch(newMatchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/matches/{id}")
    public ResponseEntity<?> updateMatch(@PathVariable Long id, @RequestBody @Valid UpdateMatchDto updateMatchDto){

        matchService.updateMatch(id, updateMatchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/matches/{id}/is-canceled")
    public ResponseEntity<?> cancelMatch(@PathVariable Long id){

        matchService.cancelMatch(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

