package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<ProjectModel, String> {
}
