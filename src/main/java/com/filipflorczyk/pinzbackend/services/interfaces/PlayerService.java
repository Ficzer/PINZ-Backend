package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.BooleanDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerInfoDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerStatsDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerService extends BaseService<Player, PlayerDto>{

    PlayerDto updatePlayerStats(Long id, PlayerDto playerDto);

    PlayerDto getMyPlayer();

    PlayerDto addMyPlayer(PlayerInfoDto playerDto);

    PlayerDto updateMyPlayerInformation(PlayerInfoDto playerDto);

    PlayerDto updatePlayerStats(Long id, PlayerStatsDto playerStatsDto);

    Page<PlayerDto> getPlayersOfGivenClub(Long id, Pageable pageable);

    Page<PlayerDto> getPlayersOfMyClub(Pageable pageable);

    PlayerDto makePlayerTrainer(Long id, BooleanDto booleanDto);
}
