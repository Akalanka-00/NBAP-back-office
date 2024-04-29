package com.nexusbit.apiportal.model;

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
@Entity
@Table(name = "education")
public class EducationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userId;


}
