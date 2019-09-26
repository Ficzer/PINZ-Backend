package com.filipflorczyk.pinzbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne
    @JoinColumn(name = "ownedClub_id")
    private Club ownedClub;

    @OneToMany(mappedBy = "player",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PlayerMatchStat> playerMatchStats;

    @OneToMany(mappedBy = "senderPlayer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiverPlayer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> receivedMessages;
}
