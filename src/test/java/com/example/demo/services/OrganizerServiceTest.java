package com.example.demo.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.model.Organizer;
import com.example.demo.repo.OrganizerRepo;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrganizerServiceTest {
	AdminServiceIMPL organizerServiceImpl;
	
	@Mock
	OrganizerRepo organizerRepo;
	ArrayList<Organizer> organizerSimulation;
	
	Organizer orgA;
	Organizer orgB;
	
	@Before
	public void setup()
	{ 
		organizerServiceImpl = new AdminServiceIMPL();
		ReflectionTestUtils.setField(organizerServiceImpl, "organizerRepo", organizerRepo);
		
		orgA = new Organizer ("OrgLogin","Organizer1","email@email.com","password1");
		orgB = new Organizer ( "", "Organizer2","ema+il2@em.ail.com", "");
		organizerSimulation = new ArrayList<Organizer>();
		organizerSimulation.add(orgA);
		organizerSimulation.add(orgB);
	}
	@Test
	public void testSelectAll()
	{
		when(organizerRepo.findAll()).thenReturn(organizerSimulation);
		ArrayList<Organizer> retrieveOrg = organizerServiceImpl.selectAll();
		assertEquals(organizerSimulation, retrieveOrg);
		
		
	}
	
	@Test
	public void testaddNewOrg()
	{
		//TODO try to implement more tests
	}
	
}
