package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.ClubStatistic;
import com.filipflorczyk.pinzbackend.entities.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubStatisticRepository extends JpaRepository<ClubStatistic, Long>, JpaSpecificationExecutor<ClubStatistic> {

    Page<ClubStatistic> findAllByLeague_Id(Long id, Pageable pageable);
}
