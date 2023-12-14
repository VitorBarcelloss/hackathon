package com.gambitechwinners.sistema_saude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gambitechwinners.sistema_saude.model.request.InsumoRequest;
import com.gambitechwinners.sistema_saude.model.response.InsumoResponse;
import com.gambitechwinners.sistema_saude.service.InsumosService;

@CrossOrigin("*")
@RestController
@RequestMapping("/insumo")
public class InsumosController {
    @Autowired
    InsumosService insumosService;

    @PostMapping("/criarInsumo")
    public ResponseEntity<InsumoResponse> criarInsumo(@RequestBody InsumoRequest requisicaoInsumo){
        InsumoResponse respostaInsumo = insumosService.criarInsumo(requisicaoInsumo);

        return new ResponseEntity<>(respostaInsumo, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletarInsumo/{id}")
    public ResponseEntity<?> deletarInsumo(@PathVariable Long id){
        insumosService.deletarInsumo(id);
        return new ResponseEntity<>("Insumo deletado com sucesso!", HttpStatus.NO_CONTENT);
    }
}
