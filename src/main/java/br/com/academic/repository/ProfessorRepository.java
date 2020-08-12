package br.com.academic.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	
	@Query("select sum(salario) from Professor")
	BigDecimal salarios();
	
}
