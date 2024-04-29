package com.nexusbit.apiportal.service;

import com.nexusbit.apiportal.dto.qualification.EducationRequest;
import com.nexusbit.apiportal.dto.qualification.EducationResponse;
import com.nexusbit.apiportal.dto.qualification.ExpRequest;
import com.nexusbit.apiportal.dto.qualification.ExpResponse;
import com.nexusbit.apiportal.model.EducationModel;
import com.nexusbit.apiportal.model.ExpModel;
import com.nexusbit.apiportal.repository.EducationRepo;
import com.nexusbit.apiportal.repository.ExpRepo;
import com.nexusbit.apiportal.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QualificationService {

    private final EducationRepo educationRepo;
    private final ExpRepo expRepo;
    private final UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<?> setExp(Authentication authentication, ExpRequest request){
        ResponseEntity<?> response = null;
        try {
            ExpModel expModel = ExpModel.builder()
                    .title(request.getTitle())
                    .company(request.getCompany())
                    .startMonth(request.getStartMonth())
                    .endMonth(request.getEndMonth())
                    .startYear(request.getStartYear())
                    .endYear(request.getEndYear())
                    .createdAt(new Date())
                    .userId(userRepo.findByEmail(authentication.getName()).get(0))
                    .currentlyWorking(request.isCurrentlyWorking())
                    .locationType(request.getLocationType())
                    .empType(request.getEmpType())
                    .isPrivate(request.isPrivate())
                    .build();

            expRepo.save(expModel);
            logger.trace("Experience details saved successfully!. setExp()");
            response = ResponseEntity.status(201).body("Experience details saved successfully!");

        }catch (Exception e){
            logger.error("Experience details save failed!. "+e.getMessage()+" setExp()");
            response = ResponseEntity.status(500).body(e.getMessage());
        }
        return response;
    }

    public ResponseEntity<?> getExp(Authentication authentication){
        ResponseEntity<?> response = null;
        try {
            List<ExpModel> model = expRepo.retrieveExp(authentication.getName());
            response = ResponseEntity.status(200).body(model.stream().map(this::mapToExpRes));

            logger.trace("Experience details fetched successfully!. getExp()");
        }catch (Exception e){
            logger.error("Experience details fetch failed!. "+e.getMessage()+" getExp()");
            response = ResponseEntity.status(500).body(e.getMessage());
        }
        return response;
    }

    public ResponseEntity<?> setEducation(Authentication authentication, EducationRequest request) {
        ResponseEntity<?> response = null;
        try {
            EducationModel educationModel = EducationModel.builder()
                    .school(request.getSchool())
                    .degree(request.getDegree())
                    .field(request.getField())
                    .activities(request.getActivities())
                    .grade(request.getGrade())
                    .startMonth(request.getStartMonth())
                    .endMonth(request.getEndMonth())
                    .startYear(request.getStartYear())
                    .endYear(request.getEndYear())
                    .createdAt(new Date())
                    .userId(userRepo.findByEmail(authentication.getName()).get(0))
                    .isPrivate(request.isPrivate())
                    .build();

            educationRepo.save(educationModel);
            logger.trace("Education details saved successfully!. setEducation()");
            response = ResponseEntity.status(201).body("Education details saved successfully!");
        } catch (Exception e) {
            logger.error("Education details save failed!. " + e.getMessage() + " setEducation()");
            response = ResponseEntity.status(500).body(e.getMessage());
        }

        return response;
    }

    public ResponseEntity<?> getEducation(Authentication authentication) {
        ResponseEntity<?> response = null;
        try {
            List<EducationModel> model = educationRepo.retrieveEducation(authentication.getName());
            response = ResponseEntity.status(200).body(model.stream().map(this::mapToEduRes));
            logger.trace("Education details fetched successfully!. getEducation()");
        } catch (Exception e) {
            logger.error("Education details fetch failed!. " + e.getMessage() + " getEducation()");
            response = ResponseEntity.status(500).body(e.getMessage());
        }
        return response;
    }

    private EducationResponse mapToEduRes(EducationModel educationModel) {
        return EducationResponse.builder()
                .id(educationModel.getId())
                .school(educationModel.getSchool())
                .degree(educationModel.getDegree())
                .field(educationModel.getField())
                .activities(educationModel.getActivities())
                .grade(educationModel.getGrade())
                .startMonth(educationModel.getStartMonth())
                .endMonth(educationModel.getEndMonth())
                .startYear(educationModel.getStartYear())
                .endYear(educationModel.getEndYear())
                .createdAt(educationModel.getCreatedAt())
                .userId(educationModel.getUserId().getId())
                .isPrivate(educationModel.isPrivate())
                .build();
    }

    private ExpResponse mapToExpRes(ExpModel model){
        return ExpResponse.builder()
                .id(model.getId())
                .title(model.getTitle())
                .company(model.getCompany())
                .startMonth(model.getStartMonth())
                .endMonth(model.getEndMonth())
                .startYear(model.getStartYear())
                .endYear(model.getEndYear())
                .createdAt(model.getCreatedAt())
                .currentlyWorking(model.isCurrentlyWorking())
                .locationType(model.getLocationType())
                .empType(model.getEmpType())
                .userId(model.getUserId().getId())
                .isPrivate(model.isPrivate())
                .build();
    }
}
