package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.converter.DepartementConverter;
import tn.esprit.spring.dtoEntities.DepartementDTo;

@Service
public class DepartementServiceImpl implements IDepartementService {


	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
    DepartementConverter converter;

	public List<Departement> getAllDepartements() {
		return (List<Departement>) deptRepoistory.findAll();
	}
	public Integer ajouterDepartement(DepartementDTo dep) {
		try{
			Departement departartement=converter.depTodo(dep);
			deptRepoistory.save(departartement);
			return departartement.getId();
		} catch (Exception e) {
			return null;
		}
		
		
	}


}
