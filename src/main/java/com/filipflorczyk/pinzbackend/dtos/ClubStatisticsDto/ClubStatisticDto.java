package com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.LeagueDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
public class ClubStatisticDto extends BaseDto {

    @PositiveOrZero(message = "Played matches number must be positive or zero")
    private Integer playedMatches;

    @PositiveOrZero(message = "Won matches number must be positive or zero")
    private Integer won;

    @PositiveOrZero(message = "Drawn matches number must be positive or zero")
    private Integer drawn;

    @PositiveOrZero(message = "Lost matches number must be positive or zero")
    private Integer lost;

    @PositiveOrZero(message = "Scored goals number must be positive or zero")
    private Integer goalsFor;

    @PositiveOrZero(message = "Lost goals number must be positive or zero")
    private Integer goalsAgainst;

    private Integer goalDifference;

    @PositiveOrZero(message = "Scored points number must be positive or zero")
    private Integer points;

    @NotNull(message = "League in club statistics cannot be null")
    private LeagueDto league;

    @NotNull(message = "Club in club statistics cannot be null")
    private ClubDto club;
}
