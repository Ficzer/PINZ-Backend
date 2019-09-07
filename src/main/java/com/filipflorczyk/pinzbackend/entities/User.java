package com.filipflorczyk.pinzbackend.entities;

import com.filipflorczyk.pinzbackend.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity
public class User extends BaseEntity {

    @NotBlank(message = "First name of user cannot be blank")
    @Length(max = 30, message = "Maximal length of user first name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name must consist of only alphabetic letters")
    private String firstName;

    @NotBlank(message = "First name of user cannot be blank")
    @Length(max = 30, message = "Maximal length of user first name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name must consist of only alphabetic letters")
    private String lastName;
}
