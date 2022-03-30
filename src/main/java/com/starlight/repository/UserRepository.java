package com.starlight.repository;

import com.starlight.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String username);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users as u WHERE u.username = :username", nativeQuery = true)
    void deleteUserByUsername(@Param("username")String username);

    @Query(value = "SELECT u.password FROM users as u WHERE u.username = :username", nativeQuery = true)
    String findPasswordByUsername(@Param("username") String username);


    User findUserByActivationCode(String code);
}
