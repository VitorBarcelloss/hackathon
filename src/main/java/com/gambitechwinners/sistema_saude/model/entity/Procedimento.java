package com.gambitechwinners.sistema_saude.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Procedimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedimento_id")
	private Long id;

    private String nomeProcedimento;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "procedimento_insumo", joinColumns = {
            @JoinColumn(name = "procedimento_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "insumo_id")
    })
	private List<Insumos> insumos;

    @ManyToMany(mappedBy = "procedimentos")
    private List<Docente> docente;

    private Double custoProcedimento;

    public Procedimento(String nomeProcedimento, List<Insumos> insumos, Double custoProcedimento) {
        this.nomeProcedimento = nomeProcedimento;
        this.insumos = insumos;
        this.custoProcedimento = custoProcedimento;
    }	
	
}