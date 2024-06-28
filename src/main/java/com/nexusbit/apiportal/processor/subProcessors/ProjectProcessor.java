package com.nexusbit.apiportal.processor.subProcessors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusbit.apiportal.constants.enums.request.PROJECT_MSG_TYPE;
import com.nexusbit.apiportal.dto.project.ProjectRequest;
import com.nexusbit.apiportal.model.nexusModels.Request;
import com.nexusbit.apiportal.model.nexusModels.ResponseBody;
import com.nexusbit.apiportal.model.nexusModels.errModel.ErrorData;
import com.nexusbit.apiportal.service.ProjectService;
import com.nexusbit.apiportal.utils.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ProjectProcessor {

    private final ProjectService projectService;
    private final ObjectMapper mapper;
    private final LoggerService logger;
    public ResponseBody process(PROJECT_MSG_TYPE msgType, Request request, Authentication authentication) throws JsonProcessingException {

        ResponseBody notFoundResponse = ResponseBody.builder()
                .msg("Not Found")
                .data(ErrorData.builder().ERR_CODE(HttpStatus.NOT_FOUND).ERR_MSG("Request not found").build())
                .build();

        switch (msgType) {
            case CREATE:
                String data = mapper.writeValueAsString(request.getData());
                ProjectRequest projectRequest = mapper.readValue(data, ProjectRequest.class);
                return ResponseBody.builder().msg("Project created successfully").data(projectService.newProject(authentication, projectRequest)).build();

            case UPDATE:
                // Update logic
                break;
            case DELETE:
                // Delete logic
                break;
            case VIEW_ALL_ON_PORTAL:
                // View all on portal logic
                break;
            case VIEW_ON_PORTAL:
                // View on portal logic
                break;
            case VIEW_ALL_ON_API:
                // View all on api logic
                break;
            case VIEW_ON_API:
                // View on api logic
                break;
            default:
                logger.error("Msg Type was not found");
                return notFoundResponse;
        }
        return ResponseBody.builder().msg("Project request processed").build();
    }
}
