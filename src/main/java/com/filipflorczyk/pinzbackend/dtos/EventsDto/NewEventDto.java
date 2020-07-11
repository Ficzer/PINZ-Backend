package com.filipflorczyk.pinzbackend.dtos.EventsDto;

import com.filipflorczyk.pinzbackend.dtos.EventTypeDto;
import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewEventDto {

    @Size(max = 10000, message = "Maximum size of event description is 10000 letters")
    private String description;

    private LocalDateTime date;

    private IdentificationDto eventTypeId;
}
