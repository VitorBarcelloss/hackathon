package com.gambitechwinners.sistema_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gambitechwinners.sistema_saude.model.entity.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long>{
    Procedimento findByNomeProcedimento(@Param("nomeProcedimento") String nomeProcedimento);
}
