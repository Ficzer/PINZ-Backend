package com.filipflorczyk.pinzbackend.dtos.MatchsDtos;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.LeagueDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.PlayerMatchStatDto;
import com.filipflorczyk.pinzbackend.dtos.StadiumDto;
import com.filipflorczyk.pinzbackend.entities.MatchResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MatchDto extends BaseDto {

    @NotNull(message = "Starting date time of match cannot be null")
    private LocalDateTime startDateTime;

    @NotNull(message = "Ending date time of match cannot be null")
    private LocalDateTime endDateTime;

    @NotNull(message = "Match result cannot be null")
    private MatchResult matchResult;

    @PositiveOrZero(message = "Home club goals number must be positive or zero")
    @Max(value = 1000, message = "Maximum number of goals cannot be more than 1000")
    private Integer homeClubGoals;

    @PositiveOrZero(message = "Away club goals number must be positive or zero")
    @Max(value = 1000, message = "Maximum number of goals cannot be more than 1000")
    private Integer awayClubGoals;

    @PositiveOrZero(message = "Home club possession percentage must be positive or zero")
    @Max(value = 100, message = "Maximum possession percentage cannot be more than 100")
    private Integer homeClubPossessionPercentage;

    @PositiveOrZero(message = "Away club possession percentage must be positive or zero")
    @Max(value = 100, message = "Maximum possession percentage cannot be more than 100")
    private Integer awayClubPossessionPercentage;

    @PositiveOrZero(message = "Home club shots on target number must be positive or zero")
    @Max(value = 10000, message = "Maximum number of shots on target cannot be more than 10000")
    private Integer homeClubShotsOnTarget;

    @PositiveOrZero(message = "Away club shots on target must be positive or zero")
    @Max(value = 10000, message = "Maximum number of shots on target cannot be more than 10000")
    private Integer awayClubShotsOnTarget;

    @PositiveOrZero(message = "Home club shots number must be positive or zero")
    @Max(value = 10000, message = "Maximum number of shots cannot be more than 10000")
    private Integer homeClubShots;

    @PositiveOrZero(message = "Away club shots number must be positive or zero")
    @Max(value = 10000, message = "Maximum number of shots cannot be more than 10000")
    private Integer awayClubShots;

    @PositiveOrZero(message = "Home club passes number must be positive or zero")
    @Max(value = 50000, message = "Maximum number of passes cannot be more than 50000")
    private Integer homePasses;

    @PositiveOrZero(message = "Away club passes number must be positive or zero")
    @Max(value = 50000, message = "Maximum number of passes cannot be more than 50000")
    private Integer awayPasses;

    @PositiveOrZero(message = "Home club corners number must be positive or zero")
    @Max(value = 100, message = "Maximum number of passes cannot be more than 100")
    private Integer homeCorners;

    @PositiveOrZero(message = "Away club corners number must be positive or zero")
    @Max(value = 100, message = "Maximum number of corners cannot be more than 100")
    private Integer awayCorners;

    @PositiveOrZero(message = "Home club offsides number must be positive or zero")
    @Max(value = 100, message = "Maximum number of offsides cannot be more than 100")
    private Integer homeOffsides;

    @PositiveOrZero(message = "Away club offsides number must be positive or zero")
    @Max(value = 100, message = "Maximum number of offsides cannot be more than 100")
    private Integer awayOffsides;

    @PositiveOrZero(message = "Home club yellow cards number must be positive or zero")
    @Max(value = 100, message = "Maximum number of yellow cards cannot be more than 100")
    private Integer homeYellowCards;

    @PositiveOrZero(message = "Away club yellow cards number must be positive or zero")
    @Max(value = 100, message = "Maximum number of yellow cards cannot be more than 100")
    private Integer awayYellowCards;

    @PositiveOrZero(message = "Home club red cards number must be positive or zero")
    @Max(value = 100, message = "Maximum number of red cards cannot be more than 100")
    private Integer homeRedCards;

    @PositiveOrZero(message = "Away club red cards number must be positive or zero")
    @Max(value = 100, message = "Maximum number of red cards cannot be more than 100")
    private Integer awayRedCards;

    @PositiveOrZero(message = "Home club fouls number must be positive or zero")
    @Max(value = 1000, message = "Maximum number of fouls cannot be more than 1000")
    private Integer homeFouls;

    @PositiveOrZero(message = "Away club fouls number must be positive or zero")
    @Max(value = 1000, message = "Maximum number of fouls cannot be more than 1000")
    private Integer awayFouls;

    private ClubDto homeClub;

    private ClubDto awayClub;

    @NotNull(message = "League cannot be null")
    private LeagueDto league;

    private StadiumDto stadium;

    private List<PlayerMatchStatDto> playerMatchStats;
}
