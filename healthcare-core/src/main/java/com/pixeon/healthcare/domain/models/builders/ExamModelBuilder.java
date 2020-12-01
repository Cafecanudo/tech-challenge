package com.pixeon.healthcare.domain.models.builders;

import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.domain.models.enums.Gender;

public final class ExamModelBuilder {

    private Integer id;
    private String patientName;
    private int patientAge;
    private Gender patientGender;
    private String physicianName;
    private int physicianCRM;
    private String procedureName;
    private HealthcareInstitutionDTO healthcareInstitutionDTO;

    private ExamModelBuilder() {
    }

    public static ExamModelBuilder Builder() {
        return new ExamModelBuilder();
    }

    public ExamModelBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ExamModelBuilder withPatientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public ExamModelBuilder withPatientAge(int patientAge) {
        this.patientAge = patientAge;
        return this;
    }

    public ExamModelBuilder withPatientGender(Gender patientGender) {
        this.patientGender = patientGender;
        return this;
    }

    public ExamModelBuilder withPhysicianName(String physicianName) {
        this.physicianName = physicianName;
        return this;
    }

    public ExamModelBuilder withPhysicianCRM(int physicianCRM) {
        this.physicianCRM = physicianCRM;
        return this;
    }

    public ExamModelBuilder withProcedureName(String procedureName) {
        this.procedureName = procedureName;
        return this;
    }

    public ExamModelBuilder withHealthcareInstitution(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        this.healthcareInstitutionDTO = healthcareInstitutionDTO;
        return this;
    }

    public ExamModel build() {
        ExamModel examModel = new ExamModel();
        examModel.setId(id);
        examModel.setPatientName(patientName);
        examModel.setPatientAge(patientAge);
        examModel.setPatientGender(patientGender);
        examModel.setPhysicianName(physicianName);
        examModel.setPhysicianCRM(physicianCRM);
        examModel.setProcedureName(procedureName);
        examModel.setHealthcareInstitution(healthcareInstitutionDTO);
        return examModel;
    }
}
