package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRoleService extends BaseService<UserRole, UserRoleDto> {

    UserRoleDto addRolesToUser(Long id, BaseDto userRoleDto);

    Page<UserRoleDto> findRolesOfUser(Long id, Pageable pageable);
}
