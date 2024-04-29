package com.nexusbit.apiportal.dto.qualification;

import com.nexusbit.apiportal.model.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EducationResponse {


    private long id;
    private String school;
    private String degree;
    private String field;
    private  String activities;
    private String grade;
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;
    private Date createdAt;
    private long userId;
    private boolean isPrivate;


}
