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
@Table(name = "players")
public class Player extends BaseEntity {

    private String firstName;

    private String lastName;

    private String pseudonym;

    private LocalDateTime birthDate;

    private Integer height;

    @Enumerated(EnumType.STRING)
    private FieldPosition fieldPosition;

    private Integer goals;

    private Integer appearances;

    private Integer stars;

    private boolean isTrainer;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne
    @JoinColumn(name = "ownedClub_id")
    private Club ownedClub;

    @OneToMany(mappedBy = "player",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<PlayerMatchStat> playerMatchStats;

    @OneToMany(mappedBy = "senderPlayer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiverPlayer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Post> posts;

    @ManyToMany
    private List<Post> staredPosts;

    @ManyToMany
    private List<Comment> staredComments;

    @OneToMany(mappedBy = "author",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> comments;

    @OneToMany(mappedBy = "player",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ClubInvitation> clubInvitations;

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        if(obj instanceof Player){
            Player player = (Player) obj;
            return player.id.equals(this.id);
        }

        return false;
    }
}
