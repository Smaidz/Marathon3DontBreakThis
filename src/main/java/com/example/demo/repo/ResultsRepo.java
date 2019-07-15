package com.example.demo.repo;

import java.security.Timestamp;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Marathon;
import com.example.demo.model.Results;
import com.example.demo.model.User;
@Repository
public interface ResultsRepo extends CrudRepository<Results, Long> {
	Results findByUserAndMarathonAndDisqualifiedAndTimeResult
	(User user, Marathon marathon, boolean disqualified, String timeResult);
	ArrayList<Results> findByMarathon(Marathon maraton);

}
