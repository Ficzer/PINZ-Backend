package com.filipflorczyk.pinzbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "player_match_stats")
public class PlayerMatchStat extends BaseEntity {

    private Integer goals;

    @ElementCollection
    private List<Integer> goalsMinutesInMatch;

    private Integer passes;

    private Integer yellowCards;

    @ElementCollection
    private List<Integer> yellowCardsMinutesInMatch;

    private Integer redCards;

    @ElementCollection
    private List<Integer> redCardsMinutesInMatch;

    private Integer fouls;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
}
