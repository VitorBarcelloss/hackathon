package com.gambitechwinners.sistema_saude.model.response;

import java.util.List;

import com.gambitechwinners.sistema_saude.model.entity.InsumosExtra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RelatorioGeral {
    private List<String> procedimentos;
    private int quantidadeInsumos;
    private List<InsumosExtra> insumosExtra;
    private int quantidadeInsumosExtra;
    private double mediaInsumosExtraPorProcedimento;
    private double custoMedioDosProcedimentos; 
    private double custoTotalDosProcedimentos;   

  //relatorio de uso extra por procedimento e periodo
  //fluxo de estoque
}
