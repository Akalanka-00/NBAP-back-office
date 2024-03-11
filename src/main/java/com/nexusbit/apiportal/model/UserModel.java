package com.nexusbit.apiportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_Table")
public class UserModel {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private long userId;
    private String fname;
    private String lname;
    private String email;
    private String userType;
    private String status;
}
