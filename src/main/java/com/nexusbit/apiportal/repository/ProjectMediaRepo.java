package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.ProjectMediaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMediaRepo extends JpaRepository<ProjectMediaModel, Long> {

    List<ProjectMediaModel> findByProjectId(String projectId);
}
