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

		l.info(" methode affectation departement a entreprise");
		l.debug("chercher de l'entreprise par id ");
		Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
		if (value.isPresent()) {
			Entreprise entrepriseManagedEntity = value.get();

			l.debug(" trouver l'entreprise" + entrepriseManagedEntity);
			l.debug("j recherche du departement par id ");
			Optional<Departement> value1 = deptRepoistory.findById(depId);
			if (value1.isPresent()) {
				Departement depManagedEntity = value1.get();

				l.debug(" trouver le departement" + depManagedEntity);
				l.debug("update de l'ntreprise et l'enregistré");

				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);

				l.debug("'update de l'entreprise ");
				l.info("fin de   la methode ");

			}
		}
		else {
			l.debug("l'entreprise ou departement n'exite pas");
			l.info("fin de   la methode affectation departement a entreprise");

		}
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		l.debug("methode getAllDepartementsNamesByEntreprise ");
		List<String> depNames = new ArrayList<>();
		try {
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
			
			if(entrepriseManagedEntity!=null && entrepriseManagedEntity.getDepartements()!=null){
			for(Departement dep : entrepriseManagedEntity.getDepartements()){
				depNames.add(dep.getName());
			}
			l.debug("getAllDepartementsNamesByEntreprise fini avec succes ");
			return depNames;
			}
			else {
				l.error("erreur methode getAllDepartementsNamesByEntreprise : " );
				return depNames;
			}
		} catch (Exception e) {
			l.error("erreur methode getAllDepartementsNamesByEntreprise : " +e);
			return depNames;
		}
	}
	@Transactional
	public int deleteEntrepriseById(int entrepriseId) {
		l.debug("methode deleteEntrepriseById ");
		
		try {
			if(entrepriseRepoistory.findById(entrepriseId).orElse(null)!=null){
			entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).orElse(null));
			l.debug("deleteEntrepriseById fini avec succes ");
			return 0;}else {
				l.error("erreur methode deleteEntrepriseById : " );
				return -1;
			}
		} catch (Exception e) {
			l.error("erreur methode deleteEntrepriseById : " +e);
			return -1;
		}		
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		deptRepoistory.delete(deptRepoistory.findById(depId).get());	
		l.info("departement   supprimé  "  );
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		l.debug("methode getEntrepriseById ");
		
		
		try {
			Entreprise et= entrepriseRepoistory.findById(entrepriseId).orElse(null);
			l.debug("getEntrepriseById fini avec succes ");
			return et;
		} catch (Exception e) {
			l.error("erreur methode getEntrepriseById : " +e);
			return null;
		}	
		
		  
	}

}
