package br.com.academic.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AlunoDisciplinaPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_aluno")
	Long id_aluno;
	
	@Column(name = "id_disciplina")
	Long id_disciplina;

	
	public Long getAlunoId() {
		return id_aluno;
	}

	public void setAlunoId(Long alunoId) {
		this.id_aluno = alunoId;
	}

	public Long getDisciplinaId() {
		return id_disciplina;
	}

	public void setDisciplinaId(Long disciplinaId) {
		this.id_disciplina = disciplinaId;
	}

	public AlunoDisciplinaPK() {
		super();
	}

	public AlunoDisciplinaPK(Long alunoId, Long disciplinaId) {
		super();
		this.id_aluno = alunoId;
		this.id_disciplina = disciplinaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_aluno == null) ? 0 : id_aluno.hashCode());
		result = prime * result + ((id_disciplina == null) ? 0 : id_disciplina.hashCode());
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
		AlunoDisciplinaPK other = (AlunoDisciplinaPK) obj;
		if (id_aluno == null) {
			if (other.id_aluno != null)
				return false;
		} else if (!id_aluno.equals(other.id_aluno))
			return false;
		if (id_disciplina == null) {
			if (other.id_disciplina != null)
				return false;
		} else if (!id_disciplina.equals(other.id_disciplina))
			return false;
		return true;
	}
	
	
	
}
