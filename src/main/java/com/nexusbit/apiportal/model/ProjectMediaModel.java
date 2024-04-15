package com.nexusbit.apiportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "project_media")
public class ProjectMediaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectModel project;

    @ManyToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private MediaModel media;
}
