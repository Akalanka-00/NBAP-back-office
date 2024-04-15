package com.nexusbit.apiportal.dto.project;

import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectResponse {

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
