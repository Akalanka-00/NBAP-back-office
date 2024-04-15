package com.nexusbit.apiportal.service;

import com.nexusbit.apiportal.dto.project.ProjectRequest;
import com.nexusbit.apiportal.model.MediaModel;
import com.nexusbit.apiportal.model.ProjectMediaModel;
import com.nexusbit.apiportal.model.ProjectModel;
import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import com.nexusbit.apiportal.repository.*;
import com.nexusbit.apiportal.utils.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final FileService fileService;
    private final ProjectRepo projectRepo;
    private final ReferenceUrlsRepo referenceUrlsRepo;
    private final MediaRepo mediaRepo;
    private final ProjectMediaRepo projectMediaRepo;
    private final UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<?> newProject(Authentication authentication, ProjectRequest request) {
        ResponseEntity<?> response = null;
        try {
            ProjectModel project = ProjectModel.builder()
                    .name(request.getName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .description(request.getDescription())
                    .isOngoing(request.isOngoing())
                    .canRate(request.isCanRate())
                    .isPrivate(request.isPrivate())
                    .createdAt(new Date())
                    .createdBy(userRepo.findByEmail(authentication.getName()).get(0))
                    .build();

            ProjectModel savedProject = projectRepo.save(project);
            logger.trace("Project details saved successfully!. newProject()");

            if(request.getReferenceUrls() != null && request.getReferenceUrls().length > 0){
                List<ReferenceUrlsModel> referenceUrls = Arrays.stream(request.getReferenceUrls()).toList().stream().map(url -> ReferenceUrlsModel.builder()
                        .project(savedProject)
                        .hostname(url.getHostname())
                        .url(url.getUrl())
                        .build()).toList();

                referenceUrlsRepo.saveAll(referenceUrls);
                logger.trace("Reference URLs saved successfully!. newProject()");
            }

            if(request.getMediaFiles() != null && request.getMediaFiles().length > 0){
                List<String> urls = Arrays.stream(request.getMediaFiles()).toList().stream().map(fileService::base64ToUrl).toList();
                List<ProjectMediaModel> projectMedia = urls.stream().map(url -> ProjectMediaModel.builder()
                        .project(savedProject)
                        .media(mediaRepo.save(MediaModel.builder().url(url).createdAt(new Date()).build()))
                        .build()).toList();

                projectMediaRepo.saveAll(projectMedia);
                logger.trace("Media files saved successfully!. newProject()");
            }
            response = ResponseEntity.status(201).body(savedProject);


        }catch (Exception e){
            logger.error("Project save failed!. "+e.getMessage()+" newProject()");

        }

        return response;
    }
}
