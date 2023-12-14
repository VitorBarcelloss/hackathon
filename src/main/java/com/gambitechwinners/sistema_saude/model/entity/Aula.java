package com.gambitechwinners.sistema_saude.model.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aula_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "procedimento_id")
    private Procedimento tipoDeProcedimento;
    private Date dateProcedimento;
    @Lob
    @Column(length = 10000)
    private byte[] qrCode;
    private Integer quantidadeAlunos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "insumo_extra_id")
    private List<InsumosExtra> insumosExtra;
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docenteResponsavel;

    public Aula(Procedimento tipoDeProcedimento, Date dateProcedimento, Integer quantidadeAlunos) {
        this.tipoDeProcedimento = tipoDeProcedimento;
        this.dateProcedimento = dateProcedimento;
        this.quantidadeAlunos = quantidadeAlunos;
    }
}
