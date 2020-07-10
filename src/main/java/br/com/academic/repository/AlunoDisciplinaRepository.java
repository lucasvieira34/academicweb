package br.com.academic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.academic.models.AlunoDisciplina;

@Repository
public interface AlunoDisciplinaRepository extends JpaRepository<AlunoDisciplina, Long>{

}
