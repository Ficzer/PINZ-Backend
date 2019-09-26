package com.filipflorczyk.pinzbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clubs")
public class Club extends BaseEntity {

    @OneToMany(mappedBy = "club",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Player> players = new ArrayList<>();

    @OneToOne(mappedBy = "ownedClub",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Player trainer;

    @OneToMany(mappedBy = "homeClub",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Match> homeMatches;

    @OneToMany(mappedBy = "awayClub",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Match> awayMatches;

    @OneToMany(mappedBy = "match",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PlayerMatchStat> playerMatchStats;

    @OneToMany(mappedBy = "club",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Event> eventList;

    @OneToMany(mappedBy = "club",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Post> postList;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
}
