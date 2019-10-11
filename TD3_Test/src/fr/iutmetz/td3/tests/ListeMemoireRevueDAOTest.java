package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.iutmetz.td3.dao.lm.ListeMemoireClientDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoirePeriodiciteDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoireRevueDAO;
import fr.iutmetz.td3.exceptions.ExistingCompositeKeyException;
import fr.iutmetz.td3.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td3.pojo.Periodicite;
import fr.iutmetz.td3.pojo.Revue;

public class ListeMemoireRevueDAOTest {
	@Before
	public void setUp() {
		ListeMemoireRevueDAO.getInstance().getAll().clear();
	}
	
	/*
	 * CREATE
	 */
	
	@Test
	public void testCreateOk() {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		
		boolean create = ListeMemoireRevueDAO.getInstance().create(revue);
		assertTrue(create);
	}
	
	@Test
	public void testCreateAddToListOk() {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		
		ListeMemoireRevueDAO.getInstance().create(revue);
		assertEquals(ListeMemoireRevueDAO.getInstance().getAll().size(), 1);
	}
	
	@Test
	public void testCreateExist() {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);
		
		Revue revue2 = new Revue(0, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue2);
		assertNotEquals(ListeMemoireRevueDAO.getInstance().getAll().get(0).getId_revue(), ListeMemoireRevueDAO.getInstance().getAll().get(1).getId_revue());
	}
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateOk() throws NonExistentDataObjectException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);
		
		ArrayList<Revue> revues = ListeMemoireRevueDAO.getInstance().getAll();
		
		Revue revueUpdate = new Revue(revues.get(revues.size()-1).getId_revue(), "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		assertTrue(ListeMemoireRevueDAO.getInstance().update(revueUpdate));
	}
	
	@Test
	public void testUpdateIsModified() throws NonExistentDataObjectException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);
		
		ArrayList<Revue> revues = ListeMemoireRevueDAO.getInstance().getAll();
		
		Revue revueUpdate = new Revue(revues.get(revues.size()-1).getId_revue(), "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().update(revueUpdate);
		assertEquals(ListeMemoireRevueDAO.getInstance().getById(revueUpdate.getId_revue()), revueUpdate);
	}
	
	@Test
	public void testUpdateNotExistException() {
		Revue revueUpdate = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		
		try {
			ListeMemoireRevueDAO.getInstance().update(revueUpdate);
			fail("Exception non lancé");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testUpdateExistException() {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);
		
		ArrayList<Revue> revues = ListeMemoireRevueDAO.getInstance().getAll();
		
		Revue revueUpdate = new Revue(revues.get(revues.size()-1).getId_revue(), "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		
		try {
			ListeMemoireRevueDAO.getInstance().update(revueUpdate);
			
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	/*
	 * DELETE 
	 */
	
	@Test
	public void testDeleteOk() throws NonExistentDataObjectException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);
		
		assertTrue(ListeMemoireRevueDAO.getInstance().delete(revue));
	}
	
	@Test
	public void testDeleteIsModified() throws NonExistentDataObjectException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);

		ListeMemoireRevueDAO.getInstance().delete(revue);
		assertNull(ListeMemoireRevueDAO.getInstance().getById(revue.getId_revue()));
	}
	
	@Test
	public void testDeleteNotExistException() {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		
		try {
			ListeMemoireRevueDAO.getInstance().delete(revue);
			fail("Exception non lancé.");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testDeleteExistException() throws ExistingCompositeKeyException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue);
		
		try {
			ListeMemoireRevueDAO.getInstance().delete(revue);
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	
	/*
	 * getAll
	 */
	
	@Test
	public void testGetAllOk() {
		Revue revue1 = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue1);
		
		Revue revue2 = new Revue(0, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue2);

		
		ArrayList<Revue> revues = ListeMemoireRevueDAO.getInstance().getAll();
		assertFalse(revues.isEmpty());
		
	}
	
	@Test
	public void testGetAllIsSame() {
		List<Revue> data = new ArrayList<Revue>();
		data = ListeMemoireRevueDAO.getInstance().getAll();
		
		Revue revue1 = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue1);
		
		Revue revue2 = new Revue(0, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		ListeMemoireRevueDAO.getInstance().create(revue2);
		
		data.add(revue1);
		data.add(revue2);
		
		ArrayList<Revue> revues = ListeMemoireRevueDAO.getInstance().getAll();
		assertTrue(revues.containsAll(data));
	}
}
