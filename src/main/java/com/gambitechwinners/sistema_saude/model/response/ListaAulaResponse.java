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
public class ListaAulaResponse {
    private Long id;
    private String nomeProcedimento;
    private Date dateProcedimento;
}
