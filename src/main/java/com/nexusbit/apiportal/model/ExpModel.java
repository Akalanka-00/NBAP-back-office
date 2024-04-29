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
@Table(name = "experience")
public class ExpModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userId;


}
