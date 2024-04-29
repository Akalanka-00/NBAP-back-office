package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.AuthorityModel;
import com.nexusbit.apiportal.model.ExpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpRepo extends JpaRepository<ExpModel, Long>{

    @Query(value = "SELECT  e.* from experience AS e JOIN users AS u ON e.user_id = u.id where u.email = :email", nativeQuery = true)
    public List<ExpModel> retrieveExp(String email);

}
