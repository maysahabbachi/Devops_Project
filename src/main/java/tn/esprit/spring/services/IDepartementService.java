package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.dtoEntities.DepartementDTo;


public interface IDepartementService {
	
	public Integer ajouterDepartement(DepartementDTo dep);
	public List<Departement> getAllDepartements();


	
	
	

	
}
