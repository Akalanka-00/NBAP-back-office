package com.nexusbit.apiportal.model.nexusModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Request {

    private RequestHeader header;
    private Object data;
}