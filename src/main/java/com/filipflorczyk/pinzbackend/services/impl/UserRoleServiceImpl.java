package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRoleRepository;
import com.filipflorczyk.pinzbackend.services.interfaces.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleRepository, UserRole, UserRoleDto> implements UserRoleService {

    UserRepository userRepository;

    public UserRoleServiceImpl(UserRoleRepository repository, UserRepository userRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "User role");
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(String entity, String value) {
        return super.entityNotFoundException("User role", value);
    }

    @Override
    public UserRole convertToEntity(UserRoleDto userDto) {

        UserRole userRole = modelMapper.map(userDto, UserRole.class);

        return userRole;
    }

    @Override
    public UserRoleDto convertToDto(UserRole entity) {

        UserRoleDto userRoleDto = modelMapper.map(entity, UserRoleDto.class);

        return userRoleDto;
    }

    @Override
    public UserRoleDto addRolesToUser(Long id, BaseDto userRoleDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with given id not found"));
        UserRole userRole = repository.findById(userRoleDto.getId())
                .orElseThrow(() -> entityNotFoundException(userRoleDto.getId(), "User role"));
        user.getUserRoles().add(userRole);
        userRepository.save(user);

        return convertToDto(userRole);
    }

    @Override
    public UserRoleDto findRolesOfUser(Long id) {



        return null;
    }
}
