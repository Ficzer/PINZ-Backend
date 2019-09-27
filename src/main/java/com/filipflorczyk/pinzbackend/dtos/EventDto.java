package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {

    @Size(max = 10000, message = "Maximum size of event description is 10000 letters")
    private String description;

    @PastOrPresent(message = "Event creation time must be past or present")
    private LocalDateTime creationTime;

    @NotNull(message = "Club in event cannot be null")
    private ClubDto club;

    @NotNull(message = "Event type in event cannot be null")
    private EventTypeDto eventType;
}
