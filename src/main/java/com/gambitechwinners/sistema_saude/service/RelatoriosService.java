package com.gambitechwinners.sistema_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gambitechwinners.sistema_saude.errors.exception.ResourceNotFoundException;
import com.gambitechwinners.sistema_saude.model.entity.Aula;
import com.gambitechwinners.sistema_saude.model.entity.Insumos;
import com.gambitechwinners.sistema_saude.model.entity.InsumosExtra;
import com.gambitechwinners.sistema_saude.model.entity.Procedimento;
import com.gambitechwinners.sistema_saude.model.request.PeriodoRequest;
import com.gambitechwinners.sistema_saude.model.response.RelatorioGeral;
import com.gambitechwinners.sistema_saude.model.response.RelatorioProcedimento;
import com.gambitechwinners.sistema_saude.repository.AulaRepository;

@Service
public class RelatoriosService {
    @Autowired
    AulaRepository aulaRepository;

    public RelatorioProcedimento gerarRelatorioProcedimento(Long aulaId){
        Aula aula = aulaRepository.findById(aulaId).orElseThrow(() -> new ResourceNotFoundException("Aula não encontrada!"));

        return criarRelatorioProcedimento(aula);
    }

    public RelatorioGeral gerarRelatorioGeral(){
        List<Aula> aulas = aulaRepository.findAll();

        return criarRelatorioGeral(aulas);
    }

    public RelatorioGeral gerarRelatorioPeriodo(PeriodoRequest periodoRequest){
        List<Aula> aulas = aulaRepository.findByPeriodo(periodoRequest.getDataInicio(), periodoRequest.getDateFinal());

        return criarRelatorioGeral(aulas);
    }

//------------------------------------------------------------------------------------------------------

    public RelatorioProcedimento criarRelatorioProcedimento(Aula aula){
        int quantidadeInsumos = (aula.getQuantidadeAlunos() * aula.getTipoDeProcedimento().getInsumos().size()) + aula.getInsumosExtra().size(); 
        double custoTotalProcedimento = calcularCustoTotal(aula);

        return new RelatorioProcedimento(
            aula.getTipoDeProcedimento(),
            aula.getDateProcedimento(),
            quantidadeInsumos,
            aula.getInsumosExtra(),
            aula.getInsumosExtra().size(),
            custoTotalProcedimento
            );
    }

    public RelatorioGeral criarRelatorioGeral(List<Aula> aulas){
        List<Procedimento> procedimentos = buscarProcedimentos(aulas);
        Integer quantidadeInsumos = calcularQuantidadeInsumos(aulas);
        List<InsumosExtra> insumosExtras = criarListaDeExtras(aulas);
        Integer quantidadeExtras = calcularQuantidadeExtras(aulas);
        Double mediaExtraPorProcedimento = calcularMediaDeExtraPorProcedimento(aulas);
        Double custoMedioDosProcedimentos = calcularCustoMedio(aulas);
        Double custoTotalProcedimentos = calcularCustoTotal(aulas);

        return new RelatorioGeral(
            procedimentos.stream().map(a -> a.getNomeProcedimento()).toList(),
            quantidadeInsumos,
            insumosExtras,
            quantidadeExtras,
            mediaExtraPorProcedimento,
            custoMedioDosProcedimentos,
            custoTotalProcedimentos
        );
    }

     public double calcularCustoTotal(Aula aula){
        double custoTotalProcedimento = 0;
        for(InsumosExtra i: aula.getInsumosExtra()){
            custoTotalProcedimento = custoTotalProcedimento + i.getInsumo().getPrecoInsumo();
        }
        for(Insumos i: aula.getTipoDeProcedimento().getInsumos()){
            custoTotalProcedimento = custoTotalProcedimento + i.getPrecoInsumo();
        }

        return custoTotalProcedimento;
    }

    public Integer calcularQuantidadeInsumos(List<Aula> aulas){
        int quantidadeInsumos = 0;
        for(Aula a: aulas){
            quantidadeInsumos = quantidadeInsumos + (a.getQuantidadeAlunos() * a.getTipoDeProcedimento().getInsumos().size()) + a.getInsumosExtra().size();
        }
        return quantidadeInsumos;
    }

    public List<Procedimento> buscarProcedimentos(List<Aula> aulas){
        List<Procedimento> procedimentos = new ArrayList<>();
        for(Aula a:aulas){
            procedimentos.add(a.getTipoDeProcedimento());
        }

        return procedimentos;
    }

    public List<InsumosExtra> criarListaDeExtras(List<Aula> aulas){
        List<InsumosExtra> listaInsumosExtras = new ArrayList<>();
        for(Aula a: aulas){
            listaInsumosExtras.addAll(a.getInsumosExtra());
        }
        return listaInsumosExtras;
    }

    public Integer calcularQuantidadeExtras(List<Aula> aulas){
        int quantidadeExtras = 0;
        for(Aula a: aulas){
            quantidadeExtras = quantidadeExtras + a.getInsumosExtra().size();
        }
        return quantidadeExtras;
    }

    public Double calcularMediaDeExtraPorProcedimento(List<Aula> aulas){
        double total = 0.0;

        for(Aula a:aulas){
            for(InsumosExtra i:a.getInsumosExtra()){
                total = total + i.getInsumo().getPrecoInsumo();
            }
        }

        double media = total / aulas.size();

        return media;
    }

    public Double calcularCustoMedio(List<Aula> aulas){
        double total = 0.0;

        for(Aula a:aulas){
            for(InsumosExtra i:a.getInsumosExtra()){
                total = total + i.getInsumo().getPrecoInsumo();
            }
            for(Insumos i: a.getTipoDeProcedimento().getInsumos()){
                total = total + (a.getQuantidadeAlunos() * i.getPrecoInsumo());
            } 
        }

        double media = total / aulas.size();

        return media;
    }

    public Double calcularCustoTotal(List<Aula> aulas){
        double total = 0.0;

        for(Aula a:aulas){
            for(InsumosExtra i:a.getInsumosExtra()){
                total = total + i.getInsumo().getPrecoInsumo();
            }
            for(Insumos i: a.getTipoDeProcedimento().getInsumos()){
                total = total + (a.getQuantidadeAlunos() * i.getPrecoInsumo());
            }
        }

        return total;
    }
}
