package com.starlight.service;

import com.starlight.dto.UserDto;
import com.starlight.exception.UserAlreadyExistException;
import com.starlight.exception.ValidationException;
import com.starlight.model.User;
import com.starlight.repository.UserRepository;
import com.starlight.security.PasswordEncoderConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starlight.model.enums.Role.USER;
import static com.starlight.model.enums.UserStatus.ACTIVE;

@Service
@PropertySource("classpath:/mail.properties")
public class UserService implements CommonService<UserDto, Long> {

    private final PasswordEncoderConfig passwordEncoderConfig;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MailSenderService mailSenderService;
    @Value("${account.activation.url}")
    private final String ACTIVATION_ACCOUNT_URL = null;

    @Autowired
    public UserService(PasswordEncoderConfig passwordEncoderConfig, UserRepository userRepository, ModelMapper modelMapper, MailSenderService mailSenderService) {
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void create(UserDto userDto) throws UserAlreadyExistException {
        if (emailOrUsernameExist(userDto)) {
            throw new UserAlreadyExistException("Account with email " + "\'" + userDto.getEmail() + "\'" +
                    " or username " + "\'" + userDto.getUsername() + "\'" + " is already exist");
        }
        User user = convertToUser(userDto);
        user.setPassword(encodePassword(user.getPassword()));
        user.setRole(USER);
        user.setUserStatus(ACTIVE);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        String activationMessage = String.format("""
                Hello, %s.
                Welcome to Starlight auction. Please, visit this link to activate your account: %s%s
                """, user.getUsername(), ACTIVATION_ACCOUNT_URL, user.getActivationCode());

        mailSenderService.sendMail(user.getEmail(), "Account activation", activationMessage);
    }

    public void createOAuth2User(OidcIdToken idToken) {
        String generatedUserPassword = UUID.randomUUID().toString();
        var user = User.builder()
                .username(idToken.getClaim("name"))
                .email(idToken.getClaim("email"))
                .password(encodePassword(generatedUserPassword))
                .role(USER)
                .userStatus(ACTIVE)
                .build();
        userRepository.save(user);
        mailSenderService.sendMail(user.getEmail(), "Your password", generatedUserPassword);
    }

    public boolean isActivationCodePresent(String code) {
        User user = userRepository.findUserByActivationCode(code);
        if (user == null) return false;
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
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

    public Integer deleteByUsername(String username, String password) throws ValidationException, DataIntegrityViolationException {
        checkUserPassword(username, password);
        var deleteUserResult = userRepository.deleteUserByUsername(username);
        return deleteUserResult;
    }

    @Override
    public List<UserDto> getAll() {
        return null;
    }

    public void changePassword(String oldPassword, UserDto userDto) throws ValidationException {
        var username = userDto.getUsername();
        checkUserPassword(username, oldPassword);

        if (userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            var encodeUserPassword = encodePassword(userDto.getPassword());
            userRepository.changeUserPasswordByUsername(username, encodeUserPassword);
        }
    }

    public boolean emailOrUsernameExist(UserDto userDto) {
        return userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername()) != null;
    }

    public boolean isUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean checkUserPassword(String username, String password) throws ValidationException {
        var passwordFromDb = userRepository.findPasswordByUsername(username);
        if (!passwordEncoderConfig.passwordEncoder().matches(password, passwordFromDb)) {
            throw new ValidationException("Incorrect password");
        }

        return true;
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private String encodePassword(String password) {
        return passwordEncoderConfig.passwordEncoder().encode(password);
    }

}
