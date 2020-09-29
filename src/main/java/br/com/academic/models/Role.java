package br.com.academic.models;

import java.util.Collection;

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

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_role;
	
	@Column(unique = true)
	private String nome;

	@ManyToMany(mappedBy = "roles")
	private Collection<Usuario> usuarios;
	
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

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id_role ^ (id_role >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id_role != other.id_role)
			return false;
		return true;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nome;
	}

}
