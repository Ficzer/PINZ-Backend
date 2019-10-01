package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRoleRepository;
import com.filipflorczyk.pinzbackend.services.interfaces.PlayerService;
import com.filipflorczyk.pinzbackend.services.interfaces.UserRoleService;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;

public class PlayerServiceImpl extends BaseServiceImpl<PlayerRepository, Player, PlayerDto> implements PlayerService {

    private UserRepository userRepository;

    public PlayerServiceImpl(PlayerRepository repository, UserRepository userRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Player");
    }

    @Override
    public Player convertToEntity(PlayerDto playerDto) {

        Player player = modelMapper.map(playerDto, Player.class);

        return player;
    }

    @Override
    public PlayerDto convertToDto(Player player) {

        PlayerDto playerDto = modelMapper.map(player, PlayerDto.class);

        return playerDto;
    }


    @Override
    public PlayerDto updatePlayerStats(Long id, PlayerDto playerDto) {

        Player player = repository.findById(id).orElseThrow(() -> entityNotFoundException(id, "Player"));
        player.setAppearances(playerDto.getAppearances());
        player.setGoals(player.getGoals());
        return convertToDto(repository.save(player));
    }

    @Override
    public PlayerDto addPlayerToMe(PlayerDto playerDto) {

        return null;
    }
}
