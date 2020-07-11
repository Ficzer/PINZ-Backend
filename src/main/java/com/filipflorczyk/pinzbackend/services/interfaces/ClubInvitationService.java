package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.ClubInvitationDto;
import com.filipflorczyk.pinzbackend.dtos.ClubInvitationsDtos.NewClubInvitationDto;
import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ClubInvitationService extends BaseService<ClubInvitation, ClubInvitationDto> {

    Page<ClubInvitationDto> getMyClubInvitations(Pageable pageable);

    void makeInvitation(NewClubInvitationDto newClubInvitationDto);

    void removeMyInvitation(Long invitationId);

    Page<ClubInvitationDto> getMyInvitations(Pageable pageable);

    void executeMyInvitation(Long invitationId, boolean isAccepted);
}
