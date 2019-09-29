package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.PlayerMatchStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayerMatchStatRepository extends JpaRepository<PlayerMatchStat, Long>, JpaSpecificationExecutor<PlayerMatchStat> {
}
