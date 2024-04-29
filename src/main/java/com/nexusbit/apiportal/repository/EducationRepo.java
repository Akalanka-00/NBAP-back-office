package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.EducationModel;
import com.nexusbit.apiportal.model.ExpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepo extends JpaRepository<EducationModel, Long>{

    @Query(value = "SELECT  e.* from education AS e JOIN users AS u ON e.user_id = u.id where u.email = :email", nativeQuery = true)
    public List<EducationModel> retrieveEducation(String email);
}
