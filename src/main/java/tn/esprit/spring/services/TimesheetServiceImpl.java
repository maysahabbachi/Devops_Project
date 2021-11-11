package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import org.apache.log4j.Logger;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	
	private static final Logger l = Logger.getLogger(TimesheetServiceImpl.class);

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		l.info("Adding Mission");
		missionRepository.save(mission);
		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		l.info("Affecting Mission To Departement");
		Optional<Mission> mission = missionRepository.findById(missionId);
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if(mission.isPresent() && dep.isPresent()){
			
			mission.get().setDepartement(dep.get());
			missionRepository.save(mission.get());
			
		}
	
		}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		l.info("Adding TimeSheet");
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); 
		timesheetRepository.save(timesheet);
		
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		l.info("In valider Timesheet");
		Optional<Employe> validateur = employeRepository.findById(validateurId);
		Optional<Mission> mission = missionRepository.findById(missionId);
		if( validateur.isPresent() && !validateur.get().getRole().equals(Role.CHEF_DEPARTEMENT)){
			l.debug("l'employe doit etre chef de departement pour valider une feuille de temps !");
			return;
		}
		boolean chefDeLaMission = false;
		if(validateur.isPresent()){
		for(Departement dep : validateur.get().getDepartements()){
			if(mission.isPresent() && dep.getId() == mission.get().getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		}
		if(!chefDeLaMission){
			l.debug("l'employe doit etre chef de departement de la mission en question");
			return;
		}
//
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        l.debug("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		l.info("Finding All Mission By Employe JPQL");
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		l.info("Getting All Employees By Mission");
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
