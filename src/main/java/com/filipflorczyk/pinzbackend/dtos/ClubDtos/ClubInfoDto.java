package com.filipflorczyk.pinzbackend.dtos.ClubDtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ClubInfoDto {

    @NotBlank(message = "Club name cannot be blank")
    @Size(max = 30, message = "Maximal length of club name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Club name must consist of only alphabetic letters")
    private String name;
}
