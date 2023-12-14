package com.gambitechwinners.sistema_saude.model.request;

import java.util.Date;

import com.gambitechwinners.sistema_saude.model.entity.Procedimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AulaRequest {
    private Procedimento tipoDeProcedimento;
    private Date dateProcedimento;
}
