package com.starlight.repository;

import com.starlight.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailOrUsername(String email, String username);


}
