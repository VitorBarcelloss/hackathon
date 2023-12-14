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
@Setter
@Getter
public class AulaResponse {
    private Long id;
    private Procedimento tipoDeProcedimento;
    private Date dateProcedimento;
    private Integer quantidadeAlunos;
    private List<InsumosExtra> insumosExtra;
}
