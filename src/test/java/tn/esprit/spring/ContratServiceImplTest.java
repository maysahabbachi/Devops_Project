package tn.esprit.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.dtoEntities.ContratDTo;

import tn.esprit.spring.services.ContratServiceImpl;


import java.util.Date;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratServiceImplTest {
	private static final Logger L = LogManager.getLogger(EmployeServiceImplTest.class);
    @Autowired
    ContratServiceImpl ContratService;
    int referenceContrat;

    @Before
    public void initBefore() {

        L.info(" Add a new Conrat");
        Date current = new Date();
        L.info("Create contrat with Current Date");
        ContratDTo contrat= new ContratDTo(current,"CDD",1000);
        referenceContrat=ContratService.ajouterContrat(contrat);
        L.info(" Contrat added in @Before with reference ==> : " + referenceContrat);
    }

    @Test
    public void initTest() {

        L.info(" Add a new Contrat");
        Date current = new Date();
        ContratDTo contrat= new ContratDTo(current,"CDD",1000);
        referenceContrat=ContratService.ajouterContrat(contrat); 

        L.info(" Contrat added in @Test with reference ==> : " + referenceContrat);
    }

    @After
    public void initAfter() {

        L.info(" Add a new Conrat");
        Date current = new Date();
        ContratDTo contrat= new ContratDTo(current,"CDD",1000);
        referenceContrat=ContratService.ajouterContrat(contrat);
        L.info(" Contrat added in @After with reference ==> : " + referenceContrat);
    }

    @Test
    public void deleteTestContrat() {

        try {
            L.info("---Start Test--- ");
	
	    L.debug("----Start TEST--- ");

            L.debug("Start Delete Contrat");
            ContratService.deleteContratById(79);

            L.debug("Je viens de finir l'opération du suppression.");

            L.info("Out deleteContratById(79) without errors.");
        }
        catch (Exception e) { L.error("Erreur dans deleteContratById(79) : " + e); }
    }
}
