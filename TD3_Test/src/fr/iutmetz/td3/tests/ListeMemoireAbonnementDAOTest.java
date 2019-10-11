package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.iutmetz.td3.dao.lm.ListeMemoireAbonnementDAO;
import fr.iutmetz.td3.exceptions.ExistingCompositeKeyException;
import fr.iutmetz.td3.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td3.pojo.Abonnement;

public class ListeMemoireAbonnementDAOTest {
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Before
	public void setUp() {
		ListeMemoireAbonnementDAO.getInstance().getAll().clear();
	}
	
	/*
	 * CREATE
	 */
	
	@Test
	public void testCreateOk() throws ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		boolean create = ListeMemoireAbonnementDAO.getInstance().create(abo);
		assertTrue(create);
	}
	
	@Test
	public void testCreateAddToList() throws ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		assertEquals(ListeMemoireAbonnementDAO.getInstance().getAll().size(), 1);
	}
	
	@Test
	public void testCreateAlreadyExistException() throws ExistingCompositeKeyException  {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		
		Abonnement abo2 = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			ListeMemoireAbonnementDAO.getInstance().create(abo);
			fail("Exception non lancée.");
		} catch (ExistingCompositeKeyException e) {
			// Nothing
		}
	}
	
	@Test
	public void testCreateNotExistException() {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			ListeMemoireAbonnementDAO.getInstance().create(abo);
		} catch (ExistingCompositeKeyException e) {
			fail("Exception lancée par erreur.");
		}
	}
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateOk() throws NonExistentDataObjectException, ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		
		dateFin = LocalDate.parse("31/12/1990", this.format);
		Abonnement aboUpdate = new Abonnement(1, 2, dateDebut, dateFin);
		assertTrue(ListeMemoireAbonnementDAO.getInstance().update(aboUpdate));
	}
	
	@Test
	public void testUpdateIsModified() throws NonExistentDataObjectException, ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		ListeMemoireAbonnementDAO.getInstance().create(abo);

		dateFin = LocalDate.parse("31/12/1990", this.format);

		Abonnement aboUpdate = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().update(aboUpdate);
		assertEquals(ListeMemoireAbonnementDAO.getInstance().getByIds(aboUpdate.getId_client(), aboUpdate.getId_revue()), aboUpdate);
	}
	
	@Test
	public void testUpdateNotExistException() {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		Abonnement aboUpdate = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			ListeMemoireAbonnementDAO.getInstance().update(aboUpdate);
			fail("Exception non lancé");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testUpdateExistException() throws ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		ListeMemoireAbonnementDAO.getInstance().create(abo);

		dateFin = LocalDate.parse("31/12/1990", this.format);

		Abonnement aboUpdate = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			ListeMemoireAbonnementDAO.getInstance().update(aboUpdate);
			
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	/*
	 * DELETE
	 */
	
	@Test
	public void testDeleteOk() throws NonExistentDataObjectException, ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		
		assertTrue(ListeMemoireAbonnementDAO.getInstance().delete(abo));
	}
	
	@Test
	public void testDeleteIsModified() throws NonExistentDataObjectException, ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().create(abo);

		ListeMemoireAbonnementDAO.getInstance().delete(abo);
		assertNull(ListeMemoireAbonnementDAO.getInstance().getByIds(abo.getId_client(), abo.getId_revue()));
	}
	
	@Test
	public void testDeleteNotExistException() {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			ListeMemoireAbonnementDAO.getInstance().delete(abo);
			fail("Exception non lancé.");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testDeleteExistException() throws ExistingCompositeKeyException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		
		try {
			ListeMemoireAbonnementDAO.getInstance().delete(abo);
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	/*
	 * getAll
	 */
	
	@Test
	public void testGetAllOk() throws ExistingCompositeKeyException {
		LocalDate dateDebut1 = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin1 = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut1, dateFin1);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		
		LocalDate dateDebut2 = LocalDate.parse("01/01/1990", this.format);
		LocalDate dateFin2 = LocalDate.parse("31/12/1990", this.format);
		Abonnement abo2 = new Abonnement(2, 3, dateDebut2, dateFin2);
		ListeMemoireAbonnementDAO.getInstance().create(abo2);

		
		ArrayList<Abonnement> abonnements = ListeMemoireAbonnementDAO.getInstance().getAll();
		assertFalse(abonnements.isEmpty());
		
	}
	
	@Test
	public void testGetAllIsSame() throws ExistingCompositeKeyException {
		List<Abonnement> data = new ArrayList<Abonnement>();
		data = ListeMemoireAbonnementDAO.getInstance().getAll();
		
		LocalDate dateDebut1 = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin1 = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut1, dateFin1);
		ListeMemoireAbonnementDAO.getInstance().create(abo);
		
		LocalDate dateDebut2 = LocalDate.parse("01/01/1990", this.format);
		LocalDate dateFin2 = LocalDate.parse("31/12/1990", this.format);
		Abonnement abo2 = new Abonnement(2, 3, dateDebut2, dateFin2);
		ListeMemoireAbonnementDAO.getInstance().create(abo2);
		
		data.add(abo);
		data.add(abo2);
		
		ArrayList<Abonnement> abonnements = ListeMemoireAbonnementDAO.getInstance().getAll();
		assertTrue(abonnements.containsAll(data));
	}
}
