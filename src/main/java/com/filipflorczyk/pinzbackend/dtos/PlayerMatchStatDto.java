package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayerMatchStatDto extends ResourceSupport {

    @PositiveOrZero(message = "Player match goals must be positive or zero")
    private Integer goals;

    @PositiveOrZero(message = "Player minute of goals must be positive or zero") //TODO check if null passes
    private List<Integer> goalsMinutesInMatch;

    @PositiveOrZero(message = "Player passes in match must be positive or zero")
    private Integer passes;

    @PositiveOrZero(message = "Player yellow cards in match must be positive or zero")
    private Integer yellowCards;

    @PositiveOrZero(message = "Player minute of yellow cards must be positive or zero")
    private List<Integer> yellowCardsMinutesInMatch;

    @PositiveOrZero(message = "Player red cards in match must be positive or zero")
    private Integer redCards;

    @PositiveOrZero(message = "Player minute of red cards must be positive or zero")
    private List<Integer> redCardsMinutesInMatch;

    @PositiveOrZero(message = "Player fouls in match must be positive or zero")
    private Integer fouls;

    private PlayerDto player;

    private MatchDto match;
}
