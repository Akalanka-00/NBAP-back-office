package com.nexusbit.apiportal.dto.project;

import com.nexusbit.apiportal.model.MediaModel;
import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectRequest {

    private String name;
    private String banner;
    private Date startDate;
    private Date endDate;
    private String description;
    private boolean isOngoing;
    private boolean canRate;
    private boolean isPrivate;
    private String[] mediaFiles;
    private ReferenceUrlsModel[] referenceUrls;
}
