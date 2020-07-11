package com.filipflorczyk.pinzbackend.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "matches")
public class Match extends BaseEntity {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private MatchResult matchResult;

    private Integer homeClubGoals;

    private Integer awayClubGoals;

    private Integer homeClubPossessionPercentage;

    private Integer awayClubPossessionPercentage;

    private Integer homeClubShotsOnTarget;

    private Integer awayClubShotsOnTarget;

    private Integer homeClubShots;

    private Integer awayClubShots;

    private Integer homePasses;

    private Integer awayPasses;

    private Integer homeCorners;

    private Integer awayCorners;

    private Integer homeOffsides;

    private Integer awayOffsides;

    private Integer homeYellowCards;

    private Integer awayYellowCards;

    private Integer homeRedCards;

    private Integer awayRedCards;

    private Integer homeFouls;

    private Integer awayFouls;

    private boolean isCanceled;

    @ManyToOne
    @JoinColumn(name = "home_club_id")
    private Club homeClub;

    @ManyToOne
    @JoinColumn(name = "away_club_id")
    private Club awayClub;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "match",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<PlayerMatchStat> playerMatchStats;
}
