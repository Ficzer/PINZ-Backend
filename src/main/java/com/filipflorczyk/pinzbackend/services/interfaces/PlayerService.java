package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.UserRole;

public interface PlayerService extends BaseService<Player, PlayerDto>{

    PlayerDto updatePlayerStats(Long id, PlayerDto playerDto);

    PlayerDto addPlayerToMe(PlayerDto playerDto);
}
