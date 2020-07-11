package com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayerMatchStatDto extends BaseDto {

    private boolean isFromHomeClub;

    @PositiveOrZero(message = "Player match goals must be positive or zero")
    private Integer goals;


    @PositiveOrZero(message = "Player passes in match must be positive or zero")
    private Integer passes;

    @PositiveOrZero(message = "Player yellow cards in match must be positive or zero")
    private Integer yellowCards;

    @PositiveOrZero(message = "Player red cards in match must be positive or zero")
    private Integer redCards;

    @PositiveOrZero(message = "Player fouls in match must be positive or zero")
    private Integer fouls;

    private PlayerDto player;

    private MatchDto match;
}
