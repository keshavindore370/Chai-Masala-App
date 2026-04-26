package com.chaisharma.chaimasala.service;

import com.chaisharma.chaimasala.exception.ResourceNotFoundException;
import com.chaisharma.chaimasala.model.User;
import com.chaisharma.chaimasala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ── Register new user ─────────────────────────────────────────────────────
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("CUSTOMER");
        }
        return userRepository.save(user);
    }

    // ── Check duplicate mobile before registering ─────────────────────────────
    public boolean existsByMobileNumber(String mobileNumber) {
        return userRepository.existsByMobileNumber(mobileNumber);
    }

    // ── Get user by ID ────────────────────────────────────────────────────────
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    // ── Get user by mobile ────────────────────────────────────────────────────
    public User getUserByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User", "mobileNumber", mobileNumber));
    }

    // ── Get all users (admin) ─────────────────────────────────────────────────
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ── Update user ───────────────────────────────────────────────────────────
    public User updateUser(String id, User updatedUser) {
        User existing = getUserById(id);

        if (updatedUser.getMobileNumber() != null) {
            existing.setMobileNumber(updatedUser.getMobileNumber());
        }
        if (updatedUser.getEmail() != null) {
            existing.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(existing);
    }

    // ── Delete user ───────────────────────────────────────────────────────────
    public void deleteUser(String id) {
        userRepository.delete(getUserById(id));
    }

    // ── Find or create user after Google OAuth2 login ─────────────────────────
    public User findOrCreateOAuthUser(String email, String name) {
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            return existing.get();
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setMobileNumber(email); // email used as username for OAuth users
        newUser.setPassword(passwordEncoder.encode("OAUTH2_NO_PASSWORD"));
        newUser.setRole("CUSTOMER");
        return userRepository.save(newUser);
    }
}
