package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.Match;
import com.filipflorczyk.pinzbackend.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {

    Page<Match> findAllByPlayer_Id(Long id, Pageable pageable);
    Page<Match> findAllByLeague_Id(Long id, Pageable pageable);
    Page<Match> findAllByHomeClub_IdOrAwayClub_Id(Long id, Pageable pageable);

}
