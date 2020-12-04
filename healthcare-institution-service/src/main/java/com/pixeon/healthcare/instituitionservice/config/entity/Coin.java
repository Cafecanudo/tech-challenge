package com.pixeon.healthcare.instituitionservice.config.entity;

import com.pixeon.healthcare.instituitionservice.config.entity.enums.OperationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal currentBalance;
    private BigDecimal newBalance;
    @Enumerated(EnumType.STRING)
    private OperationEnum operation;
    private BigDecimal valueOperation;
    private Date dateOperation;
    @ManyToOne
    @JoinColumn(name = "healthcare_institution_id", nullable = false)
    private HealthcareInstitution healthcareInstitution;

}
