package com.pixeon.healthcare.models;

import com.pixeon.healthcare.models.enums.Gender;

public class ExamModel {

    private Integer id;
    private HealthcareInstitution healthcareInstitution;
    private String patientName;
    private Integer patientAge;
    private Gender patientGender;
    private String physicianName;
    private Integer physicianCRM;
    private String procedureName;

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
}
