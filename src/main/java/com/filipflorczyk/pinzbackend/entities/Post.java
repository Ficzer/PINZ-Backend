package com.filipflorczyk.pinzbackend.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "posts")
public class Post extends BaseEntity {

    private String content;

    private LocalDateTime creationDateTime;

    private Integer stars;

    @ManyToMany
    private List<Player> playersWhoGiveStar;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player author;

    @OneToMany(mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> commentList;
}
