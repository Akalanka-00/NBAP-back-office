package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.UserActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepo extends JpaRepository<UserActivityModel, Long> {
}
