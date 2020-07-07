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
	Long alunoId;
	
	@Column(name = "id_disciplina")
	Long disciplinaId;

	
	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long alunoId) {
		this.alunoId = alunoId;
	}

	public Long getDisciplinaId() {
		return disciplinaId;
	}

	public void setDisciplinaId(Long disciplinaId) {
		this.disciplinaId = disciplinaId;
	}

	public AlunoDisciplinaPK() {
		super();
	}

	public AlunoDisciplinaPK(Long alunoId, Long disciplinaId) {
		super();
		this.alunoId = alunoId;
		this.disciplinaId = disciplinaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alunoId == null) ? 0 : alunoId.hashCode());
		result = prime * result + ((disciplinaId == null) ? 0 : disciplinaId.hashCode());
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
		if (alunoId == null) {
			if (other.alunoId != null)
				return false;
		} else if (!alunoId.equals(other.alunoId))
			return false;
		if (disciplinaId == null) {
			if (other.disciplinaId != null)
				return false;
		} else if (!disciplinaId.equals(other.disciplinaId))
			return false;
		return true;
	}
	
	
	
}
