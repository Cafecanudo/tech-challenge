package com.pixeon.healthcare.examservice.config.entity;

import com.pixeon.healthcare.examservice.config.entity.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer healthcareInstitutionId;
    private String patientName;
    private Integer patientAge;
    @Enumerated(EnumType.STRING)
    private GenderEnum patientGender;
    private String physicianName;
    private Integer physicianCRM;
    private String procedureName;
    private boolean billed;
}
