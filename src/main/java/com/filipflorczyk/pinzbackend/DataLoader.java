package com.filipflorczyk.pinzbackend;

import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.repositories.PlayerRepository;
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
    private PlayerRepository playerRepository;

    private PasswordEncoder bCryptPasswordEncoder;

    public DataLoader(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder,
                      PlayerRepository playerRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.playerRepository = playerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserRole userRole1 = UserRole.builder().name("ADMIN").build();
        UserRole userRole2 = UserRole.builder().name("USER").build();

        Player player = Player.builder().firstName("Filip")
                .lastName("Florczyk").height(180).isTrainer(true).build();

        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole1);
        User user = User.builder()
                .userName("admin")
                .userPassword(bCryptPasswordEncoder.encode("admin"))
                .userRoles(userRoleSet)
                .player(player)
                .build();

        userRoleRepository.save(userRole1);
        userRoleRepository.save(userRole2);
        userRepository.save(user);
        player.setUser(user);
        playerRepository.save(player);
    }
}
