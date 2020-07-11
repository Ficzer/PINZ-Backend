package com.filipflorczyk.pinzbackend.dtos.EventsDto;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.EventTypeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDto extends BaseDto {

    @Size(max = 10000, message = "Maximum size of event description is 10000 letters")
    private String description;

    private LocalDateTime date;

    @NotNull(message = "Club in event cannot be null")
    private ClubDto club;

    @NotNull(message = "Event type in event cannot be null")
    private EventTypeDto eventType;
}
