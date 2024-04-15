package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceUrlsRepo extends JpaRepository<ReferenceUrlsModel, Integer> {
}
