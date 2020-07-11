package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.PlayerMatchStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMatchStatRepository extends JpaRepository<PlayerMatchStat, Long>, JpaSpecificationExecutor<PlayerMatchStat> {

    Page<PlayerMatchStat> findAllByMatchId(Long id, Pageable pageable);
    Page<PlayerMatchStat> findAllByPlayerId(Long id, Pageable pageable);
}
