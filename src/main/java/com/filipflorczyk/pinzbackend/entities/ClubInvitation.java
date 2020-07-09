package com.filipflorczyk.pinzbackend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "club_invitations")
public class ClubInvitation extends BaseEntity {

    private String description;

    @ManyToOne()
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        if(obj instanceof ClubInvitation){
            ClubInvitation clubInvitation = (ClubInvitation)obj;
            return clubInvitation.id.equals(this.id);
        }

        return false;
    }
}
