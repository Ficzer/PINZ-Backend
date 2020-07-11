package com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto;

import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
public class NewClubStatisticDto {

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

    IdentificationDto leagueId;

    IdentificationDto clubId;
}
