package com.filipflorczyk.pinzbackend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.ClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LeagueDto extends BaseDto {

    @NotBlank(message = "League name cannot be blank")
    @Size(max = 50, message = "Maximal length of league name is 50 letters")
    private String name;

    @FutureOrPresent(message = "League start date cannot be past")
    private LocalDateTime startDateTime;

    @FutureOrPresent(message = "League end date cannot be past")
    private LocalDateTime endDateTime;

    @JsonIgnore
    private List<ClubStatisticDto> clubStatistics = new ArrayList<>();

    @JsonIgnore
    private List<MatchDto> matches = new ArrayList<>();
}
