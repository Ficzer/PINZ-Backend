package com.filipflorczyk.pinzbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "event_types")
public class EventType extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "eventType",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Event> events;
}
