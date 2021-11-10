package tn.esprit.spring.services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;

@Service
public class ContratServiceImpl implements IContratService {


	@Autowired
	ContratRepository contratRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	
	private static final Logger l = LogManager.getLogger(ContratServiceImpl.class);


	public List<Contrat> getAllContrats() {
		
		l.info("In getAllContrats() : ");
		
		l.debug("La liste des contrats du BD");
		
		l.info("Out getAllContrats() ");		
		
		return (List<Contrat>) contratRepository.findAll();
	}
	
	
	public Integer ajouterContrat(Contrat contrat) {
		l.debug("In ajouterContrat");
		try{
			contratRepository.save(contrat);
			l.info("Contrat ajouter avec reference = "+contrat.getReference());
			l.debug("Out ajouterContrat");
			return contrat.getReference();
		} catch (Exception e) {
			l.error("erreur dans l'ajout :"+e);
			return null;
		}
	}
	
	
	public void deleteAllContratJPQL() {
		l.debug("In deleteAllContrat ");
		employeRepository.deleteAllContratJPQL();
		l.info("Liste de contrats supprimé");
	}

}
