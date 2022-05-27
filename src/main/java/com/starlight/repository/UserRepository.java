package com.starlight.repository;

import com.starlight.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailOrUsername(String email, String username);

    User findUserByActivationCode(String code);

    @Query(value = "SELECT u.password FROM users as u WHERE u.username = :username", nativeQuery = true)
    String findPasswordByUsername(@Param("username") String username);

    @Query(value = "SELECT u.id, u.username, u.birth_date, u.email, u.password, u.role, u.user_status, u.activation_code " +
            "FROM users u " +
            "WHERE u.email=:email AND u.activation_code is null", nativeQuery = true)
    User findByEmail(@Param("email")String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users as u WHERE u.username = :username", nativeQuery = true)
    Integer deleteUserByUsername(@Param("username")String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password = :password WHERE username = :username", nativeQuery = true)
    Integer changeUserPasswordByUsername(@Param("username") String username, @Param("password") String password);

}
