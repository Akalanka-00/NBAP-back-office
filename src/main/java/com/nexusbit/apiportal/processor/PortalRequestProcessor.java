package com.nexusbit.apiportal.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusbit.apiportal.constants.enums.request.AUTHENTICATION_MSG_TYPE;
import com.nexusbit.apiportal.constants.enums.request.REQUEST_GROUP;
import com.nexusbit.apiportal.dto.user.UserRequest;
import com.nexusbit.apiportal.model.nexusModels.*;
import com.nexusbit.apiportal.model.nexusModels.errModel.ErrorData;
import com.nexusbit.apiportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalRequestProcessor{

    private  final UserService userService;

    public ResponseModel  processSecureRequest(Authentication authentication, String json) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        RequestModel request = mapper.readValue(json, RequestModel.class);
        RequestHeader header = request.getHeader();
        REQUEST_GROUP MSG_GRP = REQUEST_GROUP.values()[header.getMSG_GRP()];
        ResponseHeader responseHeader = ResponseHeader.builder()
                .CL_IP(header.getCL_IP())
                .VERSION(header.getVERSION())
                .MSG_GRP(header.getMSG_GRP()+1000)
                .MSG_TYP(header.getMSG_TYP()+1000)
                .build();

        ResponseModel notFoundResponse = ResponseModel.builder()
                .header(responseHeader)
                .body(
                        ResponseBodyModel.builder()
                                .msg("Not Found")
                                .data(ErrorData.builder()
                                        .ERR_CODE(HttpStatus.NOT_FOUND)
                                        .ERR_MSG("Request not found")
                                        .build())
                                .build()
                )
                .build();


        ResponseModel response = ResponseModel.builder().header(responseHeader).build();

        switch (MSG_GRP) {
            case AUTHENTICATION:
                AUTHENTICATION_MSG_TYPE MSG_TYP = AUTHENTICATION_MSG_TYPE.values()[header.getMSG_TYP()];
                switch (MSG_TYP) {
                    case LOGIN:
                        // Login logic
                        break;
                    case REGISTER:
                        // Register logic
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

    public ResponseModel  processPublicRequest(Authentication authentication, String json) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        RequestModel request = mapper.readValue(json, RequestModel.class);
        RequestHeader header = request.getHeader();
        REQUEST_GROUP MSG_GRP = REQUEST_GROUP.values()[header.getMSG_GRP()];
        ResponseHeader responseHeader = ResponseHeader.builder()
                .CL_IP(header.getCL_IP())
                .VERSION(header.getVERSION())
                .MSG_GRP(header.getMSG_GRP()+1000)
                .MSG_TYP(header.getMSG_TYP()+1000)
                .build();

        ResponseModel notFoundResponse = ResponseModel.builder()
                .header(responseHeader)
                .body(
                        ResponseBodyModel.builder()
                                .msg("Not Found")
                                .data(ErrorData.builder()
                                        .ERR_CODE(HttpStatus.NOT_FOUND)
                                        .ERR_MSG("Request not found")
                                        .build())
                                .build()
                )
                .build();


        ResponseModel response = ResponseModel.builder().header(responseHeader).build();
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



