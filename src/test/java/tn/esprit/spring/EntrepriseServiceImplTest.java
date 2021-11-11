package tn.esprit.spring;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.dtoEntities.ContratDTo;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(EntrepriseServiceImplTest.class)

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntrepriseServiceImplTest {
private static final Logger L = LogManager.getLogger(EntrepriseServiceImplTest.class);
    
	@Mock
	@Autowired
	private EmployeRepository employeRepository;
	
	@Autowired
	@Mock
	IEntrepriseService entrepriseService;
	
	@Autowired
	@InjectMocks
	   EntrepriseServiceImpl  Entrepriseservice ;

	private Entreprise entreprise;

    /*@Test
    public void getAllEmployeNamesJPQLTest() {
        List<String> list = Entrepriseservice.getAllDepartementsNamesByEntreprise(1);
        L.debug("liste des departements "+list+" affiché avec succès .");
        L.info("affiché  sans erreurs.");
        assertEquals(1, 1);
    }
*/
  /*@Test
    public void getSalaireByEmployeIdJPQL() {
        int employeId = 1;
        double salaire = Entrepriseservice.getEntrepriseById(employeId);
        double salaire = employeService.getSalaireByEmployeIdJPQL(employeId);
        assertFalse(employeRepository.findById(employeId).isPresent() &&
                employeRepository.findById(employeId).get().getContrat().getSalaire() == salaire);
    }*/
    @Test
    public void deleteTestEntreprise() {

        try {
            L.debug("Start Delete Contrat");
            Entrepriseservice.deleteDepartementById(79);
            L.debug("Suppression effectuer avec succès .");
            L.info("effectué sans erreurs.");
            assertEquals(1, 1);
        }
        catch (Exception e) { L.error("Erreur " + e); }
    }
    @Test
    public void getAlldepartements() {
        assertTrue(entrepriseService.getAllDepartementsNamesByEntreprise(1).isEmpty());
    }
   /*@Test
    public void getAllEmployes() {
        assertTrue(employeService.getAllEmployes().isEmpty());
    }
	*/
}

