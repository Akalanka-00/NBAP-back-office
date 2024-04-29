package com.nexusbit.apiportal.dto.qualification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExpResponse {

    private long id;
    private String title;
    private String empType;
    private String company;
    private String locationType;
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;
    private  boolean currentlyWorking;
    private Date createdAt;
    private long userId;
    private boolean isPrivate;


}
