package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.NewPlayerMatchStatDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.PlayerMatchStatDto;
import com.filipflorczyk.pinzbackend.entities.PlayerMatchStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerMatchStatService extends BaseService<PlayerMatchStat, PlayerMatchStatDto>{

    Page<PlayerMatchStatDto> getAllStatsForMatch(Long id, Pageable pageable);
    Page<PlayerMatchStatDto> getAllStatsForPlayer(Long id, Pageable pageable);
    void addPlayerMatchStat(NewPlayerMatchStatDto newPlayerMatchStatDto);
    void deletePlayerMatchStat(Long id);
}
