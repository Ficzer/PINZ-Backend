package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.ClubStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubStatisticRepository extends JpaRepository<ClubStatistic, Long>, JpaSpecificationExecutor<ClubStatistic> {
}
