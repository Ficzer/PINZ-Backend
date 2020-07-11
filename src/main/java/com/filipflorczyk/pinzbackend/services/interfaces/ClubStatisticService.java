package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.ClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.NewClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.UpdateClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.NewMatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.UpdateMatchDto;
import com.filipflorczyk.pinzbackend.entities.ClubStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubStatisticService extends BaseService<ClubStatistic, ClubStatisticDto>  {

    Page<ClubStatisticDto> getAllClubStatisticOfLeague(Long id, Pageable pageable);
    void addNewClubStatistic(NewClubStatisticDto newClubStatisticDto);
    void updateClubStatistic(Long id, UpdateClubStatisticDto updateClubStatisticDto);
}
