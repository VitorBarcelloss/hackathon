package com.gambitechwinners.sistema_saude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gambitechwinners.sistema_saude.model.request.PeriodoRequest;
import com.gambitechwinners.sistema_saude.model.response.RelatorioGeral;
import com.gambitechwinners.sistema_saude.model.response.RelatorioProcedimento;
import com.gambitechwinners.sistema_saude.service.RelatoriosService;

@CrossOrigin("*")
@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {
    @Autowired 
    RelatoriosService relatoriosService;

    @GetMapping("/gerarRelatorioGeral")
    public ResponseEntity<RelatorioGeral> gerarRelatorioGeral(){
        RelatorioGeral relatorioGeral = relatoriosService.gerarRelatorioGeral();

        return new ResponseEntity<>(relatorioGeral, HttpStatus.OK);
    }

    @GetMapping("/gerarRelatorioPeriodo")
    public ResponseEntity<RelatorioGeral> gerarRelatorioPeriodo(@RequestBody PeriodoRequest periodoRequest){
        RelatorioGeral relatorioGeral = relatoriosService.gerarRelatorioPeriodo(periodoRequest);

        return new ResponseEntity<>(relatorioGeral, HttpStatus.OK);
    }

    @GetMapping("/gerarRelatorioProcedimento/{aulaId}")
    public ResponseEntity<RelatorioProcedimento> gerarRelatorioProcedimento(@PathVariable Long aulaId){
        RelatorioProcedimento relatorioProcedimento = relatoriosService.gerarRelatorioProcedimento(aulaId);

        return new ResponseEntity<>(relatorioProcedimento, HttpStatus.OK);
    }

}
