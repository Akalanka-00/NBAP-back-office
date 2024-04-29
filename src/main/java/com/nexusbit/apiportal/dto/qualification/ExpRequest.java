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
public class ExpRequest {


    private String title;
    private String empType;
    private String company;
    private String locationType;
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;
    private  boolean currentlyWorking;
    private boolean isPrivate;


}
