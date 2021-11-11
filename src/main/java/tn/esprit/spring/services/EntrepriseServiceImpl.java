package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.dtoEntities.EntrepriseDTo;
import tn.esprit.spring.converter.EntrepriseConverter;


@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger l = Logger.getLogger(EntrepriseServiceImpl.class);
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
    EntrepriseConverter converter;
	public Integer ajouterEntreprise(EntrepriseDTo entreprise) {
		Entreprise entre = converter.entrepriseTodo(entreprise);
		entrepriseRepoistory.save(entre);
		l.info("entreprise ajouté avec succès");
		return entreprise.getId();
		
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		l.info("departement  ajouté avec succès");
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
				Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
				Departement depManagedEntity = deptRepoistory.findById(depId).get();
				l.info("departement n : "+depId+" à etait affecté avec succès à l'entreprise n : "+entrepriseId );
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Optional<Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.get().getDepartements()){
			depNames.add(dep.getName());
		}
		l.info("les departements sont retournées avec succès"  );
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());	
		l.info("l'entreprise  supprimé  "  );
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		deptRepoistory.delete(deptRepoistory.findById(depId).get());	
		l.info("departement   supprimé  "  );
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("entreprise : "+entrepriseId+" est affiché ") ; 
		return entrepriseRepoistory.findById(entrepriseId).get();	
		
	}

}
