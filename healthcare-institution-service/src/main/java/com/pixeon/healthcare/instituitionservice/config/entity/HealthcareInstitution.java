package com.pixeon.healthcare.instituitionservice.config.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthcareInstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String cnpj;
    private BigDecimal coin;
    @OneToMany(mappedBy = "healthcareInstitution")
    private List<Coin> coins;

}
