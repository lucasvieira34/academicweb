package br.com.academic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.AlunoDisciplinaPK;

@Repository
public interface AlunoDisciplinaRepository extends JpaRepository<AlunoDisciplina, Long>{

	Optional<AlunoDisciplina> findById(AlunoDisciplinaPK id);

}