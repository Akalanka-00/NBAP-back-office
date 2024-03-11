package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, Long> {
}
