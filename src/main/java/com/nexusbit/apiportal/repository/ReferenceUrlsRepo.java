package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceUrlsRepo extends JpaRepository<ReferenceUrlsModel, Integer> {

    List<ReferenceUrlsModel> findByProjectId(String projectId);
}
