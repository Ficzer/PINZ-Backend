package com.filipflorczyk.pinzbackend.security;

import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(s)
                .orElseThrow(() -> new UsernameNotFoundException("User with given username not found"));

        return new UserPrincipal(user);
    }
}
