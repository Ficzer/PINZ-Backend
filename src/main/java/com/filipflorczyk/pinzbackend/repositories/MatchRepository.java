package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
}
