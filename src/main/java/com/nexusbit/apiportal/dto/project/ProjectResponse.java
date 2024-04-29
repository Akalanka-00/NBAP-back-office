package com.nexusbit.apiportal.dto.project;

import com.nexusbit.apiportal.model.MediaModel;
import com.nexusbit.apiportal.model.ReferenceUrlsModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectResponse {

    private String id;
    private String name;
    private MediaModel banner;
    private Date startDate;
    private Date endDate;
    private String description;
    private boolean isOngoing;
    private boolean canRate;
    private boolean isPrivate;
    private List<MediaModel> mediaFiles;
    private List<ReferenceUrlsModel> referenceUrls;
    private Date createdAt;
    private long createdBy;
}
