package com.gambitechwinners.sistema_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gambitechwinners.sistema_saude.model.entity.Insumos;

public interface InsumosRepository extends JpaRepository<Insumos, Long>{
    Insumos findByNomeInsumo(@Param("nomeInsumo") String nomeInsumo);
}
