package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.EventsDto.EventDto;
import com.filipflorczyk.pinzbackend.dtos.EventsDto.NewEventDto;
import com.filipflorczyk.pinzbackend.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService extends BaseService<Event, EventDto>   {

    Page<EventDto> getAllEventsForMyClub(Pageable pageable);
    void addEvent(NewEventDto newEventDto);
    void removeEvent(Long id);
}
