package com.gambitechwinners.sistema_saude.model.response;

import java.util.List;

import com.gambitechwinners.sistema_saude.model.entity.Insumos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcedimentoResponse {
    private Long id;
    private String nomeProcedimento;
	private List<Insumos> insumos;
    private Double custoProcedimento;	
}
