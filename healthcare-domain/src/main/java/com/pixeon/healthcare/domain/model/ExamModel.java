package com.pixeon.healthcare.domain.model;

import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamModel {

    private Integer id;
    private HealthcareInstitutionModel healthcareInstitution;
    private String patientName;
    private Integer patientAge;
    private GenderEnum patientGender;
    private String physicianName;
    private Integer physicianCRM;
    private String procedureName;
    private boolean billed;

    public boolean isNullId() {
        return getId() == null;
    }

    public boolean alreadyBeenCharged() {
        return isBilled();
    }

    public boolean isEmptyFields() {
        return this.getPatientName() == null ||
                this.getPatientName().isEmpty() ||
                this.getProcedureName() == null ||
                this.getProcedureName().isEmpty() ||
                this.getPhysicianName() == null ||
                this.getPhysicianName().isEmpty() ||
                this.getPatientAge() == null ||
                this.getPatientGender() == null ||
                this.getPhysicianCRM() == null;
    }
}
