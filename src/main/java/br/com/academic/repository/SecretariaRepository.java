package br.com.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Secretaria;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long>{

}
