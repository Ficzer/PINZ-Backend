package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.UserRole;

public interface UserRoleService extends BaseService<UserRole, UserRoleDto> {

    UserRoleDto addRolesToUser(Long id, BaseDto userRoleDto);

    UserRoleDto findRolesOfUser(Long id);
}
