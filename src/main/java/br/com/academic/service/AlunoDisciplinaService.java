package br.com.academic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.AlunoDisciplina;
import br.com.academic.repository.AlunoDisciplinaRepository;

@Service
public class AlunoDisciplinaService {
	
	@Autowired
	private AlunoDisciplinaRepository adr;
	
	public List<AlunoDisciplina> getAlunoDisciplinas(){
		return adr.findAll();
	}

	public void salvarAlunoDisciplina(AlunoDisciplina alunoDisciplina) {
		adr.save(alunoDisciplina);
	}

}
