package com.nexusbit.apiportal.repository;

import com.nexusbit.apiportal.model.MediaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepo extends JpaRepository<MediaModel, String> {

}
