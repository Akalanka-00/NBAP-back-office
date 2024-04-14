package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepo extends JpaRepository<UserModel, Long> {

    List<UserModel> findByEmail(String email);

//    @Query(value = "SELECT a.authority FROM authorities a INNER JOIN roles r ON a.role = r.role INNER JOIN users u ON r.role = u.role WHERE u.email = :userEmail", nativeQuery = true)

    @Query(value = "CALL getUserAuthorities(:userEmail);", nativeQuery = true)
    List<String> getAuthorities(@Param("userEmail") String userEmail);
}
