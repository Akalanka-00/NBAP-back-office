package com.nexusbit.apiportal.model.nexusModels.errModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorData {

    private HttpStatus ERR_CODE;
    private String ERR_MSG;


}
