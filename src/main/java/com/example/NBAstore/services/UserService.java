package com.example.NBAstore.services;

import com.example.NBAstore.enums.Role;
import com.example.NBAstore.models.User;
import com.example.NBAstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userResponse = userRepository.findByEmail(email);

        if (userResponse.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user with email = " + email);
        }

        User user = userResponse.get();

        String roleStr = "ROLE_USER";

        if (user.getRole() == Role.admin) {
            roleStr = "ROLE_ADMIN";
        }

        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleStr))
        );
    }
}
