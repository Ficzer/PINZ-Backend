package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRoleRepository;
import com.filipflorczyk.pinzbackend.security.AuthenticationRequest;
import com.filipflorczyk.pinzbackend.security.UserPrincipal;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public User convertToEntity(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);

        Set<UserRole> userRoles = new HashSet<>();
        if(userDto.getUserRoleDto() != null){
            userDto.getUserRoleDto().stream()
                    .forEach(userRoleDto -> {userRoles.add(userRoleRepository.findById(userRoleDto.getId())
                            .orElseThrow(() -> super.entityNotFoundException(userRoleDto.getId(), "User role")));});
        }
        user.setUserRoles(userRoles);

        return user;
    }

    @Override
    public UserDto convertToDto(User entity) {

        UserDto userDto = modelMapper.map(entity, UserDto.class);

        Set<UserRoleDto> userRoleDtoSet = new HashSet<>();
        for (UserRole userRole: entity.getUserRoles()) {
            userRoleDtoSet.add(modelMapper.map(userRole, UserRoleDto.class));
        }

        userDto.setUserRoleDto(userRoleDtoSet);

        return userDto;
    }

    @Override
    public void registerUser(AuthenticationRequest registerRequest) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(repository.existsByUserName(registerRequest.getUsername())){
            throw new IllegalArgumentException("User is already registered");
        }

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRoleRepository.findByName("USER")
                .orElseThrow(() -> new EntityNotFoundException("User role with given name does not exist")));

        User user = User.builder()
                .userName(registerRequest.getUsername())
                .userPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .userRoles(userRoles)
                .build();
        repository.save(user);
    }

    @Override
    public UserDto getCurrentUserDto() {
        return convertToDto(getCurrentUser());
    }

    private User getCurrentUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if(userDetails instanceof UserPrincipal){
            username = ((UserPrincipal) userDetails).getUsername();
        }

        return repository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username not found"));
    }
}
