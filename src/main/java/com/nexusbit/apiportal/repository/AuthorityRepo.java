package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.AuthorityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<AuthorityModel, Long>{
}
