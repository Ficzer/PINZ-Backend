package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.EventsDto.EventDto;
import com.filipflorczyk.pinzbackend.dtos.EventsDto.NewEventDto;
import com.filipflorczyk.pinzbackend.entities.*;
import com.filipflorczyk.pinzbackend.repositories.*;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EventServiceImpl extends BaseServiceImpl<EventRepository, Event, EventDto> implements EventService {

    UserRepository userRepository;
    PlayerRepository playerRepository;
    ClubRepository clubRepository;
    EventTypeRepository eventTypeRepository;

    public EventServiceImpl(EventRepository repository, UserRepository userRepository,
                           PlayerRepository playerRepository,
                           ClubRepository clubRepository,
                            EventTypeRepository eventTypeRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Post");
    }

    @Override
    public Event convertToEntity(EventDto eventDto) {

        Event event = modelMapper.map(eventDto, Event.class);

        return event;
    }

    @Override
    public EventDto convertToDto(Event event) {

        EventDto eventDto = modelMapper.map(event, EventDto.class);

        return eventDto;
    }

    @Override
    public Page<EventDto> getAllEventsForMyClub(Pageable pageable) {
        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getClub() == null)
            throw new EntityNotFoundException("Player is not in any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        return repository.findAllByClub_Id(club.getId(), pageable).map(this::convertToDto);
    }

    @Override
    public void addEvent(NewEventDto newEventDto) {

        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        EventType eventType = eventTypeRepository.findById(newEventDto.getEventTypeId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Event type with given id not found"));

        Event event = Event.builder()
                .club(club)
                .description(newEventDto.getDescription())
                .eventType(eventType)
                .date(newEventDto.getDate())
                .build();

        club.getEventList().add(event);
        eventType.getEvents().add(event);

        repository.save(event);

        clubRepository.save(club);
        eventTypeRepository.save(eventType);
    }

    @Override
    public void removeEvent(Long id) {

        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        Event event = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with given id not found"));

        if(event.getClub().getId() != club.getId())
            throw new IllegalArgumentException("You cannot remove event of not your club");

        repository.delete(event);
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
