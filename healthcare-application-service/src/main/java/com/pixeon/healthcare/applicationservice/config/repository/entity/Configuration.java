package com.pixeon.healthcare.applicationservice.config.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Configuration {

    @Id
    private Integer id;
    private BigDecimal valueForNewHealthcareInstitution;
    private BigDecimal valueForCreateExam;
    private BigDecimal valueForConsultingExam;

}
