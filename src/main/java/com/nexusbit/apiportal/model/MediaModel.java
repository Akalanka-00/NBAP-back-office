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
@Table(name = "media")
public class MediaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String url;
    private Date createdAt;
}
