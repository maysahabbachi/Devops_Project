package tn.esprit.spring.services;


import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.dtoEntities.EntrepriseDTo;


public interface IEntrepriseService {
	
	public Integer ajouterEntreprise(EntrepriseDTo entreprise);
	public int ajouterDepartement(Departement dep);
	public void deleteEntrepriseById(int entrepriseId);
	public void deleteDepartementById(int depId);
	public Entreprise getEntrepriseById(int entrepriseId);
}
