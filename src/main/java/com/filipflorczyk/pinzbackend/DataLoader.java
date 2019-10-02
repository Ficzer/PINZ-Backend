package com.filipflorczyk.pinzbackend;

import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import com.filipflorczyk.pinzbackend.repositories.UserRoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    private PasswordEncoder bCryptPasswordEncoder;

    public DataLoader(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserRole userRole = UserRole.builder().name("ADMIN").build();
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole);
        User user = User.builder()
                .userName("admin")
                .userPassword(bCryptPasswordEncoder.encode("admin"))
                .userRoles(userRoleSet)
                .build();

        userRoleRepository.save(userRole);
        userRepository.save(user);
    }
}
