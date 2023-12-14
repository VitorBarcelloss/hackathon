package com.gambitechwinners.sistema_saude.model.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeriodoRequest {
    private Date dataInicio;
    private Date dateFinal;
}
