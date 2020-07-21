package br.com.academic.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "professor")
public class Professor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_professor")
	private long id_professor;

	@NotBlank(message = "O campo matricula não pode ser nulo")
	@Size(min = 9, message = "O campo matricula deve ter no mínimo 10 caracteres")
	@Column(name = "matricula")
	private String matricula;
	
	@NotBlank(message = "O campo nome não pode ser nulo")
	@Column(name = "nome")
	private String nome;
	
	@NotBlank(message = "O campo sobrenome não pode ser nulo")
	@Column(name = "sobrenome")
	private String sobrenome;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true)
	private Usuario usuario;
	
	@ManyToMany
	@JoinTable(
			name="ProfessoresDisciplinas",
			uniqueConstraints = @UniqueConstraint(columnNames = { "id_professor", "id_disciplina" }),
			joinColumns 	  = @JoinColumn(name = "id_professor"),
			inverseJoinColumns = @JoinColumn(name = "id_disciplina")
	)
	private List<Disciplina> disciplinas;
	
	
	public Professor() {
		super();
	}

	public long getId_professor() {
		return id_professor;
	}

	public void setId_professor(long id_professor) {
		this.id_professor = id_professor;
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

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	
}
