package com.gambitechwinners.sistema_saude.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gambitechwinners.sistema_saude.errors.exception.ResourceNotFoundException;
import com.gambitechwinners.sistema_saude.model.entity.Aula;
import com.gambitechwinners.sistema_saude.model.entity.Docente;
import com.gambitechwinners.sistema_saude.model.entity.Insumos;
import com.gambitechwinners.sistema_saude.model.entity.InsumosExtra;
import com.gambitechwinners.sistema_saude.model.entity.Procedimento;
import com.gambitechwinners.sistema_saude.model.request.AulaRequest;
import com.gambitechwinners.sistema_saude.model.request.InsumoExtraRequest;
import com.gambitechwinners.sistema_saude.model.request.ProcedimentoRequest;
import com.gambitechwinners.sistema_saude.model.response.AulaResponse;
import com.gambitechwinners.sistema_saude.model.response.InsumoResponse;
import com.gambitechwinners.sistema_saude.model.response.ListaAulaResponse;
import com.gambitechwinners.sistema_saude.model.response.ProcedimentoResponse;
import com.gambitechwinners.sistema_saude.repository.AulaRepository;
import com.gambitechwinners.sistema_saude.repository.DocenteRepository;
import com.gambitechwinners.sistema_saude.repository.InsumosExtraRepository;
import com.gambitechwinners.sistema_saude.repository.InsumosRepository;
import com.gambitechwinners.sistema_saude.repository.ProcedimentoRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jakarta.transaction.Transactional;

@Service
public class ProcedimentosService {
    @Autowired
    AulaRepository aulaRepository;
    @Autowired
    InsumosRepository insumosRepository;
    @Autowired
    InsumosExtraRepository insumosExtraRepository;
    @Autowired
    ProcedimentoRepository procedimentoRepository;
    @Autowired
    DocenteRepository docenteRepository;
    
    @Transactional
    public byte[] QRCodeCheckin(Long aulaId){
        Aula aula = aulaRepository.findById(aulaId).orElseThrow(() -> new ResourceNotFoundException("Aula não encontrada!"));

        return aula.getQrCode();
    }

    @Transactional
    public byte[] QRCodeInsumo(Long insumoId){
        Insumos insumo = insumosRepository.findById(insumoId).orElseThrow(() -> new ResourceNotFoundException("Insumo não encontrado!"));

        return insumo.getQrCode();
    }

    @Transactional
    public List<ListaAulaResponse> listarAulas(){
        List<Aula> aulas = aulaRepository.findAll();
        if(aulas.isEmpty()){
            throw new ResourceNotFoundException("Nenhuma aula encontrada!");
        }

        List<ListaAulaResponse> listaAulaResponses = new ArrayList<>();
        for(Aula a: aulas){
            ListaAulaResponse listaAulaResponse = new ListaAulaResponse(
                    a.getId(),
                    a.getTipoDeProcedimento().getNomeProcedimento(),
                    a.getDateProcedimento()
            );
            listaAulaResponses.add(listaAulaResponse);
        }

        return listaAulaResponses;
    }

    @Transactional
    public List<ListaAulaResponse> listarAulasPorDocente(Long id){
        Docente docente = docenteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Docente não encontrado!"));
        List<Aula> aulas = docente.getAulas();

        if(aulas.isEmpty()){
            throw new ResourceNotFoundException("Nenhuma aula encontrada!");
        }

        List<ListaAulaResponse> listaAulaResponses = new ArrayList<>();
        for(Aula a: aulas){
            ListaAulaResponse listaAulaResponse = new ListaAulaResponse(
                    a.getId(),
                    a.getTipoDeProcedimento().getNomeProcedimento(),
                    a.getDateProcedimento()
            );
            listaAulaResponses.add(listaAulaResponse);
        }

        return listaAulaResponses;
    }

