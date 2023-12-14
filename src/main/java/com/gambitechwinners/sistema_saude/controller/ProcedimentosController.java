package com.gambitechwinners.sistema_saude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gambitechwinners.sistema_saude.model.request.AulaRequest;
import com.gambitechwinners.sistema_saude.model.request.InsumoExtraRequest;
import com.gambitechwinners.sistema_saude.model.request.ProcedimentoRequest;
import com.gambitechwinners.sistema_saude.model.response.AulaResponse;
import com.gambitechwinners.sistema_saude.model.response.InsumoResponse;
import com.gambitechwinners.sistema_saude.model.response.ListaAulaResponse;
import com.gambitechwinners.sistema_saude.model.response.ProcedimentoResponse;
import com.gambitechwinners.sistema_saude.service.ProcedimentosService;

@CrossOrigin("*")
@RestController
@RequestMapping("/procedimento")
public class ProcedimentosController {
    @Autowired
    ProcedimentosService procedimentosService;

    @GetMapping("/QRCodeCheckin/{aulaId}")
    public ResponseEntity<byte[]> QRCodeCheckin(@PathVariable Long aulaId){
        byte[] qrCode = procedimentosService.QRCodeCheckin(aulaId);

        HttpHeaders headers = gerarHeader(qrCode);
        
        return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
    }

    @GetMapping("/QRCodeInsumo/{insumoId}")
    public ResponseEntity<byte[]> QRCodeInsumo(@PathVariable Long insumoId){
        byte[] qrCode = procedimentosService.QRCodeInsumo(insumoId);

        HttpHeaders headers = gerarHeader(qrCode);
        
        return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
    }

    @GetMapping("/listarAulas")
    public ResponseEntity<List<ListaAulaResponse>> listarAulas(){
        List<ListaAulaResponse> listaAulas = procedimentosService.listarAulas();

        return new ResponseEntity<>(listaAulas, HttpStatus.OK);
    }

    @GetMapping("/listarAulaPorDocente/{id}")
     public ResponseEntity<List<ListaAulaResponse>> listarAulaPorDocente(@PathVariable Long id){
        List<ListaAulaResponse> listaAulas = procedimentosService.listarAulasPorDocente(id);

        return new ResponseEntity<>(listaAulas, HttpStatus.OK);
    }

    @GetMapping("/detalharProcedimento/{id}")
    public ResponseEntity<ProcedimentoResponse> detalharProcedimento(@PathVariable Long id){
       ProcedimentoResponse respostaProcedimento = procedimentosService.detalharProcedimento(id);

        return new ResponseEntity<>(respostaProcedimento, HttpStatus.OK);
    }

    @PostMapping("/criarAula")
    public ResponseEntity<AulaResponse> criarAula(@RequestBody AulaRequest requisicaoAula){
        AulaResponse respostaAula = procedimentosService.criarAula(requisicaoAula);

        return new ResponseEntity<>(respostaAula, HttpStatus.CREATED);
    }

    @PostMapping("/criarProcedimento")
    public ResponseEntity<ProcedimentoResponse> criarProcedimento(@RequestBody ProcedimentoRequest requisicaoProcedimento){
        ProcedimentoResponse respostaProcedimento = procedimentosService.criarProcedimento(requisicaoProcedimento);

        return new ResponseEntity<>(respostaProcedimento, HttpStatus.CREATED);
    }

    @PutMapping("/checkin/{aulaId}")
    public ResponseEntity<?> checkin(@PathVariable Long aulaId){
        procedimentosService.checkin(aulaId);

        return new ResponseEntity<>("Checkin realizado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping("/lerInsumo/{aulaId}/{insumoId}")
    public ResponseEntity<InsumoResponse> lerInsumo(@PathVariable Long aulaId, @PathVariable Long insumoId, @RequestBody InsumoExtraRequest insumoExtraRequest){
        InsumoResponse respostaInsumo = procedimentosService.lerInsumo(aulaId, insumoId, insumoExtraRequest);

        return new ResponseEntity<>(respostaInsumo, HttpStatus.OK);
    }

    public HttpHeaders gerarHeader(byte[] bytes){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);

        return headers;
    }

}
