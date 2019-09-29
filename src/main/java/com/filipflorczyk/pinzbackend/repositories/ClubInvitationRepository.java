package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClubInvitationRepository extends JpaRepository<ClubInvitation, Long>, JpaSpecificationExecutor<ClubInvitation> {
}
