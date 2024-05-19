package com.nexusbit.apiportal.model.nexusModels;

import com.nexusbit.apiportal.constants.enums.request.REQUEST_GROUP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseHeader {

    private String VERSION;
    private String CL_IP;
    private int MSG_GRP;
    private int MSG_TYP;
}
