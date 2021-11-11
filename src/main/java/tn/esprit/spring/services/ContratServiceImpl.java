package tn.esprit.spring.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.converter.ContratConverter;
import tn.esprit.spring.dtoEntities.ContratDTo;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;

@Service
public class ContratServiceImpl implements IContratService {

	
	@Autowired
	ContratRepository contratRepository;
	@Autowired
	ContratConverter converter;
	private static final Logger l = LogManager.getLogger(ContratServiceImpl.class);
	
	public Integer ajouterContrat(ContratDTo  NouveauContrat) {
		
		try{
			Contrat contrat=converter.contdto(NouveauContrat);
			contratRepository.save(contrat);
			return contrat.getReference();
		} catch (Exception e) {
			l.error("erreur dans la methode ajouterContrat() :"+e);
			return null;
		}
       
	}
	
	public int deleteContratById(int contratId) {
		l.info("In deleteContratById() ");
		try {
			Optional<Contrat> contrat=contratRepository.findById(contratId);
			if (contrat.isPresent()) {
				l.debug("contrat portant l'id :  "+contratId +" est recupére avec succés");
			
				contratRepository.delete(contrat.get());	
			l.info("Out deleteContratById() ");
			return 1;
			} 
		} catch (Exception e) {
			l.error("erreur methode deleteContratById() :" + e);
			return 0;
		}
		return -1;

	}
	
	public List<Contrat> getAllContrats() {
        l.info("In getAllContrats() : ");
		l.debug("Récupération des contrats");
		l.info("Out getAllContrats() ");
		return (List<Contrat>) contratRepository.findAll();
	}
	


	


}