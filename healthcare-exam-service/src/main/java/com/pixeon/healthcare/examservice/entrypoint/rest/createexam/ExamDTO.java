package com.pixeon.healthcare.examservice.entrypoint.rest.createexam;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class ExamDTO {

    private Integer id;
    private Integer healthcareInstitutionId;
    @NotNull
    @Size(max = 100)
    private String patientName;
    @NotNull
    @Min(0)
    private Integer patientAge;
    @NotNull
    @Pattern(regexp = "^(MALE|FEMALE)$")
    private String patientGender;
    @NotNull
    @Size(max = 100)
    private String physicianName;
    @NotNull
    private Integer physicianCRM;
    @NotNull
    @Size(max = 100)
    private String procedureName;
    private boolean billed;
}
