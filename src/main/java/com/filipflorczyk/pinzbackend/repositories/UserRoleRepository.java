package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole>{

    @Query(value = "select * from user_roles as ur " +
            "join users_user_roles as uur " +
            "on ur.id = uur.user_role_id " +
            "join users as u " +
            "on u.id = uur.user_id " +
            "where ur.id=uur.user_role_id and u.id = uur.user_id and u.id = ?1",
            nativeQuery = true)
    Page<UserRole> findByUsers_Id(Long id, Pageable pageable);

    Optional<UserRole> findByName(String name);
}
