package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.IEmployeService;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(EmployeServiceImplTest.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeServiceImplTest {
	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);
	@Mock
	@Autowired
	private EmployeRepository employeRepository;
	
	@Autowired
	@Mock
	IEmployeService empployeService;
	
	@Autowired
	@InjectMocks
	private EmployeServiceImpl employeService;

	private Employe employe1;

	private static String mail = "mohamedneji.ghazouani@esprit.tn";


	@Before
	public void setUp() {
		
		employe1 = new Employe("ghazouani", "neji", "mohamedneji.ghazouani@esprit.tn", true, Role.INGENIEUR);
	
		employeRepository.save(employe1);



	}

	@After
	public void tearDown() {
		
		employeRepository.deleteAll();
	}
	@Test
 	public void ajouterEmployeTest() {
		   Employe e = new Employe();
		   e.setActif(true);
		   e.setEmail("mohamedneji.ghazouani@esprit.tn");
		   e.setNom("ghazouani");
		   e.setPrenom("neji"); 
		  when(employeRepository.save(e)).thenReturn(e);	
		  assertEquals(e.getId(), employeService.addOrUpdateEmploye(e));
		  
	}
	
	@Test
 	public void getNombreEmployeJPQLTest() {
 		int nbr = employeService.getNombreEmployeJPQL();
 		assertThat(nbr).isEqualTo(0);
 	}
    @Test
    public void getAllEmployeNamesJPQLTest() {
        List<String> list = employeService.getAllEmployeNamesJPQL();
        List<String> list1 = employeService.getAllEmployes().stream().map(Employe::getNom).collect(Collectors.toList());
        assertEquals(list, list1);
    }


	   @Test
	    public void getAllEmployes() {
	        assertTrue(employeService.getAllEmployes().isEmpty());
	    }
	   @Test
	    public void getSalaireByEmployeIdJPQL() {
	        int employeId = 1;
	        double salaire = employeService.getSalaireByEmployeIdJPQL(employeId);
	        assertFalse(employeRepository.findById(employeId).isPresent() &&
	                employeRepository.findById(employeId).get().getContrat().getSalaire() == salaire);
	    }
		
		
		@Test
		public void mettreAjourEmailByEmployeIdJPQLTest() {
			employeService.mettreAjourEmailByEmployeIdJPQL("mohamedneji.ghazouani@esprit.tn", employe1.getId());
			Optional<Employe> e = employeRepository.findById(employe1.getId());
			if (e.isPresent()) {
				assertThat(e.get().getEmail()).isEqualTo("mohamedneji.ghazouani@esprit.tn");
			}
		}
		
}
