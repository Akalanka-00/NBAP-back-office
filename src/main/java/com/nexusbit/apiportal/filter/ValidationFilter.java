package com.nexusbit.apiportal.filter;

import com.nexusbit.apiportal.constants.enums.USER_TYPES;
import com.nexusbit.apiportal.dto.ErrorResponse;
import com.nexusbit.apiportal.repository.UserRepo;
import com.nexusbit.apiportal.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class ValidationFilter implements Filter {

    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private final Charset credentialsCharset = StandardCharsets.UTF_8;
    private static final Logger logger = LoggerFactory.getLogger(ValidationFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String header = req.getHeader(AUTHORIZATION);
        String role = req.getHeader("role");

        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                byte[] decodedRole;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    decodedRole = Base64.getDecoder().decode(role);

                    String token = new String(decoded, credentialsCharset);
                    String roleToken = new String(decodedRole, credentialsCharset);
                    int delim = token.indexOf(":");
                    int roleDelim = roleToken.indexOf(":");

                    if (delim == -1) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error("Invalid basic authentication token!. userValidation()");
                        return;
                    }
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error("User login failed!. userValidation()");
                        return;
                    }

                    if (roleDelim == -1) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error("Invalid role authentication token!. userValidation()");
                        return;

                    }

                    String roleName = roleToken.substring(0, roleDelim);
                    String expDateStrValue = roleToken.substring(roleDelim + 1);
                    if(expDateStrValue.equals("none")){
                        if(roleName.equals(USER_TYPES.premium.name())){
                            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error("Role expired!. userValidation()");
                            return;
                        }
                    }else{
                        Date expDate = new Date(Long.parseLong(expDateStrValue));
                        if(expDate.before(new Date()) && roleName.equals(USER_TYPES.premium.name())) {
                            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error("Role expired!. userValidation()");
                            return;
                        }
                    }

                } catch (IllegalArgumentException e) {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error("Failed to decode basic authentication token!."+e.getMessage()+" userValidation()");
                    return;

                }
            }
        }
        chain.doFilter(request, response);


    }
}
