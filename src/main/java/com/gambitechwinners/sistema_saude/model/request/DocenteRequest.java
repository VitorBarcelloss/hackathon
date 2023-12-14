package com.gambitechwinners.sistema_saude.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocenteRequest {
    private String nome;
	private List<String> procedimentos;
}
