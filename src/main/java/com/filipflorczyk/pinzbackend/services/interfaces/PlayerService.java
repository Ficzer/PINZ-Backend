package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerInfoDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerStatsDto;
import com.filipflorczyk.pinzbackend.entities.Player;

public interface PlayerService extends BaseService<Player, PlayerDto>{

    PlayerDto updatePlayerStats(Long id, PlayerDto playerDto);

    PlayerDto getMyPlayer();

    PlayerDto addMyPlayer(PlayerDto playerDto);

    PlayerDto updateMyPlayerInformation(PlayerInfoDto playerDto);

    PlayerDto updatePlayerStats(Long id, PlayerStatsDto playerStatsDto);
}
