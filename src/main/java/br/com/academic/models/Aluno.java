package br.com.academic.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aluno")
	private long id_aluno;
	
	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "sobrenome")
	private String sobrenome;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true)
	private Usuario usuario;
	
	/*@ManyToMany
	@JoinTable(
			name="AlunosDisciplinas",
			uniqueConstraints = @UniqueConstraint(columnNames = { "id_aluno", "id_disciplina" }),
			joinColumns 	  = @JoinColumn(name = "id_aluno"),
			inverseJoinColumns = @JoinColumn(name = "id_disciplina")
	)
	private List<Disciplina> disciplinas;*/
	
	public Aluno() {
		super();
	}

	public long getId_aluno() {
		return id_aluno;
	}
	public void setId_aluno(long id_aluno) {
		this.id_aluno = id_aluno;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
