package br.com.academic.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
		
	Aluno findById(long id);
	
	Aluno findByMatricula(String matricula);
	
	@Query("select sum(mensalidade) from Aluno")
	BigDecimal mensalidades();
	
}