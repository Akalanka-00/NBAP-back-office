package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<RoleModel, String> {
}
