package com.starlight.security;

import com.starlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final UserService userService;

    @Autowired
    public SecurityConfig(@Qualifier ("userDetailsServiceImpl") UserDetailsService userDetailsService, PasswordEncoderConfig passwordEncoderConfig,UserService userService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                    .disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                                .antMatchers("/index", "/login", "/registration").permitAll()
                                .antMatchers("/market/**", "/user-profile/**", "/lot/**").authenticated()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login"))
                .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/market"))
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/market")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                );

    }

    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {

        return userRequest -> {
            OidcIdToken idToken = userRequest.getIdToken();
            String userEmail = idToken.getClaim("email");

            if (!userService.isUserExist(userEmail)) {
                userService.createOAuth2User(idToken);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            DefaultOidcUser defaultOidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfig.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    ((proxy, method, args) -> userDetailsMethods.contains(method)
                    ? method.invoke(userDetails, args) : method.invoke(defaultOidcUser, args)));
        };

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
