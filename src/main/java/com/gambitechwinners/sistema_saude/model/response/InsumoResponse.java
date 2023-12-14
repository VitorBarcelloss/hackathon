package com.gambitechwinners.sistema_saude.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsumoResponse {
    private Long id;
    private String nomeInsumo;
    private Double precoInsumo;
    private int quantidade;
}
