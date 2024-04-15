package com.nexusbit.apiportal.dto.user;

import com.nexusbit.apiportal.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class UserResponse {


    private long id;
    private String fname;
    private String lname;
    private String email;
//    private String password;
    private String status;  //active, pending, restricted
    private Date createdAt;
    private String type;    //standard or premium user
    private RoleModel role;
    private Date roleExpDate;
}
