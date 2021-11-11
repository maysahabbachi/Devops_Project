package tn.esprit.spring.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.dtoEntities.DepartementDTo;
import tn.esprit.spring.services.IDepartementService;

@Controller
public class ControllerDepartementImpl {
	@Autowired
	IDepartementService idepartementservice;

	public Integer ajouterDepartement(DepartementDTo dep) {
		return idepartementservice.ajouterDepartement(dep);
	}


}