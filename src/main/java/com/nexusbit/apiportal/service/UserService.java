package com.nexusbit.apiportal.service;

import com.nexusbit.apiportal.constants.enums.USER_STATUS;
import com.nexusbit.apiportal.constants.enums.USER_TYPES;
import com.nexusbit.apiportal.dto.ErrorResponse;
import com.nexusbit.apiportal.dto.user.UserRequest;
import com.nexusbit.apiportal.dto.user.UserResponse;
import com.nexusbit.apiportal.model.RoleModel;
import com.nexusbit.apiportal.model.UserModel;
import com.nexusbit.apiportal.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public ResponseEntity<?> registerUser(UserRequest request) {

        ResponseEntity<?> response = null;

        List<UserModel> users = userRepo.findByEmail(request.getEmail());
        if (users.size() > 0) {
            logger.error("User already exists!. registerUser()");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().title("Error Occurred").error("User already exists").build());
        }

        try {
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            UserModel model = UserModel.builder()
                    .fname(request.getFname())
                    .lname(request.getLname())
                    .email(request.getEmail())
                    .password(hashedPassword)
                    .createdAt(new Date())
                    .role(RoleModel.builder().role(USER_TYPES.standard.name()).build())
                    .status(USER_STATUS.pending.name())
                    .build();

            userRepo.save(model);
            UserResponse userResponse = UserResponse.builder()
                    .id(model.getId())
                    .fname(model.getFname())
                    .lname(model.getLname())
                    .email(model.getEmail())
                    .status(model.getStatus())
                    .createdAt(model.getCreatedAt())
                    .role(RoleModel.builder().role(USER_TYPES.standard.name()).build())
                    .build();
            logger.trace("User registration success!. registerUser()");
            System.out.println("user saved");

            response = ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        } catch (Exception e) {
            logger.error("User registration failed!. registerUser()");
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return response;
    }

    public ResponseEntity<?> loginUser(Authentication authentication, String token) {

        String[] credentials = new String(Base64.getDecoder().decode(token.split(" ")[1])).split(":");

        ResponseEntity<?> response = null;
        try {
            List<UserModel> users = userRepo.findByEmail(authentication.getName());
            if (users.size() > 0) {
                if (passwordEncoder.matches(credentials[1], users.get(0).getPassword())) {
                    UserResponse userResponse = UserResponse.builder()
                            .id(users.get(0).getId())
                            .fname(users.get(0).getFname())
                            .lname(users.get(0).getLname())
                            .email(users.get(0).getEmail())
//                            .password(users.get(0).getPassword())
                            .status(users.get(0).getStatus())
                            .createdAt(users.get(0).getCreatedAt())
                            .role(users.get(0).getRole())
                            .roleExpDate(users.get(0).getRoleExpDate())
                            .build();
                    logger.trace("User login success!. loginUser()");
                    response = ResponseEntity.status(HttpStatus.OK).body(userResponse);
                } else {
                    logger.error("User login failed!. loginUser()");
                    response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().title("Unauthorized").error("Invalid username or password").build());
                }
            } else {
                logger.error("User not found!. loginUser()");
                response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().title("Unauthorized").error("Invalid username or password").build());
            }
        } catch (Exception e) {
            logger.error("User login failed!. loginUser() " + e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().title("Internal Server Error").error(e.getMessage()).build());
        }
        return response;
    }

    public Boolean isRoleExpired(String email) {
        ResponseEntity<?> response = null;
        try {
            List<UserModel> users = userRepo.findByEmail(email);
            if (users.size() > 0) {
                if (users.get(0).getRole().getRole().equals(USER_TYPES.premium.name()) && users.get(0).getRoleExpDate().before(new Date())) {
                    logger.error("User role expired!. isRoleExpired()");
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("User role expired failed!. isRoleExpired() " + e.getMessage());
        }

        return false;
    }
}

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        String uname, password = null;
//
//        List<GrantedAuthority> authorities = null;
//        List<UserModel> users = userRepo.findByEmail(username);
//
//        if (users.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        else {
//            uname = users.get(0).getEmail();
//            password = users.get(0).getPassword();
//            authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(users.get(0).getRole()));
//        }
//
//        return new User(uname, password, authorities);
//    }


