package br.com.academic.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.Secretaria;
import br.com.academic.repository.SecretariaRepository;

@Service
public class SecretariaService {
	
	@Autowired
	private SecretariaRepository sr;
	
	public List<Secretaria> getSecretarias() {
		return sr.findAll();
	}
	
	public void salvarSecretaria(Secretaria secretaria) {
		sr.save(secretaria);
	}
	
	public Secretaria getSecretariaById(long id) {
		return sr.findById(id).get();
	}
	
	public long quantidadeFuncionarios() {
		return sr.count();
	}
	
	public BigDecimal balancoMensal() {
		return sr.balanco();
	}
}
