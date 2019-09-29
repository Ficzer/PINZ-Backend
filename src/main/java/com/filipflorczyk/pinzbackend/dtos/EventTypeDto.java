package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class EventTypeDto extends ResourceSupport {

    @NotBlank(message = "Event type name cannot be blank")
    @Size(max = 30, message = "Maximal size of event type name is 30 letters")
    private String name;
}
