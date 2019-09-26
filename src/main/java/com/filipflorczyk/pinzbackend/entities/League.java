package com.filipflorczyk.pinzbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "leagues")
public class League extends BaseEntity {

    @OneToMany(mappedBy = "league",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Club> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "league",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Match> matches = new ArrayList<>();
}
