package com.starlight.auction.service;

import com.starlight.auction.dto.UserDto;
import com.starlight.auction.exception.UserAlreadyExistException;
import com.starlight.auction.exception.ValidationException;
import com.starlight.auction.model.User;
import com.starlight.auction.repository.UserRepository;
import com.starlight.auction.security.PasswordEncoderConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.starlight.auction.model.enums.Role.USER;
import static com.starlight.auction.model.enums.UserStatus.ACTIVE;

@Service
@PropertySource("classpath:/mail.properties")
public class UserService implements CommonService<UserDto>{

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
            String errorMessage = String.format("""
                    Account with email %s or username %s is already exist
                    """, userDto.getEmail(), userDto.getUsername());
            throw new UserAlreadyExistException(errorMessage);
        }

        User user = convertToUser(userDto);
        if (userDto.getTelegramAccount().isEmpty()) {
            user.setTelegramAccount(null);
        }
        user.setPassword(encodePassword(user.getPassword()));
        user.setRole(USER);
        user.setUserStatus(ACTIVE);
//        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

//        sendActivationMessage(user);
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

    public Integer deleteByUsername(String username, String password) throws ValidationException, DataIntegrityViolationException {
        checkUserPassword(username, password);
        return userRepository.deleteUserByUsername(username);
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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByTelegramAccount(String tgAccount) {
        return userRepository.findByTelegramAccount(tgAccount);
    }

    private void sendActivationMessage(User user) {
        String activationMessage = String.format("""
                Hello, %s.
                Welcome to Starlight auction. Please, visit this link to activate your account: %s%s
                """, user.getUsername(), ACTIVATION_ACCOUNT_URL, user.getActivationCode());

        mailSenderService.sendMail(user.getEmail(), "Account activation", activationMessage);
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private String encodePassword(String password) {
        return passwordEncoderConfig.passwordEncoder().encode(password);
    }

}
