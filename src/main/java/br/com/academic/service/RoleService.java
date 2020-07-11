package br.com.academic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.Role;
import br.com.academic.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository rr;
	
	public Role getRole(long id) {
		return rr.findById(id).get();
	}

}
