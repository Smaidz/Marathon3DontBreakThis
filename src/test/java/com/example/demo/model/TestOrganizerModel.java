package com.example.demo.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOrganizerModel {

	Organizer orgA;
	Organizer orgB;
	
	@Before
	public void setup()
	{
		orgA = new Organizer ("Organizer1", "OrgLogin", "password1","email@email.com");
		orgB = new Organizer ("Organizer2", "", "","ema+il2@em.ail.com");
	}	
	
	@Test
	public void testOrgA()
	{
		assertEquals("Organizer1", orgA.getName());
		assertEquals("OrgLogin", orgA.getLogin());
		assertEquals("password1", orgA.getPassword());
		assertEquals("email@email.com", orgA.getOrgemail());
	}
	
	@Test
	public void testOrgB()
	{
		assertEquals("Name Test", "Organizer2", orgA.getName());
		assertEquals("OrgLoginTest","Login", orgA.getLogin());	
		assertEquals("Password Test", "password1", orgB.getPassword());
		assertEquals("Email Test", "email@email.com", orgA.getOrgemail());
	}
	
}
