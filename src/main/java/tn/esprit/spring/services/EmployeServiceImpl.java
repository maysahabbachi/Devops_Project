package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.converter.EmployeConverter;
import tn.esprit.spring.dtoEntities.EmployeDTo;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	private static final Logger l = Logger.getLogger(EmployeServiceImpl.class);
	


	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	 @Autowired
	 EmployeConverter converter;

	@Override
	public Employe authenticate(String login, String password) {
		l.info("authenticate user with login : "+login+ "password : "+ password);
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public Integer addOrUpdateEmploye(EmployeDTo  employe) {
		l.info("START addOrUpdateEmploye ");
		Employe emp=converter.empTodo(employe);
		employeRepository.save(emp);
		return employe.getId();
	}


	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		Employe emp;
		if ( employeManagedEntity.isPresent()) {
			emp= employeManagedEntity.get();
		
		l.info("mettreAjourEmailByEmployeId with email :  " + email + "and employee : " + employeId);
		emp.setEmail(email);
		employeRepository.save(emp);

	}}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		l.info("START affecterEmployeADepartement with employeId : "+employeId + "and depId : "+depId);
		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		Departement dep;
		Employe emp = new Employe();
		l.trace("Début Test : verifier si le departement na aucun employe");
if (depManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
			 
			dep=depManagedEntity.get();
		if(dep.getEmployes() == null){
			l.trace("Entrer Test : le departement na aucun employe");
			emp = employeManagedEntity.get();
			List<Employe> employes = new ArrayList<>();
			
			employes.add(emp);
			dep.setEmployes(employes);
			
		}else{
			l.trace("Entrer Test : le departement a des employes");
			dep.getEmployes().add(emp);
		}

		deptRepoistory.save(dep); }
	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{l.info("START desaffecterEmployeDuDepartement with employeId : "+employeId + "and depId : "+depId);

	Optional <Departement> dep = deptRepoistory.findById(depId);
	Departement dept;

	if (dep.isPresent()) {
		dept = dep.get();
		int employeNb = dept.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dept.getEmployes().get(index).getId() == employeId){
				l.trace("Entrer Test : le departement a l'employes avec employeId : "+employeId);
				dept.getEmployes().remove(index);
					l.info("remove employe from department done");
				break;
			}
		}
	} 
	}




	public String getEmployePrenomById(int employeId) {
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		Employe emp = new Employe();
		if (employeManagedEntity.isPresent()) {
			emp = employeManagedEntity.get();
			
		}
		return emp.getPrenom();

	}
	 
	public void deleteEmployeById(int employeId)
	{l.info("Starting getEmployePrenomById with id : "+employeId);
	Optional<Employe> employe = employeRepository.findById(employeId);
	Employe emp;
	if (employe.isPresent()) {
		emp = employe.get();
		for(Departement dep : employe.get().getDepartements()){
			dep.getEmployes().remove(emp);
		}

		employeRepository.delete(employe.get());
		l.info(" deleteEmployeById with id : "+employeId + "is Done");
	}}



	public int getNombreEmployeJPQL() {
		l.info("Starting getNombreEmployeJPQL");
		l.info("getNombreEmployeJPQL is : " + employeRepository.countemp());
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		l.info("Starting getAllEmployeNamesJPQL");
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		l.info("Starting getAllEmployeByEntreprise");
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		l.info("Starting mettreAjourEmailByEmployeIdJPQL with email : "+email + "and employeId : "+employeId);

		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
        employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		l.info("Starting getSalaireByEmployeIdJPQL with employeId : "+employeId);
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		l.info("Starting getSalaireMoyenByDepartementId with departementId : "+departementId);
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		l.info("Starting getTimesheetsByMissionAndDate");
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		l.info("Starting getAllEmployes");
		return (List<Employe>) employeRepository.findAll();
	}
	
	

}
