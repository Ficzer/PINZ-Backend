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
public class StadiumDto extends ResourceSupport {

    @NotBlank(message = "Stadium name cannot be blank")
    @Size(max = 30, message = "Maximal size of stadium name is 30 letters")
    private String name;

    @NotBlank(message = "Stadium address cannot be blank")
    @Size(max = 50, message = "Maximal size of stadium address is 50 letters")
    private String address;
}
