package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.iutmetz.td3.dao.lm.ListeMemoireAbonnementDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoireClientDAO;
import fr.iutmetz.td3.exceptions.ExistingCompositeKeyException;
import fr.iutmetz.td3.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td3.pojo.Abonnement;
import fr.iutmetz.td3.pojo.Client;

public class ListeMemoireClientDAOTest {
	@Before
	public void setUp() {
		ListeMemoireClientDAO.getInstance().getAll().clear();
	}
	
	/*
	 * CREATE
	 */
	
	@Test
	public void testCreateOk() {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		boolean create = ListeMemoireClientDAO.getInstance().create(client);
		assertTrue(create);
	}
	
	@Test
	public void testCreateAddToListOk() {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		ListeMemoireClientDAO.getInstance().create(client);
		assertEquals(ListeMemoireClientDAO.getInstance().getAll().size(), 1);
	}
	
	@Test
	public void testCreateExist() {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		Client client2 = new Client(0, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client2);
		assertNotEquals(ListeMemoireClientDAO.getInstance().getAll().get(0).getId_client(), ListeMemoireClientDAO.getInstance().getAll().get(1).getId_client());
	}
	
	
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateOk() throws NonExistentDataObjectException {
		
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		ArrayList<Client> clients = ListeMemoireClientDAO.getInstance().getAll();
		
		Client clientUpdate = new Client(clients.get(clients.size()-1).getId_client(), "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		assertTrue(ListeMemoireClientDAO.getInstance().update(clientUpdate));
	}
	
	@Test
	public void testUpdateIsModified() throws NonExistentDataObjectException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		ArrayList<Client> clients = ListeMemoireClientDAO.getInstance().getAll();
		
		Client clientUpdate = new Client(clients.get(clients.size()-1).getId_client(), "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().update(clientUpdate);
		assertEquals(ListeMemoireClientDAO.getInstance().getById(clientUpdate.getId_client()), clientUpdate);
	}
	
	@Test
	public void testUpdateNotExistException() {
		Client clientUpdate = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		try {
			ListeMemoireClientDAO.getInstance().update(clientUpdate);
			fail("Exception non lancé");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testUpdateExistException() {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		ListeMemoireClientDAO.getInstance().create(client);
		
		ArrayList<Client> clients = ListeMemoireClientDAO.getInstance().getAll();

		Client clientUpdate = new Client(clients.get(clients.size()-1).getId_client(), "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		try {
			ListeMemoireClientDAO.getInstance().update(clientUpdate);
			
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	/*
	 * DELETE 
	 */
	
	@Test
	public void testDeleteOk() throws NonExistentDataObjectException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		assertTrue(ListeMemoireClientDAO.getInstance().delete(client));
	}
	
	@Test
	public void testDeleteIsModified() throws NonExistentDataObjectException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);

		ListeMemoireClientDAO.getInstance().delete(client);
		assertNull(ListeMemoireClientDAO.getInstance().getById(client.getId_client()));
	}
	
	@Test
	public void testDeleteNotExistException() {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		try {
			ListeMemoireClientDAO.getInstance().delete(client);
			fail("Exception non lancé.");
		} catch(NonExistentDataObjectException e) {
			// Nothing
		}
	}
	
	@Test
	public void testDeleteExistException() throws ExistingCompositeKeyException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		try {
			ListeMemoireClientDAO.getInstance().delete(client);
		} catch(NonExistentDataObjectException e) {
			fail("Exception lancé par erreur.");
		}
	}
	
	
	/*
	 * getAll
	 */
	
	@Test
	public void testGetAllOk() {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		Client client2 = new Client(0, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client2);

		
		ArrayList<Client> clients = ListeMemoireClientDAO.getInstance().getAll();
		assertFalse(clients.isEmpty());
		
	}
	
	@Test
	public void testGetAllIsSame() {
		List<Client> data = new ArrayList<Client>();
		data = ListeMemoireClientDAO.getInstance().getAll();
		
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client);
		
		Client client2 = new Client(0, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		ListeMemoireClientDAO.getInstance().create(client2);
		
		data.add(client);
		data.add(client2);
		
		ArrayList<Client> clients = ListeMemoireClientDAO.getInstance().getAll();
		assertTrue(clients.containsAll(data));
	}
}
