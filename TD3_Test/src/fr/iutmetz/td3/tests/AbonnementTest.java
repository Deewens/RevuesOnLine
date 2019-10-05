package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import fr.iutmetz.td3.pojo.Abonnement;

public class AbonnementTest {

	@Test
	public void testDefaultConstructorAttributesCreatedOk() {
		
		Abonnement abo = new Abonnement();
		assertNotNull(abo.getId_client());
		assertNotNull(abo.getId_revue());
		assertNotNull(abo.getDate_debut());
		assertNotNull(abo.getDate_fin());
	}
	
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
	
	
}
