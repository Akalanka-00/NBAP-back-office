package com.nexusbit.apiportal.config;

import com.nexusbit.apiportal.model.UserModel;
import com.nexusbit.apiportal.repository.UserRepo;
import com.nexusbit.apiportal.utils.LoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthProvider implements AuthenticationProvider {


    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;
    private static final LoggerService logger = new LoggerService();

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<UserModel> users = userRepo.findByEmail(email);

        if (users.size()>0) {
            List<String> authorities = userRepo.getAuthorities(email);
            if (passwordEncoder.matches(password, users.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(email, users.get(0).getPassword(), getGrantedAuthorities(authorities));
            } else {
                logger.error("Invalid Password");
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            logger.error("User not registered with this details");
            throw new BadCredentialsException("User not registered with this details!");

        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
       for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }
}
