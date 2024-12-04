package com.cartworks.users.config;

import com.cartworks.users.entity.User;
import com.cartworks.users.entity.Role;
import com.cartworks.users.repository.UserRepository;
import com.cartworks.users.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Profile("qa")
public class QaProfileDatabaseInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public QaProfileDatabaseInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        // Insert sample roles into the database for QA profile
        Role roleUser = Role.builder().name("ROLE_USER").build();
        Role roleAdmin = Role.builder().name("ROLE_ADMIN").build();
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        // Insert sample users into the database for QA profile
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        User user1 = User.builder()
                .email("qauser1@example.com")
                .firstName("QA")
                .lastName("User1")
                .phoneNumber("1234567890")
                .password("password1")
                .roles(roles)
                .build();

        User user2 = User.builder()
                .email("qauser2@example.com")
                .firstName("QA")
                .lastName("User2")
                .phoneNumber("0987654321")
                .password("password2")
                .roles(roles)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
