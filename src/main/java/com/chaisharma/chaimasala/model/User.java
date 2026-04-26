package com.chaisharma.chaimasala.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String mobileNumber;   // used as username for standard login

    private String email;          // used for Google OAuth2 login

    private String password;

    private String role = "CUSTOMER"; // "CUSTOMER" or "ADMIN"

    private LocalDateTime createdAt = LocalDateTime.now();
}
