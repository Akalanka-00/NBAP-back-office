package com.nexusbit.apiportal.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusbit.apiportal.constants.enums.request.AUTHENTICATION_MSG_TYPE;
import com.nexusbit.apiportal.constants.enums.request.PROJECT_MSG_TYPE;
import com.nexusbit.apiportal.constants.enums.request.REQUEST_GROUP;
import com.nexusbit.apiportal.model.nexusModels.*;
import com.nexusbit.apiportal.model.nexusModels.errModel.ErrorData;
import com.nexusbit.apiportal.processor.subProcessors.ProjectProcessor;
import com.nexusbit.apiportal.utils.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestProcessor {

    private static final  LoggerService logger = new LoggerService();
    private final ProjectProcessor projectProcessor;
    private final ObjectMapper mapper;

    public Response processSecureRequest(Authentication authentication, HttpServletRequest servletRequest, String json) throws IOException {

        Request request = mapper.readValue(json, Request.class);
        RequestHeader header = request.getHeader();
        REQUEST_GROUP MSG_GRP = REQUEST_GROUP.values()[header.getMSG_GRP()];
        String clientIp = servletRequest.getRemoteAddr();
        ResponseHeader responseHeader = ResponseHeader.builder()
                .CL_IP(header.getCL_IP())
                .VERSION(header.getVERSION())
                .MSG_GRP(header.getMSG_GRP()+1000)
                .MSG_TYP(header.getMSG_TYP()+1000)
                .build();

        Response notFoundResponse = Response.builder()
                .header(responseHeader)
                .build();


        Response response = Response.builder().header(responseHeader).data(null).build();

        if(!Objects.equals(clientIp, header.getCL_IP())){
            response.setData(
                    ResponseBody.builder()
                            .msg("Invalid User Found")
                            .data(ErrorData.builder()
                                    .ERR_CODE(HttpStatus.UNAUTHORIZED)
                                    .ERR_MSG("Invalid User")
                                    .build())
                            .build()
            );
            return response;
        }

        switch (MSG_GRP) {
            case AUTHENTICATION:
                AUTHENTICATION_MSG_TYPE MSG_TYP = AUTHENTICATION_MSG_TYPE.values()[header.getMSG_TYP()];
                break;

            case PROJECT:
                logger.info("Processing project request");
                PROJECT_MSG_TYPE projectMsgType = PROJECT_MSG_TYPE.values()[header.getMSG_TYP()];
                response.setData(projectProcessor.process(projectMsgType, request, authentication));
                return response;

            case QUALIFICATION:
                // Qualification logic
                break;
            default:
                return notFoundResponse;
        }
        return notFoundResponse;


    }

    public Response processPublicRequest(Authentication authentication, String json) throws JsonProcessingException {

        Request request = mapper.readValue(json, Request.class);
        RequestHeader header = request.getHeader();
        REQUEST_GROUP MSG_GRP = REQUEST_GROUP.values()[header.getMSG_GRP()];
        ResponseHeader responseHeader = ResponseHeader.builder()
                .CL_IP(header.getCL_IP())
                .VERSION(header.getVERSION())
                .MSG_GRP(header.getMSG_GRP()+1000)
                .MSG_TYP(header.getMSG_TYP()+1000)
                .build();

        Response notFoundResponse = Response.builder()
                .header(responseHeader)
                .data(
                        ResponseBody.builder()
                                .msg("Not Found")
                                .data(ErrorData.builder()
                                        .ERR_CODE(HttpStatus.NOT_FOUND)
                                        .ERR_MSG("Request not found")
                                        .build())
                                .build()
                )
                .build();


        Response response = Response.builder().header(responseHeader).build();
        String jsonData = mapper.writeValueAsString(request.getData());

        switch (MSG_GRP) {
            case AUTHENTICATION:
                AUTHENTICATION_MSG_TYPE MSG_TYP = AUTHENTICATION_MSG_TYPE.values()[header.getMSG_TYP()];
                switch (MSG_TYP) {
                    case LOGIN:
                        break;

                    case REGISTER:
                        break;

                    case RESET:
                        // Reset logic
                        break;
                    case LOGOUT:
                        // Logout logic
                        break;
                    default:
                        return notFoundResponse;
                }

                break;
            case PROJECT:
                // Project logic
                break;
            case QUALIFICATION:
                // Qualification logic
                break;
            default:
                return notFoundResponse;
        }
        return notFoundResponse;


    }

}



