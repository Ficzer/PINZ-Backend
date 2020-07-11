package com.filipflorczyk.pinzbackend.dtos.MatchsDtos;

import com.filipflorczyk.pinzbackend.dtos.*;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.entities.MatchResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewMatchDto {

    @NotNull(message = "Starting date time of match cannot be null")
    private LocalDateTime startDateTime;

    @NotNull(message = "Ending date time of match cannot be null")
    private LocalDateTime endDateTime;

    private IdentificationDto homeClubId;

    private IdentificationDto awayClubId;

    private IdentificationDto leagueId;

    private IdentificationDto stadiumId;

}
