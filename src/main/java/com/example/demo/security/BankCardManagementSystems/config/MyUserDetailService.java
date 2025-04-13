package com.example.demo.security.BankCardManagementSystems.config;

import com.example.demo.entity.BankCardManagementSystems.User;
import com.example.demo.repository.BankCardManagementSystems.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(email + "There is not such email in Repo"));
    }
}
