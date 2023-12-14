package com.gambitechwinners.sistema_saude.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Insumos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="insumo_id")
    private Long id;
    private String nomeInsumo;
    @Lob
    @Column(length = 10000)
    private byte[] qrCode;
    private Double precoInsumo;
    private int quantidade;
    
    public Insumos(String nomeInsumo, Double precoInsumo, int quantidade) {
        this.nomeInsumo = nomeInsumo;
        this.precoInsumo = precoInsumo;
        this.quantidade = quantidade;
    }

}
