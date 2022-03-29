package com.starlight.service;

import com.starlight.config.security.SecurityConfig;
import com.starlight.dto.UserDto;
import com.starlight.exception.UserAlreadyExistException;
import com.starlight.exception.ValidationException;
import com.starlight.model.User;
import com.starlight.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.starlight.model.enums.Role.USER;
import static com.starlight.model.enums.UserStatus.ACTIVE;

@Service
public class UserService implements CommonService<UserDto, Long> {

    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(SecurityConfig securityConfig, UserRepository userRepository, ModelMapper modelMapper) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(UserDto userDto) throws UserAlreadyExistException {
        if (emailOrUsernameExist(userDto)) {
            throw new UserAlreadyExistException("Account with email " + "\'" + userDto.getEmail() + "\'" +
                    " or username " + "\'" + userDto.getUsername() + "\'" + " is already exist");
        }
        User user = convertToUser(userDto);
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        user.setRole(USER);
        user.setUserStatus(ACTIVE);
        userRepository.save(user);
    }

    private boolean emailOrUsernameExist(UserDto userDto) {
        return userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername()) != null;
    }

    @Override
    public UserDto findById(Long value) {
        return null;
    }

    @Override
    public UserDto update(UserDto model) {
        return null;
    }

    @Override
    public void delete(Long value) {

    }

    public void deleteByUsername(String username, String password) throws ValidationException, DataIntegrityViolationException {
        var passwordByUsername = userRepository.findPasswordByUsername(username);
        if (!securityConfig.passwordEncoder().matches(password, passwordByUsername)) {
            throw new ValidationException("Incorrect password");
        }
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public List<UserDto> getAll() {
        return null;
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
