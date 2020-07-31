package br.com.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academic.models.ValidationToken;

@Repository
public interface ValidationTokenRepository extends JpaRepository<ValidationToken, Long> {
	
	ValidationToken findByToken(String token);

}
