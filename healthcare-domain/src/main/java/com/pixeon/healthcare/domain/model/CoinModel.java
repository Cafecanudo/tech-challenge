package com.pixeon.healthcare.domain.model;

import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class CoinModel {

    private Integer id;
    private BigDecimal currentBalance;
    private BigDecimal newBalance;
    private OperationEnum operation;
    private Date dateOperation;
}
