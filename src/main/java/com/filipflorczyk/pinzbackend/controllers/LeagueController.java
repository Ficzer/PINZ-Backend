package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.BooleanDto;
import com.filipflorczyk.pinzbackend.dtos.LeagueDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerInfoDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerStatsDto;
import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.League;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
import com.filipflorczyk.pinzbackend.services.interfaces.LeagueService;
import com.filipflorczyk.pinzbackend.services.interfaces.PlayerService;
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
public class LeagueController {

    LeagueService leagueService;

    public LeagueController(LeagueService leagueService){
        this.leagueService = leagueService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/leagues", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<League> spec = rootNode.accept(new CustomRsqlVisitor<League>());
        Page<LeagueDto> leagueDtoPage = leagueService.findAll(spec, pageable);

        return new ResponseEntity<>(leagueDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/leagues")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<LeagueDto> leagueDtoPage = leagueService.findAll(pageable);

        return new ResponseEntity<>(leagueDtoPage, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/leagues/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id){

        LeagueDto leagueDto = leagueService.findById(id);

        return new ResponseEntity(leagueDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/leagues/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        leagueService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
