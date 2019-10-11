package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import fr.iutmetz.td3.pojo.Abonnement;

public class AbonnementTest {
	
	private Abonnement aboTest;
	
	@Before
	public void setUp() {
		this.aboTest = new Abonnement();
	}

	@Test
	public void testDefaultConstructorAttributesCreatedOk() {
		
		assertNotNull(this.aboTest.getId_client());
		assertNotNull(this.aboTest.getId_revue());
		assertNotNull(this.aboTest.getDate_debut());
		assertNotNull(this.aboTest.getDate_fin());
	}
	
	@Test
	public void testParametersConstructorAttributesCreatedOk() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse("01/01/1900", format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", format);
			
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		assertNotNull(abo.getId_client());
		assertNotNull(abo.getId_revue());
		assertNotNull(abo.getDate_debut());
		assertNotNull(abo.getDate_fin());
	}
	
	@Test
	public void testGetId_clientOk() {
		this.aboTest.setId_client(1);
		
		assertEquals(1, this.aboTest.getId_client());
	}
	
	@Test
	public void testSetId_clientOk() {
		this.aboTest.setId_client(1);
		assertEquals(this.aboTest.getId_client(), 1);
	}
	
	@Test
	public void testGet_Id_revueOk() {
		this.aboTest.setId_revue(1);
		
		assertEquals(1, this.aboTest.getId_revue());
	}
	
	@Test
	public void testSetId_revueOk() {
		this.aboTest.setId_revue(1);
		assertEquals(this.aboTest.getId_revue(), 1);
	}
	
	@Test
	public void testGetDate_debutOk() {
		Abonnement abo = new Abonnement();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse("01/01/1900", format);
		
		this.aboTest.setDate_debut(dateDebut);
		
		assertEquals(dateDebut, this.aboTest.getDate_debut());
	}
	
	@Test
	public void testSetDate_debutOk() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse("01/01/1900", format);
		
		this.aboTest.setDate_debut(dateDebut);
		
		assertEquals(this.aboTest.getDate_debut(), dateDebut);
	}
	
	@Test
	public void testGetDate_finOk() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFin = LocalDate.parse("31/12/1900", format);
		
		this.aboTest.setDate_debut(dateFin);
		
		assertEquals(dateFin, this.aboTest.getDate_debut());
	}
	
	@Test
	public void testSetDate_finOk() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateFin = LocalDate.parse("31/12/1900", format);
		
		this.aboTest.setDate_debut(dateFin);
		
		assertEquals(this.aboTest.getDate_debut(), dateFin);
	}
}
