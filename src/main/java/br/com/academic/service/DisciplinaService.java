package br.com.academic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.Disciplina;
import br.com.academic.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository dr;
	
	public List<Disciplina> getDisciplinas(){
		return dr.findAll();
	}
	
	public Disciplina getDisciplinaById(Long id) {
		return dr.findById(id).get();
	}

}
