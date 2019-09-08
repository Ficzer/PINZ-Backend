package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserRepository, User, UserDto> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
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

        return user;
    }

    @Override
    public UserDto convertToDto(User entity) {

        UserDto userDto = modelMapper.map(entity, UserDto.class);

        return userDto;
    }


}
