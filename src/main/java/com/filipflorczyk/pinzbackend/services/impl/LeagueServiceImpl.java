package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.LeagueDto;
import com.filipflorczyk.pinzbackend.entities.League;
import com.filipflorczyk.pinzbackend.repositories.*;
import com.filipflorczyk.pinzbackend.services.interfaces.LeagueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class LeagueServiceImpl extends BaseServiceImpl<LeagueRepository, League, LeagueDto> implements LeagueService {


    public LeagueServiceImpl(LeagueRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "League");
    }

    @Override
    public League convertToEntity(LeagueDto leagueDto) {

        League league = modelMapper.map(leagueDto, League.class);

        return league;
    }

    @Override
    public LeagueDto convertToDto(League league) {

        LeagueDto leagueDto = modelMapper.map(league, LeagueDto.class);

        return leagueDto;
    }


}
