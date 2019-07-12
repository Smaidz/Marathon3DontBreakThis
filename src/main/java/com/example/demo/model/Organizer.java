package com.example.demo.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="OrganizerTable")
public class Organizer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id_org")
	private long id_org;
	
	@NotEmpty
	@NotNull
	@Size(min=2, max=25)
	@Pattern(regexp="[a-zA-Z\\s]+")
	@Column(name="Name")
	private String name;
	
	@NotEmpty
	@NotNull
	@Size(min=3, max=15)
	@Pattern(regexp="[a-zA-Z\\d]+", message="Only letters and numbers are allowed.")
	@Column(name="Login")
	private String login;
	
	@NotEmpty
	@NotNull
	@Size(min=8, max=32)
	@Pattern(regexp="[a-zA-Z\\d]+", message="Min 8 symbols, letters and numbers are allowed")
	@Column(name="Password")
	private String password;
	
	@OneToMany(mappedBy="organizer")
	private Collection<Marathon> marathons;
	//TODO Get Marathon class from someone, I think that's done now but I'll just leave this here for fun
	
	@Column(name="Email")
	private String orgemail;
	
	
	public Organizer()
	
	{
		
	}
	
	public Organizer(long id_org, String name, String login, String password, String orgemail)
	{
		//engine = e;
		//speed = s;
		//year = y;
		//color = c;
		setId_org(id_org);
		setName(name);
		setLogin(login);
		setPassword(password);
		setOrgemail(orgemail);
		//setId();
	}

	public long getId_org() {
		return id_org;
	}
	public void setId_org(long id_org) {
		this.id_org = id_org;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrgemail() {
		return orgemail;
	}
	public void setOrgemail(String orgemail) {
		this.orgemail = orgemail;
	}
	
}
