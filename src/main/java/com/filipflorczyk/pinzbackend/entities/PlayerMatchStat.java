package com.filipflorczyk.pinzbackend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "player_match_stats")
public class PlayerMatchStat extends BaseEntity {

    private boolean isFromHomeClub;

    private Integer goals;

    private Integer passes;

    private Integer yellowCards;

    private Integer redCards;

    private Integer fouls;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
}
