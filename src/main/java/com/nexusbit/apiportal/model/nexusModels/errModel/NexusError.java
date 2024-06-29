package com.nexusbit.apiportal.model.nexusModels.errModel;

import com.nexusbit.apiportal.model.nexusModels.ResponseHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NexusError {

    private ResponseHeader HEADER;
    private ErrorData DATA;
}
