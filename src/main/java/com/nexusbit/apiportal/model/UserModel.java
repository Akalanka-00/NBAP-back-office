package com.nexusbit.apiportal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")

public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fname;
    private String lname;

    @Column(unique = true)
    private String email;
    private String password;
    private String status;  //active, pending, restricted
    private Date createdAt;
    private Date roleExpDate;

    @ManyToOne
    @JoinColumn(name = "role")
    private RoleModel role;    //admin, standard or premium user
}
