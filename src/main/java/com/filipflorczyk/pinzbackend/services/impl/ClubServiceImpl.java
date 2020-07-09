package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubInfoDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.NewClubDto;
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.repositories.ClubRepository;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl extends BaseServiceImpl<ClubRepository, Club, ClubDto> implements ClubService {

    private PlayerRepository playerRepository;
    private UserRepository userRepository;

    public ClubServiceImpl(ClubRepository repository, PlayerRepository playerRepository, UserRepository userRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Club");
    }

    @Override
    public Club convertToEntity(ClubDto clubDto) {

        Club club = modelMapper.map(clubDto, Club.class);

        return club;
    }

    @Override
    public ClubDto convertToDto(Club club) {

        ClubDto clubDto = modelMapper.map(club, ClubDto.class);

        return clubDto;
    }

    @Override
    public void addNewClub(NewClubDto newClubDto) {

        if(repository.existsByName(newClubDto.getName())){
            throw new IllegalArgumentException("Club with given name already exist");
        }

        Player player = playerRepository.findById(newClubDto.getTrainerId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Player with given id not found"));

        if(!player.isTrainer()){
            throw new IllegalArgumentException("Player is not set as a trainer");
        }

        Club club = Club.builder().name(newClubDto.getName()).trainer(player).build();
        player.setOwnedClub(club);
        player.setClub(club);

        ClubDto clubDto = convertToDto(repository.save(club));

        playerRepository.save(player);
    }

    @Override
    public ClubDto getMyClub() {

        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        return convertToDto(club);
    }

    @Override
    public ClubDto updateClubInfo(Long id, ClubInfoDto clubInfoDto) {

        Club club = repository.findById(id).orElseThrow(() -> entityNotFoundException(id, "name"));

        club.setName(clubInfoDto.getName());

        return convertToDto(repository.save(club));
    }

    @Override
    public ClubDto updateMyClubInfo(ClubInfoDto clubInfoDto) {
        return null;
    }

    @Override
    public ClubDto removePlayerFromMyClub(Long id) {

        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        club.setPlayers(club.getPlayers().stream().filter(player -> !player.getId().equals(id)).collect(Collectors.toList()));

        return convertToDto(repository.save(club));
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if(userDetails instanceof UserPrincipal){
            username = ((UserPrincipal) userDetails).getUsername();
        }

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username not found"));
    }
}
