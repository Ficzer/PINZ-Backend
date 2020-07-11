package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.ClubInvitationDto;
import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.NewClubInvitationDto;
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.repositories.ClubInvitationRepository;
import com.filipflorczyk.pinzbackend.repositories.ClubRepository;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubInvitationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClubInvitationServiceImpl extends BaseServiceImpl<ClubInvitationRepository, ClubInvitation, ClubInvitationDto> implements ClubInvitationService {

    private UserRepository userRepository;

    private PlayerRepository playerRepository;

    private ClubRepository clubRepository;

    public ClubInvitationServiceImpl(ClubInvitationRepository repository, UserRepository userRepository, PlayerRepository playerRepository,
            ClubRepository clubRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "Club invitation");
    }

    @Override
    public ClubInvitation convertToEntity(ClubInvitationDto clubDto) {

        ClubInvitation clubInvitation = modelMapper.map(clubDto, ClubInvitation.class);

        return clubInvitation;
    }

    @Override
    public ClubInvitationDto convertToDto(ClubInvitation clubInvitation) {

        ClubInvitationDto clubInvitationDto = modelMapper.map(clubInvitation, ClubInvitationDto.class);

        return clubInvitationDto;
    }

    @Override
    public Page<ClubInvitationDto> getMyClubInvitations(Pageable pageable) {
        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        return repository.findAllByClub_Id(club.getId(), pageable).map(this::convertToDto);
    }

    @Override
    public void makeInvitation(NewClubInvitationDto newClubInvitationDto) {

        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        Player player = playerRepository.findById(newClubInvitationDto.getPlayerId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Given player does not exist"));

        boolean alreadyInvitedFlag = false;

        for (ClubInvitation clubInvitation: club.getClubInvitations()
             ) {
            if (clubInvitation.getPlayer().equals(player)) {
                alreadyInvitedFlag = true;
                break;
            }
        }

        if(alreadyInvitedFlag){
            throw new IllegalArgumentException("Invitation for given player has already been made");
        }

        ClubInvitation clubInvitation = ClubInvitation.builder()
                .player(player)
                .club(club)
                .description(newClubInvitationDto.getDescription())
                .build();

        club.getClubInvitations().add(clubInvitation);
        player.getClubInvitations().add(clubInvitation);

        repository.save(clubInvitation);

        playerRepository.save(player);
        clubRepository.save(club);
    }

    @Override
    public void removeMyInvitation(Long invitationId) {

        User currentUser = getCurrentUser();

        if (currentUser.getPlayer() == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        if (currentUser.getPlayer().getOwnedClub() == null)
            throw new EntityNotFoundException("Player is not a trainer of any club");

        Club club = currentUser.getPlayer().getOwnedClub();

        ClubInvitation clubInvitation = repository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("Invitation with given id not found"));

        if(!club.getClubInvitations().contains(clubInvitation)){
            throw new IllegalArgumentException("Invitation not found in set of club invitations");
        }

        repository.deleteById(invitationId);
    }

    @Override
    public Page<ClubInvitationDto> getMyInvitations(Pageable pageable) {

        User currentUser = getCurrentUser();

        Player player = currentUser.getPlayer();

        if (player == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        return repository.findAllByPlayer_Id(player.getId(), pageable).map(this::convertToDto);
    }

    @Override
    public void executeMyInvitation(Long invitationId, boolean isAccepted) {

        User currentUser = getCurrentUser();

        Player player = currentUser.getPlayer();

        if (player == null)
            throw new EntityNotFoundException("Player for current logged user not found");

        ClubInvitation clubInvitation = repository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("Invitation with given id not found"));

        if(clubInvitation.getPlayer().getId() != player.getId())
            throw new IllegalArgumentException("Invitation does not belong to current logged user");

        if(isAccepted){
            Club club = clubRepository.findById(clubInvitation.getClub().getId())
                    .orElseThrow(() -> entityNotFoundException(clubInvitation.getClub().getId(), "club"));

            club.getPlayers().add(player);

            clubRepository.save(club);
        }
        else{
            repository.deleteById(invitationId);
        }
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
