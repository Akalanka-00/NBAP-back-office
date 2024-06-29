package com.nexusbit.apiportal.model.nexusModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Response {

    private ResponseHeader header;
    private ResponseBody data;
}