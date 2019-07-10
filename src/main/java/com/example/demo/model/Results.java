package com.example.demo.model;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="ResultsTable")
public class Results {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id_res")
	private long id_res;
	
	@ManyToOne
	@JoinColumn(name="ID_Usr")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="ID_Mar")
	private Marathon marathon;
	
	
	@Column(name="Disqualified")
	private boolean disqualified;
	
	
	@Column(name="Result")
	private Timestamp result;
	
	public Results(boolean dis, Timestamp res){
		this.disqualified = dis;
		this.result = res;
	}
	
	public Results(){
		
	}

	public long getId_res() {
		return id_res;
	}

	public void setId_res(long id_res) {
		this.id_res = id_res;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Marathon getMarathon() {
		return marathon;
	}

	public void setMarathon(Marathon marathon) {
		this.marathon = marathon;
	}

	public boolean isDisqualified() {
		return disqualified;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public Timestamp getResult() {
		return result;
	}

	public void setResult(Timestamp result) {
		this.result = result;
	}
	
	
	
}
