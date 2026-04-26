package com.chaisharma.chaimasala.repository;

import com.chaisharma.chaimasala.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByMobileNumber(String mobileNumber); // standard login

    Optional<User> findByEmail(String email);               // Google OAuth2 login

    boolean existsByMobileNumber(String mobileNumber);      // duplicate check on register
}
