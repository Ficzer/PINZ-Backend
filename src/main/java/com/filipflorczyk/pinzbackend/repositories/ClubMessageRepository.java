package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.ClubMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClubMessageRepository extends JpaRepository<ClubMessage, Long>, JpaSpecificationExecutor<ClubMessage> {
}
