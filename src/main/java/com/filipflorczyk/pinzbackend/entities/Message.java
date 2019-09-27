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
@Table(name = "messages")
public class Message extends BaseEntity {

    private String content;

    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "receiver_player_id")
    private Player receiverPlayer;

    @ManyToOne
    @JoinColumn(name = "sender_player_id")
    private Player senderPlayer;

}
