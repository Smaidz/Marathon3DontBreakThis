package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Marathon;
@Repository
public interface MarathonRepo extends CrudRepository<Marathon, Long>{
	
	//Marathon findByID(long id);
	Marathon findByNameAndDistanceAndPlaceAndDateAndTime(String name, double distance, String place, String date, int time);
	
	

}