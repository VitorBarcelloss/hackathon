package com.gambitechwinners.sistema_saude.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gambitechwinners.sistema_saude.model.entity.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long>{
    @Query("SELECT a FROM Aula a WHERE a.dateProcedimento >= :dataInicio AND a.dateProcedimento <= :dataFim")
    List<Aula> findByPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
}
