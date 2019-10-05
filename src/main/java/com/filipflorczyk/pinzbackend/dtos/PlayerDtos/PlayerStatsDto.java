package com.filipflorczyk.pinzbackend.dtos.PlayerDtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
public class PlayerStatsDto {

    @PositiveOrZero(message = "Player goals must be positive or zero")
    private Integer goals;

    @PositiveOrZero(message = "Player appearances must be positive or zero")
    private Integer appearances;

    @PositiveOrZero(message = "Player stars must be positive or zero")
    private Integer stars;
}
