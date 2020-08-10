package br.com.academic.models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

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

	@Column(name = "matricula")
	private String matricula;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "sobrenome")
	private String sobrenome;
	
	@Column(name = "cpf")
	private String cpf;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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
