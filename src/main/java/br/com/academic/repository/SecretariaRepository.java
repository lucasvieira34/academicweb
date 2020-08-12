package br.com.academic.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Secretaria;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long>{
	
	@Query("SELECT (SUM(mensalidade) - (SELECT SUM(salario) FROM Professor)) FROM Aluno")
	BigDecimal balanco();

}
