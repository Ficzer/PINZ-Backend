package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRoleRepository;
import com.filipflorczyk.pinzbackend.security.UserDetailsServiceImpl;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.PlayerService;
import com.filipflorczyk.pinzbackend.services.interfaces.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
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
    public PlayerDto getMyPlayer() {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        return convertToDto(user.getPlayer());
    }

    @Override
    public PlayerDto addMyPlayer(PlayerDto playerDto) {

        User user = getCurrentUser();

        if (user.getPlayer() != null)
            throw new IllegalArgumentException("User has already added his player");

        Player player = new Player();
        player.setGoals(0);
        player.setAppearances(0);
        player.setBirthDate(playerDto.getBirthDate());
        player.setFieldPosition(playerDto.getFieldPosition());
        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        player.setHeight(playerDto.getHeight());
        player.setPseudonym(player.getPseudonym());
        player.setStars(0);

        user.setPlayer(player);
        repository.save(player);
        userRepository.save(user);

        return playerDto;
    }

    @Override
    public PlayerDto updateMyPlayerInformation(PlayerDto playerDto) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        Player myPlayer = user.getPlayer();

        myPlayer.setFirstName(playerDto.getFirstName());
        myPlayer.setLastName(playerDto.getLastName());
        myPlayer.setPseudonym(playerDto.getPseudonym());
        myPlayer.setHeight(playerDto.getHeight());
        myPlayer.setBirthDate(playerDto.getBirthDate());
        myPlayer.setFieldPosition(playerDto.getFieldPosition());

        repository.save(myPlayer);

        return convertToDto(myPlayer);
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        String username = "";

        if(userDetails instanceof UserPrincipal){
            username = ((UserPrincipal) userDetails).getUsername();
        }

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username not found"));
    }
}
