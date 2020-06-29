package br.com.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	
}
