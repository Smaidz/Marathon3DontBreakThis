package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organizer;
import com.example.demo.repo.OrganizerRepo;

@Service
public class AdminServiceIMPL implements AdminService{

	@Autowired
	OrganizerRepo organizerRepo;
	
	@Override
	public ArrayList<Organizer> selectAll() {
		ArrayList<Organizer> tempList = new ArrayList<Organizer>();
		for (Organizer o:organizerRepo.findAll())
		{
			tempList.add(o);
		}
		return tempList;
	}
 	
	@Override
	public Organizer selectById_org(long id_org) {
		if (id_org >=0) {
		Organizer orgTemp = organizerRepo.findById(id_org).get();	
		if(orgTemp!=null)
			return orgTemp;
		}
		return null;
	}
	@Override
	public boolean addNewOrganizer(Organizer organizer) {
		if (organizer==null)
			return false;
		Organizer orgTemp = organizerRepo.findByLoginAndPassword(organizer.getLogin(), organizer.getPassword());
		if(orgTemp!=null) {
			return false;
		}
		else
		{	
			organizerRepo.save(organizer);
			return true;
		}
	}	
	@Override
	public boolean updateOrganizerById_org(Organizer organizer, long id_org) {
		if (organizerRepo.existsById(id_org) && organizer!=null) {
			Organizer orgTemp = organizerRepo.findById(id_org).get();
			orgTemp.setName(organizer.getName());
			orgTemp.setLogin(organizer.getLogin());
			orgTemp.setPassword(organizer.getPassword());
			organizerRepo.save(organizer);
			return true;
		}
		return false;
	}
		
	@Override
	public boolean deleteOrganizerById_org(long id_org) {
		if (organizerRepo.existsById(id_org))
		{
		Organizer orgTemp = organizerRepo.findById(id_org).get();
			organizerRepo.delete(orgTemp);
			return true;
		}
		return false;
	}
	@Override
	public boolean deleteOrganizerByObject(Organizer organizer) {
		if(organizerRepo.existsById(organizer.getId_org()) && organizer!=null)
		{
			organizerRepo.delete(organizer);
			return true;
		}
	return false;	
}
}
