package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.NewMatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.UpdateMatchDto;
import com.filipflorczyk.pinzbackend.entities.Match;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface MatchService extends BaseService<Match, MatchDto>{

    Page<MatchDto> getAllMatchesOfPlayer(Long id, Pageable pageable);
    Page<MatchDto> getAllMatchesOfLeague(Long id, Pageable pageable);
    Page<MatchDto> getAllMatchesOfClub(Long id, Pageable pageable);
    void addNewMatch(NewMatchDto newMatchDto);
    void updateMatch(Long id, UpdateMatchDto updateMatchDto);
    void cancelMatch(Long id);

}
