package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.NewPlayerMatchStatDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerMatchStatsDto.PlayerMatchStatDto;
import com.filipflorczyk.pinzbackend.entities.*;
import com.filipflorczyk.pinzbackend.repositories.*;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.PlayerMatchStatService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PlayerMatchStatServiceImpl extends BaseServiceImpl<PlayerMatchStatRepository, PlayerMatchStat, PlayerMatchStatDto> implements PlayerMatchStatService {

    private UserRepository userRepository;
    private PlayerRepository playerRepository;
    private MatchRepository matchRepository;

    public PlayerMatchStatServiceImpl(PlayerMatchStatRepository repository, UserRepository userRepository,
                                      PlayerRepository playerRepository, MatchRepository matchRepository,
                                      ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "PlayerMatchStat");
    }

    @Override
    public PlayerMatchStat convertToEntity(PlayerMatchStatDto playerMatchStatDto) {

        PlayerMatchStat playerMatchStat = modelMapper.map(playerMatchStatDto, PlayerMatchStat.class);

        return playerMatchStat;
    }

    @Override
    public PlayerMatchStatDto convertToDto(PlayerMatchStat playerMatchStat) {

        PlayerMatchStatDto playerMatchStatDto = modelMapper.map(playerMatchStat, PlayerMatchStatDto.class);

        return playerMatchStatDto;
    }

    @Override
    public Page<PlayerMatchStatDto> getAllStatsForMatch(Long id, Pageable pageable) {
        return repository.findAllByMatchId(id, pageable).map(this::convertToDto);
    }

    @Override
    public Page<PlayerMatchStatDto> getAllStatsForPlayer(Long id, Pageable pageable) {
        return repository.findAllByPlayerId(id, pageable).map(this::convertToDto);
    }

    @Override
    public void addPlayerMatchStat(NewPlayerMatchStatDto newPlayerMatchStatDto) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (user.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = user.getPlayer().getOwnedClub();

        Match match = matchRepository.findById(newPlayerMatchStatDto.getMatchId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Match with given id not found"));

        if(match.getAwayClub().getId() != club.getId() || match.getHomeClub().getId() != club.getId())
            throw new IllegalArgumentException("You can add new player match stat only for matches of your club");

        Player player = playerRepository.findById(newPlayerMatchStatDto.getPlayerId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Player with given id not found"));

        PlayerMatchStat playerMatchStat = PlayerMatchStat.builder()
                .match(match)
                .player(player)
                .fouls(newPlayerMatchStatDto.getFouls())
                .goals(newPlayerMatchStatDto.getGoals())
                .isFromHomeClub(newPlayerMatchStatDto.isFromHomeClub())
                .passes(newPlayerMatchStatDto.getPasses())
                .redCards(newPlayerMatchStatDto.getRedCards())
                .yellowCards(newPlayerMatchStatDto.getYellowCards())
                .build();

        match.getPlayerMatchStats().add(playerMatchStat);
        player.getPlayerMatchStats().add(playerMatchStat);

        repository.save(playerMatchStat);

        matchRepository.save(match);
        playerRepository.save(player);
    }

    @Override
    public void deletePlayerMatchStat(Long id) {

        User user = getCurrentUser();

        if (user.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (user.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = user.getPlayer().getOwnedClub();

        PlayerMatchStat playerMatchStat = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player Match stat with given id not found"));

        Match match = matchRepository.findById(playerMatchStat.getId())
                .orElseThrow(() -> new EntityNotFoundException("Match with given id not found"));

        if(match.getAwayClub().getId() != club.getId() || match.getHomeClub().getId() != club.getId())
            throw new IllegalArgumentException("You can add new player match stat only for matches of your club");

        repository.delete(playerMatchStat);
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
