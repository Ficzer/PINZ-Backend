package com.filipflorczyk.pinzbackend.dtos.ClubDtos;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewClubAddedDto  extends BaseDto {

    @NotBlank(message = "Club name cannot be blank")
    @Size(max = 30, message = "Maximal length of club name is 30 letters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Club name must consist of only alphabetic letters")
    private String name;

    private List<PlayerDto> players = new ArrayList<>();

    private List<ClubStatisticDto> clubStatistics;
}
