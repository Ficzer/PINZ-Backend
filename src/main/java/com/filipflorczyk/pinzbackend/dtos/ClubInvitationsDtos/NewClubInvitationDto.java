package com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos;

import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewClubInvitationDto {

    @NotBlank(message = "Invitation description content cannot be blank")
    @Size(max = 10000, message = "Maximum size of invitation description is 10000 letters")
    private String description;

    IdentificationDto playerId;
}
