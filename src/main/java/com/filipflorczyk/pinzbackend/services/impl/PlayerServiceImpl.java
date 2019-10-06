package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.BooleanDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerInfoDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerStatsDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.repositories.ClubRepository;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PlayerServiceImpl extends BaseServiceImpl<PlayerRepository, Player, PlayerDto> implements PlayerService {

    private UserRepository userRepository;
    private ClubRepository clubRepository;

    public PlayerServiceImpl(PlayerRepository repository, UserRepository userRepository, ClubRepository clubRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
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
    public PlayerDto addMyPlayer(PlayerInfoDto playerDto) {

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
        player.setUser(user);
        Player returnPlayer = repository.save(player);
        userRepository.save(user);

        return convertToDto(returnPlayer);
    }

    @Override
    public PlayerDto updateMyPlayerInformation(PlayerInfoDto playerDto) {

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

    @Override
    public PlayerDto updatePlayerStats(Long id, PlayerStatsDto playerStatsDto) {

        Player player = repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id, "name"));

        player.setAppearances(playerStatsDto.getAppearances());
        player.setGoals(playerStatsDto.getGoals());
        player.setStars(playerStatsDto.getStars());

        return convertToDto(repository.save(player));
    }

    @Override
    public Page<PlayerDto> getPlayersOfGivenClub(Long id, Pageable pageable) {

        if(clubRepository.existsById(id)){
            throw new EntityNotFoundException("Club with given id does not exist");
        }

        return repository.findByClub_Id(id, pageable).map(this::convertToDto);
    }

    @Override
    public Page<PlayerDto> getPlayersOfMyClub(Pageable pageable) {
        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (user.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        return repository.findByClub_Id(user.getPlayer().getOwnedClub().getId(), pageable).map(this::convertToDto);
    }

    @Override
    public PlayerDto makePlayerTrainer(Long id, BooleanDto booleanDto) {

        Player player = repository.findById(id).orElseThrow(() -> entityNotFoundException(id, "name"));

        player.setTrainer(booleanDto.isValue());

        return convertToDto(repository.save(player));
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if(userDetails instanceof UserPrincipal){
            UserPrincipal userPrincipal = ((UserPrincipal) userDetails);
            username = ((UserPrincipal) userDetails).getUsername();
        }

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username not found"));
    }
}
