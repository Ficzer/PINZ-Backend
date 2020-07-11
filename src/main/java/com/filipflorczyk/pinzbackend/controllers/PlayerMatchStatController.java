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
import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.NewPlayerMatchStatDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.PlayerMatchStatDto;
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
public class PlayerMatchStatController {

    private PlayerMatchStatService playerMatchStatService;

    public PlayerMatchStatController(PlayerMatchStatService playerMatchStatService){
        this.playerMatchStatService = playerMatchStatService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/player-match-stats", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<PlayerMatchStat> spec = rootNode.accept(new CustomRsqlVisitor<>());
        Page<PlayerMatchStatDto> playerMatchStatDtos = playerMatchStatService.findAll(spec, pageable);

        return new ResponseEntity<>(playerMatchStatDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/player-match-stats")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<PlayerMatchStatDto> playerMatchStatDtos = playerMatchStatService.findAll(pageable);

        return new ResponseEntity<>(playerMatchStatDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/player-match-stats/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        PlayerMatchStatDto playerMatchStatDto = playerMatchStatService.findById(id);

        return new ResponseEntity(playerMatchStatDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/player-match-stats/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        playerMatchStatService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/matches/{id}/player-match-stats")
    public ResponseEntity<?> getAllStatsForMatch(@PathVariable Long id, Pageable pageable){

        Page<PlayerMatchStatDto> playerMatchStatDtos = playerMatchStatService.getAllStatsForMatch(id, pageable);

        return new ResponseEntity<>(playerMatchStatDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/players/{id}/player-match-stats")
    public ResponseEntity<?> getAllStatsForPlayer(@PathVariable Long id, Pageable pageable){

        Page<PlayerMatchStatDto> playerMatchStatDtos = playerMatchStatService.getAllStatsForPlayer(id, pageable);

        return new ResponseEntity<>(playerMatchStatDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/player-match-stats")
    public ResponseEntity<?> addPlayerMatchStat(@RequestBody @Valid NewPlayerMatchStatDto newPlayerMatchStatDto){

        playerMatchStatService.addPlayerMatchStat(newPlayerMatchStatDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "matches/player-match-stats/{id}")
    public ResponseEntity<?> deletePlayerMatchStat(@PathVariable Long id){

        playerMatchStatService.deletePlayerMatchStat(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

