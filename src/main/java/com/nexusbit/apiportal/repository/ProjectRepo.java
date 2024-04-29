package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.dto.project.ProjectResponse;
import com.nexusbit.apiportal.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<ProjectModel, String> {

    @Query(value = "SELECT p.* from projects AS p JOIN users AS u ON p.created_by = u.id where u.email = :email", nativeQuery = true)
    public List<ProjectModel> retrieveProjects(@Param("email") String email);

    @Query(value = "SELECT p.* from projects AS p JOIN users AS u ON p.created_by = u.id where u.email = :email AND p.id = :project_id", nativeQuery = true)
    public ProjectModel retrieveProject(@Param("email") String email, @Param("project_id") String id);
}
