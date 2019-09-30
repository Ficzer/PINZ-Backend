package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long>, JpaSpecificationExecutor<League> {
}
