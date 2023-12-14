package com.gambitechwinners.sistema_saude.model.response;

import java.util.List;

import com.gambitechwinners.sistema_saude.model.entity.Procedimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocenteResponse {
    private Long id;
    private String nome;
	private List<Procedimento> procedimentos;
}
