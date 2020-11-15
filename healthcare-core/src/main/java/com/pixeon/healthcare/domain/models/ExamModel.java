package com.pixeon.healthcare.domain.models;

import com.pixeon.healthcare.domain.models.enums.Gender;

public class ExamModel {

    private Integer id;
    private HealthcareInstitution healthcareInstitution;
    private String patientName;
    private Integer patientAge;
    private Gender patientGender;
    private String physicianName;
    private Integer physicianCRM;
    private String procedureName;
    private boolean billed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HealthcareInstitution getHealthcareInstitution() {
        return healthcareInstitution;
    }

    public void setHealthcareInstitution(HealthcareInstitution healthcareInstitution) {
        this.healthcareInstitution = healthcareInstitution;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public Gender getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(Gender patientGender) {
        this.patientGender = patientGender;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public Integer getPhysicianCRM() {
        return physicianCRM;
    }

    public void setPhysicianCRM(Integer physicianCRM) {
        this.physicianCRM = physicianCRM;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
    }

    public boolean isNullId() {
        return getId() == null;
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
