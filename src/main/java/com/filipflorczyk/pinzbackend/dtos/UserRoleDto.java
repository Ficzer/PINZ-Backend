package com.filipflorczyk.pinzbackend.dtos;

import com.filipflorczyk.pinzbackend.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class UserRoleDto extends BaseDto {

    @NotBlank(message = "Role name cannot be blank")
    private String name;
}
