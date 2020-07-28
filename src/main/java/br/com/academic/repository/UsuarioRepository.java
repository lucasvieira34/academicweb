package br.com.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.academic.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByLogin(String login);
	
	Usuario findByEmail(String email);

	@Modifying
	@Query("update Usuario u set u.senha = :senha where u.id_usuario = :id_usuario")
	void updatePassword(@Param("senha") String password, @Param("id_usuario") long id_usuario);

}
