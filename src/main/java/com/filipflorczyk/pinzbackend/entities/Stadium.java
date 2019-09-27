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
@Table(name = "stadium")
public class Stadium extends BaseEntity {

    private String name;

    private String address;

    @OneToMany(mappedBy = "stadium",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Match> matches = new ArrayList<>();
}
