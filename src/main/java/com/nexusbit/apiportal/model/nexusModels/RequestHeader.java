package com.nexusbit.apiportal.model.nexusModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.nexusbit.apiportal.constants.enums.request.REQUEST_GROUP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RequestHeader {

    @JsonProperty("VERSION")
    private String VERSION;
    @JsonProperty("CL_IP")
    private String CL_IP;
    @JsonProperty("MSG_GRP")
    private int MSG_GRP;
    @JsonProperty("MSG_TYP")
    private int MSG_TYP;

}
