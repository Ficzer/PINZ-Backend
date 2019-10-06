package com.filipflorczyk.pinzbackend.dtos.PlayerDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.FieldPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDto extends BaseDto {

    @NotBlank(message = "Player first name cannot be blank")
    @Size(max = 30, message = "Maximal length of player first name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Player first name must consist of only alphabetic letters")
    private String firstName;

    @NotBlank(message = "Player last name cannot be blank")
    @Size(max = 30, message = "Maximal length of player last name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Player last name must consist of only alphabetic letters")
    private String lastName;

    @NotBlank(message = "Player pseudonym cannot be blank")
    @Size(max = 30, message = "Maximal length of player pseudonym is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Player pseudonym must consist of only alphabetic letters")
    private String pseudonym;

    @Past(message = "Player birth date must be past")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime birthDate;

    @PositiveOrZero(message = "Player height must be positive or zero")
    private Integer height;

    @Enumerated(EnumType.STRING)
    private FieldPosition fieldPosition;

    @PositiveOrZero(message = "Player goals must be positive or zero")
    private Integer goals;

    @PositiveOrZero(message = "Player appearances must be positive or zero")
    private Integer appearances;

    @PositiveOrZero(message = "Player stars must be positive or zero")
    private Integer stars;

    private boolean isTrainer;

    private UserDto user;

    private ClubDto club;
}
