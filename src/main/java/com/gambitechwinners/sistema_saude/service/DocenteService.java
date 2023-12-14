package com.gambitechwinners.sistema_saude.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gambitechwinners.sistema_saude.model.entity.Docente;
import com.gambitechwinners.sistema_saude.model.entity.Procedimento;
import com.gambitechwinners.sistema_saude.model.request.DocenteRequest;
import com.gambitechwinners.sistema_saude.model.response.DocenteResponse;
import com.gambitechwinners.sistema_saude.repository.DocenteRepository;
import com.gambitechwinners.sistema_saude.repository.ProcedimentoRepository;

@Service
public class DocenteService {
	
	@Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    public DocenteResponse criarDocente(DocenteRequest requisicaoDocente) {
        List<Procedimento> procedimentos = new ArrayList<>();
        for(String s: requisicaoDocente.getProcedimentos()){
            Procedimento procedimento = procedimentoRepository.findByNomeProcedimento(s);
            procedimentos.add(procedimento);
        }

        Docente docente = new Docente(requisicaoDocente.getNome(),procedimentos);
        docente = docenteRepository.save(docente);

        return new DocenteResponse(
            docente.getId(),
            docente.getNome(),
            docente.getProcedimentos()
        );
    }
        
    public Docente detalharDocente(Long id) {
    	return docenteRepository.findById(id).orElseThrow(() -> new Error());
    }
    
    public Docente alterarDocente(Long id, Docente docente) {
    	Docente docenteAntigo = docenteRepository.findById(id).orElseThrow(() -> new Error());
		
    	if (docente.getAulas() != null || !docente.getAulas().isEmpty()) {
			docenteAntigo.setAulas(docente.getAulas());
		}
		
		if (docente.getProcedimentos() != null || !docente.getProcedimentos().isEmpty()) {
			docenteAntigo.setProcedimentos(docente.getProcedimentos());
		}
		
		if (docente.getNome() != null || !docente.getNome().isEmpty()) {
			docenteAntigo.setNome(docente.getNome());
		}
				   	
    	return docenteRepository.save(docenteAntigo);
    }
    
    public void deletarDocente(Long id) {  	 
    	Docente docente = docenteRepository.findById(id).orElseThrow(() -> new Error());

        List<Procedimento> procedimentosDoDocente = docente.getProcedimentos();
        for(Procedimento p: procedimentosDoDocente){
            p.getDocente().remove(docente);
            procedimentosDoDocente.remove(p);

            procedimentoRepository.save(p);
        }
        
        docente.setProcedimentos(procedimentosDoDocente);
        docente.setAulas(null);
        docenteRepository.save(docente);

    	docenteRepository.delete(docente); 	
    }
    
    
    
}