package com.gambitechwinners.sistema_saude.model.response;

import java.util.Date;
import java.util.List;

import com.gambitechwinners.sistema_saude.model.entity.InsumosExtra;
import com.gambitechwinners.sistema_saude.model.entity.Procedimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelatorioProcedimento {
    private Procedimento procedimento;
    private Date dataProcedimento;
    private int quantidadeInsumos;
    private List<InsumosExtra> insumosExtra;
    private int quantidadeInsumosExtra;
    private double custoTotalProcedimento;    
}
