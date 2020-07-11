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
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import com.filipflorczyk.pinzbackend.entities.ClubStatistic;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubInvitationService;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubStatisticService;
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
public class ClubStatisticController {

    private ClubStatisticService clubStatisticService;

    public ClubStatisticController(ClubStatisticService clubStatisticService){
        this.clubStatisticService = clubStatisticService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/club-statistics", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<ClubStatistic> spec = rootNode.accept(new CustomRsqlVisitor<>());
        Page<ClubStatisticDto> clubStatisticDtoPage = clubStatisticService.findAll(spec, pageable);

        return new ResponseEntity<>(clubStatisticDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/club-statistics")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<ClubStatisticDto> clubStatisticDtoPage = clubStatisticService.findAll(pageable);

        return new ResponseEntity<>(clubStatisticDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/club-statistics/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        ClubStatisticDto clubStatisticDto = clubStatisticService.findById(id);

        return new ResponseEntity(clubStatisticDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/club-statistics/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        clubStatisticService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/leagues/{id}/club-statistics")
    public ResponseEntity<?> getAllClubStatsOfLeague(@PathVariable Long id, Pageable pageable){

        Page<ClubStatisticDto> clubStatisticDtoPage = clubStatisticService.getAllClubStatisticOfLeague(id, pageable);

        return new ResponseEntity<>(clubStatisticDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/club-statistics")
    public ResponseEntity<?> addClubStatistic(@RequestBody @Valid NewClubStatisticDto newClubStatisticDto){

        clubStatisticService.addNewClubStatistic(newClubStatisticDto);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/club-statistics/{id}")
    public ResponseEntity<?> updateClubStat(@PathVariable Long id, @RequestBody @Valid UpdateClubStatisticDto updateClubStatisticDto){

        clubStatisticService.updateClubStatistic(id, updateClubStatisticDto);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}

