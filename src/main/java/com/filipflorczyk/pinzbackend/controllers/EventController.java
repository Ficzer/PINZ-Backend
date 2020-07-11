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
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import com.filipflorczyk.pinzbackend.entities.ClubStatistic;
import com.filipflorczyk.pinzbackend.entities.Event;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubInvitationService;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubStatisticService;
import com.filipflorczyk.pinzbackend.services.interfaces.EventService;
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
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/events", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<Event> spec = rootNode.accept(new CustomRsqlVisitor<>());
        Page<EventDto> eventDtoPage = eventService.findAll(spec, pageable);

        return new ResponseEntity<>(eventDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping(produces = { "application/json" }, path = "/events")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<EventDto> eventDtoPage = eventService.findAll(pageable);

        return new ResponseEntity<>(eventDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/events/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        EventDto eventDto = eventService.findById(id);

        return new ResponseEntity(eventDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/events/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        eventService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/my-club/events")
    public ResponseEntity<?> getAllEventsOfMyClub(Pageable pageable){

        Page<EventDto> eventDtoPage = eventService.getAllEventsForMyClub(pageable);

        return new ResponseEntity<>(eventDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = { "application/json" }, path = "/clubs/my-club/events")
    public ResponseEntity<?> addEventOfMyClub(@RequestBody @Valid NewEventDto newEventDto, Pageable pageable){

        eventService.addEvent(newEventDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/clubs/my-club/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id){

        eventService.removeEvent(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}

