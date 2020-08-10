package br.com.academic.models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "disciplina")
public class Disciplina implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_disciplina")
	private long id_disciplina;
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@ManyToMany
	@JoinTable(
			name="ProfessoresDisciplinas",
			uniqueConstraints = @UniqueConstraint(columnNames = { "id_professor", "id_disciplina" }),
			joinColumns 	  = @JoinColumn(name = "id_disciplina"),
			inverseJoinColumns = @JoinColumn(name = "id_professor")
	)
	private List<Professor> professores;
	
	@OneToMany(mappedBy = "disciplina")
	Set<AlunoDisciplina> extratos;
	

	public Disciplina() {
		super();
	}	
	
	public long getId_disciplina() {
		return id_disciplina;
	}

	public void setId_disciplina(long id_disciplina) {
		this.id_disciplina = id_disciplina;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<AlunoDisciplina> getExtratos() {
		return extratos;
	}
	public void setExtratos(Set<AlunoDisciplina> extratos) {
		this.extratos = extratos;
	}
	
}