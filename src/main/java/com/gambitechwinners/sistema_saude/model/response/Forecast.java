package com.gambitechwinners.sistema_saude.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Forecast {
    private Double custoPrevisto;
    private Long insumosPrevistos;
    private Date dataInicio;
    private Date dataFinal;
}
