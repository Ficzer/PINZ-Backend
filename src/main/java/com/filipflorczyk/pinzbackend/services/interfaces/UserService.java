package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.security.AuthenticationRequest;

public interface UserService extends BaseService<User, UserDto> {

    void registerUser(AuthenticationRequest registerRequest);
}
