package com.filipflorczyk.pinzbackend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "userRoles")
    private Set<User> users;
}
