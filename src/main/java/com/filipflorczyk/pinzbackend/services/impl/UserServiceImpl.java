package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRoleRepository;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserRepository, User, UserDto> implements UserService {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return super.entityNotFoundException(id, "User");
    }

    @Override
    protected EntityNotFoundException entityNotFoundException(String entity, String value) {
        return super.entityNotFoundException("User", value);
    }

    @Override
    public User convertToEntity(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);

        Set<UserRole> userRoles = new HashSet<>();
        if(userDto.getUserRoleDto() != null){
            userDto.getUserRoleDto().stream()
                    .forEach(userRoleDto -> {userRoles.add(userRoleRepository.findById(userRoleDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("User role for user was not found")));});
        }
        user.setUserRoles(userRoles);

        return user;
    }

    @Override
    public UserDto convertToDto(User entity) {

        UserDto userDto = modelMapper.map(entity, UserDto.class);

        Set<UserRoleDto> userRoleDtoSet = new HashSet<>();

        //TODO finish user role converting to dto

        return userDto;
    }
}
