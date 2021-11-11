package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.TimesheetServiceImpl;
import tn.esprit.spring.services.ITimesheetService;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(EmployeServiceImplTest.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimeSheetServiceImplTest {
	
	@Mock
	@Autowired
	private TimesheetRepository TimesheetRepository;
	
	@Autowired
	@Mock
	ITimesheetService ITimesheetService;
	
	@Autowired
	@InjectMocks
	private TimesheetServiceImpl TimesheetService;

	@Test
 	public void findAllMissionByEmployeJPQLTest() {
 		assertThat(TimesheetService.findAllMissionByEmployeJPQL(1)).isEmpty() ;
 	}
	
	@Test
 	public void getAllEmployeByMissionTest() {
 		assertThat(TimesheetService.getAllEmployeByMission(1)).isEmpty();
 	}

}
