package br.com.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByNome(String nome);
		
}
