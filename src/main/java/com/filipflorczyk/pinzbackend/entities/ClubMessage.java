package com.filipflorczyk.pinzbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "club_messages")
public class ClubMessage extends BaseEntity {

    private String content;

    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "sender_player_id")
    private Player senderPlayer;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
