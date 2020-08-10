package br.com.academic.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id_role;
	
	@Column(unique = true)
	private String nome;

	@ManyToMany(mappedBy = "roles")
	private List<Usuario> usuarios;
	
	public Role() {
    }

    public Role(String nome) {
        this.nome = nome;
    }
	
	public String getNomeRole() {
		return nome;
	}

	public void setNomeRole(String nome) {
		this.nome = nome;
	}

	public long getIdRole() {
		return id_role;
	}

	public void setIdRole(long id) {
		this.id_role = id;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nome;
	}

}
