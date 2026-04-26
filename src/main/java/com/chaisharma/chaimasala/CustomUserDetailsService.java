package com.chaisharma.chaimasala;

import com.chaisharma.chaimasala.model.User;
import com.chaisharma.chaimasala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + mobileNumber));

        // .roles(user.getRole()) automatically adds "ROLE_" prefix
        // So "ADMIN" becomes "ROLE_ADMIN" — matches hasRole("ADMIN") in SecurityConfig
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getMobileNumber())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
