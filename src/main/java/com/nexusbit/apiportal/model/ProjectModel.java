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
@Table(name = "projects")
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "banner")
    private MediaModel banner;
    private Date startDate;
    private Date endDate;
    private String description;
    private boolean isOngoing;
    private boolean canRate;
    private boolean isPrivate;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserModel createdBy;


}
