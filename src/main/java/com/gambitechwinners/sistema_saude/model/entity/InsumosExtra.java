package com.gambitechwinners.sistema_saude.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InsumosExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insumo_extra_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "insumo_id")
    private Insumos insumo;
    private String motivo;

    public InsumosExtra(Insumos insumo, String motivo) {
        this.insumo = insumo;
        this.motivo = motivo;
    }
}
