package com.gambitechwinners.sistema_saude.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsumoRequest {
    private String nomeInsumo;
    private Double precoInsumo;
    private int quantidade;
}
