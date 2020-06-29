package br.com.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
		
	Aluno findById(long id);
	
}