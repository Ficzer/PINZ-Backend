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
@Table(name = "comments")
public class Comment extends BaseEntity {

    private String content;

    private LocalDateTime creationDateTime;

    private Integer stars;

    @ManyToMany
    private List<Player> playersWhoGiveStar;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player author;
}
