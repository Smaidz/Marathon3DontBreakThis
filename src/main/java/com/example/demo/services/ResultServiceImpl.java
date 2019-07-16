package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repo.ResultsRepo;

@Service
public class ResultServiceImpl implements ResultService {
	
	@Autowired
	ResultsRepo resultsRepo;

	@Override
	public boolean deleteById_res(long id_res) {
		if(resultsRepo.existsById(id_res)) {
			resultsRepo.deleteById(id_res);
		}
		
		return false;
	}
	
	
	

}
