package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.NewMatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.UpdateMatchDto;
import com.filipflorczyk.pinzbackend.entities.*;
import com.filipflorczyk.pinzbackend.repositories.*;
import com.filipflorczyk.pinzbackend.services.interfaces.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Service
public class MatchServiceImpl extends BaseServiceImpl<MatchRepository, Match, MatchDto> implements MatchService {

    LeagueRepository leagueRepository;
    ClubRepository clubRepository;
    StadiumRepository stadiumRepository;
    PlayerMatchStatRepository playerMatchStatRepository;


    public MatchServiceImpl(MatchRepository repository, LeagueRepository leagueRepository,
                            ClubRepository clubRepository, StadiumRepository stadiumRepository,
                            PlayerMatchStatRepository playerMatchStatRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.leagueRepository = leagueRepository;
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
        this.playerMatchStatRepository = playerMatchStatRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "League");
    }

    @Override
    public Match convertToEntity(MatchDto matchDto) {

        Match match = modelMapper.map(matchDto, Match.class);

        return match;
    }

    @Override
    public MatchDto convertToDto(Match match) {

        MatchDto matchDto = modelMapper.map(match, MatchDto.class);

        return matchDto;
    }


    @Override
    public Page<MatchDto> getAllMatchesOfPlayer(Long id, Pageable pageable) {
        return repository.findAllByPlayer_Id(id, pageable).map(this::convertToDto);
    }

    @Override
    public Page<MatchDto> getAllMatchesOfLeague(Long id, Pageable pageable) {
        return repository.findAllByLeague_Id(id, pageable).map(this::convertToDto);
    }

    @Override
    public Page<MatchDto> getAllMatchesOfClub(Long id, Pageable pageable) {
        return repository.findAllByHomeClub_IdOrAwayClub_Id(id, pageable).map(this::convertToDto);
    }

    @Override
    public void addNewMatch(NewMatchDto newMatchDto) {

        Club homeClub = clubRepository.findById(newMatchDto.getHomeClubId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Home club with given id not found"));

        Club awayClub = clubRepository.findById(newMatchDto.getAwayClubId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Away club with given id not found"));

        Stadium stadium = stadiumRepository.findById(newMatchDto.getStadiumId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Stadium with given id not found"));

        League league = leagueRepository.findById(newMatchDto.getLeagueId().getId())
                .orElseThrow(() -> new EntityNotFoundException("League with given id not found"));

        Match match = Match.builder()
                .startDateTime(newMatchDto.getStartDateTime())
                .endDateTime(newMatchDto.getStartDateTime())
                .homeClub(homeClub)
                .awayClub(awayClub)
                .league(league)
                .stadium(stadium)
                .isCanceled(false)
                .build();

        homeClub.getHomeMatches().add(match);
        awayClub.getAwayMatches().add(match);
        stadium.getMatches().add(match);
        league.getMatches().add(match);

        repository.save(match);

        clubRepository.save(homeClub);
        clubRepository.save(awayClub);
        stadiumRepository.save(stadium);
        leagueRepository.save(league);
    }

    @Override
    public void updateMatch(Long id, UpdateMatchDto updateMatchDto) {

        Match match = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match with given id not found"));


        match.setMatchResult(updateMatchDto.getMatchResult());
        match.setHomeClubGoals(updateMatchDto.getHomeClubGoals());
        match.setAwayClubGoals(updateMatchDto.getAwayClubGoals());
        match.setHomeClubPossessionPercentage(updateMatchDto.getHomeClubPossessionPercentage());
        match.setAwayClubPossessionPercentage(updateMatchDto.getAwayClubPossessionPercentage());
        match.setHomeClubShotsOnTarget(updateMatchDto.getHomeClubShotsOnTarget());
        match.setAwayClubShotsOnTarget(updateMatchDto.getAwayClubShotsOnTarget());
        match.setHomeClubShots(updateMatchDto.getHomeClubShots());
        match.setAwayClubShots(updateMatchDto.getAwayClubShots());
        match.setHomePasses(updateMatchDto.getHomePasses());
        match.setAwayPasses(updateMatchDto.getAwayPasses());
        match.setHomeCorners(updateMatchDto.getHomeCorners());
        match.setAwayCorners(updateMatchDto.getAwayCorners());
        match.setHomeOffsides(updateMatchDto.getHomeOffsides());
        match.setAwayOffsides(updateMatchDto.getAwayOffsides());
        match.setHomeYellowCards(updateMatchDto.getHomeYellowCards());
        match.setAwayYellowCards(updateMatchDto.getAwayYellowCards());
        match.setHomeRedCards(updateMatchDto.getHomeRedCards());
        match.setAwayRedCards(updateMatchDto.getAwayRedCards());
        match.setHomeFouls(updateMatchDto.getHomeFouls());
        match.setAwayFouls(updateMatchDto.getAwayFouls());

        repository.save(match);
    }

    @Override
    public void cancelMatch(Long id) {

        Match match = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match with given id not found"));

        match.setCanceled(true);

        repository.save(match);
    }


}