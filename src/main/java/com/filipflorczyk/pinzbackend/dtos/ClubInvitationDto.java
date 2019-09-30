package com.filipflorczyk.pinzbackend.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClubInvitationDto extends BaseDto {

    @NotBlank(message = "Invitation description content cannot be blank")
    @Size(max = 10000, message = "Maximum size of invitation description is 10000 letters")
    private String description;

    private PlayerDto player;

    private ClubDto club;
}
