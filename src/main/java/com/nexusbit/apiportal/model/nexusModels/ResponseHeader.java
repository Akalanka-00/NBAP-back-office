package com.nexusbit.apiportal.model.nexusModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseHeader {

    @JsonProperty("VERSION")
    private String VERSION;
    @JsonProperty("CL_IP")
    private String CL_IP;
    @JsonProperty("MSG_GRP")
    private int MSG_GRP;
    @JsonProperty("MSG_TYP")
    private int MSG_TYP;
}
