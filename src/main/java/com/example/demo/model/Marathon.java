package com.example.demo.model;



import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="MarathonTable")
public class Marathon {
	@Id // value will be unique
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_Mar") // id calumn will be in table
	private long ID_mar;
	
	
	@NotEmpty
	@NotNull
	@Size(min=3, max =20)
	@Column(name="Name")
	private String name;
	
	@NotNull
	@Min(0)
	@Max(60)
	@Column(name="Distance")
	private double distance;
	
	@NotEmpty
	@NotNull
	@Size(min=3, max =15)
	@Column(name="Place")
	private String place;
	
	@NotEmpty
	@NotNull
	@Column(name="Date")
	@Size(min=3, max =11)
	//private LocalDate date;
	private String date;
	
	@NotNull
	@Min(100)
	@Max(2400)
	@Column(name="Time")
	private int time;
	
	@ManyToMany
	@JoinTable(name="Users_Marathons", 
		joinColumns = @JoinColumn(name ="ID_Usr"), 
		inverseJoinColumns = @JoinColumn(name="ID_Mar"))
	Collection<User> marathonParticipants;
	
	@ManyToOne
	@JoinColumn(name="Id_org")
	private Organizer organizer;
	
	public Marathon() {
		
	}

	
	public Marathon(String n, int dis, String p, String dat, int t) {
		name = n;
		distance= dis;
		place = p;
		date= dat;
		time=t;
	}
	
	

	public long getId() {
		return ID_mar;
	}
	/*
	public void setId(int id) {
		this.id = id;
	}
	*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "Marathon [id=" + ID_mar + ", name=" + name + ", distance=" + distance + ", place=" + place + ", date="
				+ date + ", time=" + time + "]";
	}


	public Collection<User> getMarathonParticipants() {
		return marathonParticipants;
	}


	public void setMarathonParticipants(Collection<User> marathonParticipants) {
		this.marathonParticipants = marathonParticipants;
	}
	
	public void addMarathonParticipant(User user) {
		marathonParticipants.add(user);
	}


	public Organizer getOrganizer() {
		return organizer;
	}


	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}
	
	
	
}