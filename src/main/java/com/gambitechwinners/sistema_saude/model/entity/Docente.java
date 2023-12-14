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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gestor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Docente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docente_id")
	private Long id; 
	
	private String nome;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "procedimento_docente", joinColumns = {
            @JoinColumn(name = "docente_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "procedimento_id")
    })
	private List<Procedimento> procedimentos;
	@OneToMany(mappedBy = "docenteResponsavel")
	private List<Aula> aulas;
    
    public Docente(String nome, List<Procedimento> procedimentos) {
        this.nome = nome;
        this.procedimentos = procedimentos;
    }

    

}