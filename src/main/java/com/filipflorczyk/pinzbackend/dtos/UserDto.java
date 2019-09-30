package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserDto extends BaseDto {

    @NotBlank(message = "User name of user cannot be blank")
    @Length(max = 30, message = "Maximal length of user name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "User name must consist of only alphabetic letters")
    private String userName;

    public UserDto(){

    }

}
