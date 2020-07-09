package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.ClubInvitation;
import com.filipflorczyk.pinzbackend.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubInvitationRepository extends JpaRepository<ClubInvitation, Long>, JpaSpecificationExecutor<ClubInvitation> {

    Page<ClubInvitation> findAllByPlayer_Id(Long id, Pageable pageable);
    Page<ClubInvitation> findAllByClub_Id(Long id, Pageable pageable);
}