    @Transactional
    public ProcedimentoResponse detalharProcedimento(Long id){
        Procedimento procedimento = procedimentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Procedimento não encontrado!"));

        return new ProcedimentoResponse(
                procedimento.getId(),
                procedimento.getNomeProcedimento(),
                procedimento.getInsumos(),
                procedimento.getCustoProcedimento()
        );
    }

    @Transactional
    public AulaResponse criarAula(AulaRequest requisicaoAula){
       Aula aula = criarClasseAula(requisicaoAula);
       aula = aulaRepository.save(aula);

       byte[] qrcodeBytes = gerarQrCode(aula,200,200);
       aula.setQrCode(qrcodeBytes);
       aulaRepository.save(aula);

       return criarClasseAulaResponse(aula);
    }

    @Transactional
    public void checkin(Long aulaId){
        Aula aula = aulaRepository.findById(aulaId).orElseThrow(() -> new ResourceNotFoundException("Aula não encontrada!"));
        aula.setQuantidadeAlunos(aula.getQuantidadeAlunos()+1);

        for(Insumos i: aula.getTipoDeProcedimento().getInsumos()){
            i.setQuantidade(i.getQuantidade()-1);
        }

        aulaRepository.save(aula);
    }

    @Transactional
    public InsumoResponse lerInsumo(Long aulaId, Long insumoId, InsumoExtraRequest insumoExtraRequest){
        Insumos insumo = insumosRepository.findById(insumoId).orElseThrow(() -> new ResourceNotFoundException("Insumo não encontrado!"));
        Aula aula = aulaRepository.findById(aulaId).orElseThrow(() -> new ResourceNotFoundException("Aula não encontrada!"));
        
        InsumosExtra insumosExtra = new InsumosExtra(insumo, insumoExtraRequest.getMotivo());
        insumosExtraRepository.save(insumosExtra);

        aula.getInsumosExtra().add(insumosExtra);
        aulaRepository.save(aula);

        insumo.setQuantidade(insumo.getQuantidade()-1);
        insumosRepository.save(insumo);

        return new InsumoResponse(insumo.getId(),
                                    insumo.getNomeInsumo(),
                                    insumo.getPrecoInsumo(),
                                    insumo.getQuantidade());        
    }

    @Transactional
    public ProcedimentoResponse criarProcedimento(ProcedimentoRequest requisicaoProcedimento){
        List<Insumos> insumos = new ArrayList<>();
        for(String s: requisicaoProcedimento.getInsumos()){
            Insumos insumo = insumosRepository.findByNomeInsumo(s);
            if(insumo == null){
                throw new ResourceNotFoundException("Insumo de nome "+ s +" não econtrado!"); 
            }
            insumos.add(insumo);
        }

        double custoProcedimento = 0.0;
        for(Insumos i: insumos){
            custoProcedimento = custoProcedimento + i.getPrecoInsumo();
        }

        Procedimento procedimento = new Procedimento(
                requisicaoProcedimento.getNomeProcedimento(),
                insumos,
                custoProcedimento
        );

        procedimento = procedimentoRepository.save(procedimento);

        return new ProcedimentoResponse(
                procedimento.getId(),
                procedimento.getNomeProcedimento(),
                procedimento.getInsumos(),
                procedimento.getCustoProcedimento()
        );
    }

    //-----------------------------------------------------------------------------------------

    public AulaResponse criarClasseAulaResponse(Aula aula){
        return new AulaResponse(aula.getId(),
                                aula.getTipoDeProcedimento(),
                                aula.getDateProcedimento(),
                                aula.getQuantidadeAlunos(),
                                aula.getInsumosExtra());
    }

     public Aula criarClasseAula(AulaRequest requisicaoAula){
        return new Aula(requisicaoAula.getTipoDeProcedimento(),
                        requisicaoAula.getDateProcedimento(),
                        0);
    }

    public byte[] gerarQrCode(Aula aula, int width, int height) {
        try {
            String text = "http://localhost:8080/procedimento/checkin/"+aula.getId();

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, matrixToImageConfig);

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
