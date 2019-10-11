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

import fr.iutmetz.td3.dao.lm.ListeMemoirePeriodiciteDAO;
import fr.iutmetz.td3.exceptions.ExistingCompositeKeyException;
import fr.iutmetz.td3.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td3.pojo.Periodicite;

public class ListeMemoirePeriodiciteDAOTest {
	@Before
	public void setUp() {
		ListeMemoirePeriodiciteDAO.getInstance().getAll().clear();
	}
	
	/*
	 * CREATE
	 */
	
	@Test
	public void testCreateOk() {
		Periodicite period = new Periodicite(0, "Test");
		
		boolean create = ListeMemoirePeriodiciteDAO.getInstance().create(period);
		assertTrue(create);
	}
	
	@Test
	public void testCreateAddToListOk() {
		Periodicite period = new Periodicite(0, "Test");
		
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		assertEquals(ListeMemoirePeriodiciteDAO.getInstance().getAll().size(), 1);
	}
	
	@Test
	public void testCreateExist() {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		Periodicite period2 = new Periodicite(0, "Test2");
		ListeMemoirePeriodiciteDAO.getInstance().create(period2);
		assertNotEquals(ListeMemoirePeriodiciteDAO.getInstance().getAll().get(0).getId(), ListeMemoirePeriodiciteDAO.getInstance().getAll().get(1).getId());
	}
	
	
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateOk() throws NonExistentDataObjectException {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		ArrayList<Periodicite> periodicites = ListeMemoirePeriodiciteDAO.getInstance().getAll();
		
		Periodicite periodUpdate = new Periodicite(periodicites.get(periodicites.size()-1).getId(), "TestUpdate");
		assertTrue(ListeMemoirePeriodiciteDAO.getInstance().update(periodUpdate));
	}
	
	@Test
	public void testUpdateIsModified() throws NonExistentDataObjectException {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		ArrayList<Periodicite> periodicites = ListeMemoirePeriodiciteDAO.getInstance().getAll();
		
		Periodicite periodUpdate = new Periodicite(periodicites.get(periodicites.size()-1).getId(), "TestUpdate");
		ListeMemoirePeriodiciteDAO.getInstance().update(periodUpdate);
		assertEquals(ListeMemoirePeriodiciteDAO.getInstance().getById(periodUpdate.getId()), periodUpdate);
	}
	
	@Test
	public void testUpdateNotExistException() {
		Periodicite periodUpdate = new Periodicite(0, "Test");
		
		try {
			ListeMemoirePeriodiciteDAO.getInstance().update(periodUpdate);
			fail("Exception non lancé");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testUpdateExistException() {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		ArrayList<Periodicite> periodicites = ListeMemoirePeriodiciteDAO.getInstance().getAll();
		
		Periodicite periodUpdate = new Periodicite(periodicites.get(periodicites.size()-1).getId(), "TestUpdate");
		
		try {
			ListeMemoirePeriodiciteDAO.getInstance().update(periodUpdate);
			
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	/*
	 * DELETE 
	 */
	
	@Test
	public void testDeleteOk() throws NonExistentDataObjectException {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		assertTrue(ListeMemoirePeriodiciteDAO.getInstance().delete(period));
	}
	
	@Test
	public void testDeleteIsModified() throws NonExistentDataObjectException {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);

		ListeMemoirePeriodiciteDAO.getInstance().delete(period);
		assertNull(ListeMemoirePeriodiciteDAO.getInstance().getById(period.getId()));
	}
	
	@Test
	public void testDeleteNotExistException() {
		Periodicite period = new Periodicite(0, "Test");
		
		try {
			ListeMemoirePeriodiciteDAO.getInstance().delete(period);
			fail("Exception non lancé.");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testDeleteExistException() throws ExistingCompositeKeyException {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		try {
			ListeMemoirePeriodiciteDAO.getInstance().delete(period);
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	
	/*
	 * getAll
	 */
	
	@Test
	public void testGetAllOk() {
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		Periodicite period2 = new Periodicite(0, "Test2");
		ListeMemoirePeriodiciteDAO.getInstance().create(period2);

		
		ArrayList<Periodicite> periodicites = ListeMemoirePeriodiciteDAO.getInstance().getAll();
		assertFalse(periodicites.isEmpty());
		
	}
	
	@Test
	public void testGetAllIsSame() {
		List<Periodicite> data = new ArrayList<Periodicite>();
		data = ListeMemoirePeriodiciteDAO.getInstance().getAll();
		
		Periodicite period = new Periodicite(0, "Test");
		ListeMemoirePeriodiciteDAO.getInstance().create(period);
		
		Periodicite period2 = new Periodicite(0, "Test2");
		ListeMemoirePeriodiciteDAO.getInstance().create(period2);
		
		data.add(period);
		data.add(period2);
		
		ArrayList<Periodicite> periodicites = ListeMemoirePeriodiciteDAO.getInstance().getAll();
		assertTrue(periodicites.containsAll(data));
	}
}
