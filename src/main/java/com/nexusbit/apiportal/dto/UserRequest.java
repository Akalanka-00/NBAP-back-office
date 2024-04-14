package com.nexusbit.apiportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {

    private String fname;
    private String lname;
    private String email;
    private String password;
}
