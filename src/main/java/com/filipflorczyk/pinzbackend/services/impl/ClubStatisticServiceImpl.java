package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.ClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.NewClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.ClubStatisticsDto.UpdateClubStatisticDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.MatchDto;
import com.filipflorczyk.pinzbackend.dtos.MatchsDtos.UpdateMatchDto;
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.ClubStatistic;
import com.filipflorczyk.pinzbackend.entities.League;
import com.filipflorczyk.pinzbackend.repositories.ClubRepository;
import com.filipflorczyk.pinzbackend.repositories.ClubStatisticRepository;
import com.filipflorczyk.pinzbackend.repositories.LeagueRepository;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubStatisticService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ClubStatisticServiceImpl extends BaseServiceImpl<ClubStatisticRepository, ClubStatistic, ClubStatisticDto> implements ClubStatisticService {

    ClubRepository clubRepository;
    LeagueRepository leagueRepository;

    public ClubStatisticServiceImpl(ClubStatisticRepository repository, ClubRepository clubRepository,
                                    LeagueRepository leagueRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.clubRepository = clubRepository;
        this.leagueRepository = leagueRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Club statistic");
    }

    @Override
    public ClubStatistic convertToEntity(ClubStatisticDto clubStatisticDto) {

        ClubStatistic clubStatistic = modelMapper.map(clubStatisticDto, ClubStatistic.class);

        return clubStatistic;
    }

    @Override
    public ClubStatisticDto convertToDto(ClubStatistic clubStatistic) {

        ClubStatisticDto clubStatisticDto = modelMapper.map(clubStatistic, ClubStatisticDto.class);

        return clubStatisticDto;
    }

    @Override
    public Page<ClubStatisticDto> getAllClubStatisticOfLeague(Long id, Pageable pageable) {
        return repository.findAllByLeague_Id(id, pageable).map(this::convertToDto);
    }

    @Override
    public void addNewClubStatistic(NewClubStatisticDto newClubStatisticDto) {

        Club club = clubRepository.findById(newClubStatisticDto.getClubId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Club with given id not found"));

        League league = leagueRepository.findById(newClubStatisticDto.getLeagueId().getId())
                .orElseThrow(() -> new EntityNotFoundException("League with given id not found"));

        ClubStatistic clubStatistic = ClubStatistic.builder()
                .club(club)
                .league(league)
                .build();

        repository.save(clubStatistic);

        club.getClubStatistics().add(clubStatistic);
        league.getClubStatistics().add(clubStatistic);

        clubRepository.save(club);
        leagueRepository.save(league);
    }

    @Override
    public void updateClubStatistic(Long id, UpdateClubStatisticDto updateClubStatisticDto) {

        ClubStatistic clubStatistic = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Club with given id not found"));

        clubStatistic.setGoalDifference(updateClubStatisticDto.getGoalDifference());
        clubStatistic.setGoalsAgainst(updateClubStatisticDto.getGoalsAgainst());
        clubStatistic.setGoalsFor(updateClubStatisticDto.getGoalsFor());
        clubStatistic.setPoints(updateClubStatisticDto.getPoints());
        clubStatistic.setWon(updateClubStatisticDto.getWon());
        clubStatistic.setDrawn(updateClubStatisticDto.getDrawn());
        clubStatistic.setLost(updateClubStatisticDto.getLost());

        repository.save(clubStatistic);
    }
}
