package com.gambitechwinners.sistema_saude.service;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gambitechwinners.sistema_saude.errors.exception.ResourceNotFoundException;
import com.gambitechwinners.sistema_saude.model.entity.Insumos;
import com.gambitechwinners.sistema_saude.model.request.InsumoRequest;
import com.gambitechwinners.sistema_saude.model.response.InsumoResponse;
import com.gambitechwinners.sistema_saude.repository.InsumosRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class InsumosService {
    @Autowired
    InsumosRepository insumosRepository;

    @Transactional
    public InsumoResponse criarInsumo(InsumoRequest requisicaoInsumo){
        Insumos insumo = criarClasseInsumo(requisicaoInsumo);     
        insumo = insumosRepository.save(insumo);

        byte[] qrcodeBytes = gerarQrCode(insumo,200,200);
        insumo.setQrCode(qrcodeBytes);
        insumosRepository.save(insumo);
        
        InsumoResponse respostaInsumo = criarRespostaInsumo(insumo);

        return respostaInsumo;        
    }

    @Transactional
    public void deletarInsumo(Long id){
        Insumos insumo = insumosRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Insumo não encontrado!"));

        insumosRepository.delete(insumo);
    }

//---------------------------------------------------------------------------------------------

    public Insumos criarClasseInsumo(InsumoRequest requisicaoInsumo){
        return new Insumos(requisicaoInsumo.getNomeInsumo(),
                            requisicaoInsumo.getPrecoInsumo(),
                            requisicaoInsumo.getQuantidade());
    }

    public InsumoResponse criarRespostaInsumo(Insumos insumo){
        return new InsumoResponse(insumo.getId(),
                                insumo.getNomeInsumo(),
                                insumo.getPrecoInsumo(),
                                insumo.getQuantidade());
    }

    public byte[] gerarQrCode(Insumos insumos, int width, int height) {
        try {
            String text = "http://localhost:8080/lerInsumo/"+insumos.getId();

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
