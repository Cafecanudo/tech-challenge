package com.pixeon.healthcare.examservice.dataprovider.gateway;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;

import java.math.BigDecimal;

public class RecoveryInstituitionFromTokemJWT {

    public static HealthcareInstitutionModel recovery() {
        /*
        tokem = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IldlbGx0b24gZG9zIFNhbnRvcyBCYXJyb3MiLCJpYXQiOjE1MTYyMzkwMjIsInVzZXIiOnsiaWQiOjc4MTU0LCJ1c2VybmFtZSI6IndlbGx0b24ifSwiaW5zdGl0dWl0aW9uIjp7ImlkIjoiNDgiLCJuYW1lIjoiV2VsbHRvbiBJbnN0aXR1aWNhbyIsImNucGoiOiIwOC4yNjMuNDc4LzAwMDEtMjMifX0.Gn3l5N275k0i4E-KbbO3mDZwfm5Ic4C6FTuqLIqLwuQ
         */
        return HealthcareInstitutionModel.builder().id(1).name("Wellton Instituicao").cnpj("08.235.226/0001-89").coin(new BigDecimal(20)).build();
    }
}
