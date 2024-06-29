package com.nexusbit.apiportal.service;

import com.nexusbit.apiportal.constants.enums.MEDIA_CATEGORY;
import com.nexusbit.apiportal.dto.project.ProjectRequest;
import com.nexusbit.apiportal.dto.project.ProjectResponse;
import com.nexusbit.apiportal.model.MediaModel;
import com.nexusbit.apiportal.model.ProjectMediaModel;
import com.nexusbit.apiportal.model.ProjectModel;
import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import com.nexusbit.apiportal.model.nexusModels.ResponseBody;
import com.nexusbit.apiportal.model.nexusModels.errModel.ErrorData;
import com.nexusbit.apiportal.repository.*;
import com.nexusbit.apiportal.utils.FileService;
import com.nexusbit.apiportal.utils.LoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private static final LoggerService logger = new LoggerService() ;

    public ResponseBody newProject(Authentication authentication, ProjectRequest request)  {
        ResponseBody response;
        try {

            MediaModel banner = mediaRepo.save(
                    MediaModel.builder()
                            .url(fileService.save(request.getBanner(), authentication.getName(), MEDIA_CATEGORY.BANNER  .name()))
                            .createdAt(new Date())
                            .build());

            ProjectModel project = ProjectModel.builder()
                    .name(request.getName())
                    .banner(banner)
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
                List<String> urls = Arrays.stream(request.getMediaFiles()).toList().stream().map(media->{
                    try {
                        return fileService.save(media, authentication.getName(), MEDIA_CATEGORY.PROJECT_MEDIA.name());
                    } catch (Exception e) {
                        logger.error("Media file save failed!. "+e.getMessage()+" newProject()");
                        return null;
                    }

                }).toList();
                List<ProjectMediaModel> projectMedia = urls.stream().map(url -> ProjectMediaModel.builder()
                        .project(savedProject)
                        .media(mediaRepo.save(MediaModel.builder().url(url).createdAt(new Date()).build()))
                        .build()).toList();

                projectMediaRepo.saveAll(projectMedia);
                logger.trace("Media files saved successfully!. newProject()");
            }

            logger.info("Project Creation Successfully");
            response = ResponseBody.builder().msg("Project creation successful").data(mapToResponse(savedProject)).build();


        }catch (Exception e){
            logger.error("Project save failed!. "+e.getMessage()+" newProject()");
            response = ResponseBody.builder()
                    .msg("Error Occurred")
                    .data(ErrorData.builder()
                            .ERR_MSG(e.getMessage())
                            .ERR_CODE(HttpStatus.INTERNAL_SERVER_ERROR).build())
                    .build();



        }
        return response;
    }

    public ResponseBody retrieveProjects(Authentication authentication){
        ResponseBody response = ResponseBody.builder().build();
        try {

            List<ProjectModel> projects = projectRepo.retrieveProjects(authentication.getName());
            List<ProjectResponse> responses = projects.stream().map(this::mapToResponse).toList();

            logger.info("Projects retrieved successfully");
            response.setMsg("Projects retrieved successfully");
            response.setData(responses);

        }catch (Exception e){
            logger.error("Project save failed!. "+e.getMessage());
            ErrorData error = ErrorData.builder().ERR_MSG("Project save failed!. "+e.getMessage()).ERR_CODE(HttpStatus.INTERNAL_SERVER_ERROR).build();
            response.setMsg("Project save failed!. "+e.getMessage());
            response.setData(error);        }
        return response;
    }

    public ResponseEntity<?> retrieveProject(String id, Authentication authentication){
        ResponseEntity<?> response = null;
        try {
            ProjectModel project = projectRepo.retrieveProject(authentication.getName(), id);
            if(project == null){

                ProjectModel temp = projectRepo.findById(id).orElse(null);
                if(temp == null){
                    logger.error("Project not found!. retrieveProject()");
                    return ResponseEntity.status(404).body("Project not found");
                }

                else{
                    return ResponseEntity.status(403).body("Unauthorized access");
                }

            }else{
                ProjectResponse projectResponse = mapToResponse(project);
                logger.trace("Project retrieved successfully!. retrieveProject()");
                response = ResponseEntity.status(200).body(projectResponse);
            }

        }catch (Exception e){
            logger.error("Project retrieve failed!. "+e.getMessage()+" retrieveProject()");
            response = ResponseEntity.status(500).body(e.getMessage());
        }
        return response;
    }

    private ProjectResponse mapToResponse(ProjectModel project){
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .banner(mediaRepo.getReferenceById(project.getBanner().getId()))
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .description(project.getDescription())
                .isOngoing(project.isOngoing())
                .canRate(project.isCanRate())
                .isPrivate(project.isPrivate())
                .createdAt(project.getCreatedAt())
                .createdBy(project.getCreatedBy().getId())
                .referenceUrls(referenceUrlsRepo.findByProjectId(project.getId()))
                .mediaFiles(getMediaFiles(projectMediaRepo.findByProjectId(project.getId())))
                .build();
    }

    private List<MediaModel> getMediaFiles(List<ProjectMediaModel> projectMedia){
        return projectMedia.stream().map(media -> mediaRepo.getReferenceById(media.getMedia().getId())).toList();

    }
}
