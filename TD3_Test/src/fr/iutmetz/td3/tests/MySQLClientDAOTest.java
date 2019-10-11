package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.iutmetz.td3.dao.mysql.MySQLAbonnementDAO;
import fr.iutmetz.td3.dao.mysql.MySQLClientDAO;
import fr.iutmetz.td3.pojo.Abonnement;
import fr.iutmetz.td3.pojo.Client;

public class MySQLClientDAOTest {
	private Connection connect = null;
	
	@Before
	public void setUp() {
		/* Version IUT
		String url = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/fauvet5u_java";
		url += "?serverTimezone=Europe/Paris";
		String login = "fauvet5u_appli";
		String pwd = "31806272"; */

		String url = "jdbc:mysql://localhost/java_td1";
		String login = "root";
		String pwd = "";

		try {
			connect = DriverManager.getConnection(url, login, pwd);
		} catch(SQLException sqle) {
			System.out.println("Erreur de connexion : " + sqle.getMessage());
		}
		
		 // Use TRUNCATE
	    String sql = "TRUNCATE client";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM client";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * CREATE
	 */
	@Test
	public void testCreateOk() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		boolean create = MySQLClientDAO.getInstance().create(client);
		
		assertTrue(create);
	}
	
	@Test
	public void testCreateIsSame() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		MySQLClientDAO.getInstance().create(client);
		
		// On récupère l'ID du dernier élément inséré
		
		ResultSet res = connect.createStatement().executeQuery("SELECT MAX(id_client), nom, prenom, no_rue, voie, code_postal, ville, pays FROM client");
		res.last();
		int id = res.getInt(1);
		String nom = res.getString(2);
		String prenom = res.getString(3);
		String no_rue = res.getString(4);
		String voie = res.getString(5);
		String code_postal = res.getString(6);
		String ville = res.getString(7);
		String pays = res.getString(8);

		client.setId_client(id);
		
		Client clientDB = new Client(id, nom, prenom, no_rue, voie, code_postal, ville, pays);
		assertEquals(client.hashCode(), clientDB.hashCode());
	}
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateIsSame() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_client) FROM client");
		resId.last();
		int id = resId.getInt(1);
		client.setId_client(id);
		
		Client clientUpdate = new Client(id, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().update(clientUpdate);
		
		ResultSet res = connect.createStatement().executeQuery("SELECT nom, prenom, no_rue, voie, code_postal, ville, pays FROM client WHERE id_client = " + id);
		res.last();
		String nom = res.getString(1);
		String prenom = res.getString(2);
		String no_rue = res.getString(3);
		String voie = res.getString(4);
		String code_postal = res.getString(5);
		String ville = res.getString(6);
		String pays = res.getString(7);

		Client clientCheck = new Client(id, nom, prenom, no_rue, voie, code_postal, ville, pays);
		assertEquals(clientUpdate.hashCode(), clientCheck.hashCode());
	}
	
	@Test
	public void testUpdateOk() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_client) FROM client");
		resId.last();
		int id = resId.getInt(1);
		client.setId_client(id);
		
		Client clientUpdate = new Client(id, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		
		boolean update = MySQLClientDAO.getInstance().update(clientUpdate);
		
		assertTrue(update);
	}
	
	/*
	 * DELETE
	 */
	
	@Test
	public void testDeleteOk() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_client) FROM client");
		resId.last();
		int id = resId.getInt(1);
		client.setId_client(id);
		
		boolean delete = MySQLClientDAO.getInstance().delete(client);
		
		assertTrue(delete);
	}
	
	@Test
	public void testDeleteNotExist() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_client) FROM client");
		resId.last();
		int id = resId.getInt(1);
		client.setId_client(id);
		
		MySQLClientDAO.getInstance().delete(client);
		Client clientCheck = MySQLClientDAO.getInstance().getById(client.getId_client());
		assertNull(clientCheck);
	}
	
	@Test
	public void testGetAllOk() throws SQLException {
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client);
		
		Client client2 = new Client(0, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client2);
		
		ArrayList<Client> clients = MySQLClientDAO.getInstance().getAll();
		assertFalse(clients.isEmpty());
	}
	
	@Test
	public void testGetAllIsSame() throws SQLException {
		List<Client> data = new ArrayList<Client>();
		
		Client client = new Client(0, "Dudon", "Adrien", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client);
		
		Client client2 = new Client(0, "Fauvet", "Olivier", "37", "Rue des Vosges", "57565", "Niderviller", "France");
		MySQLClientDAO.getInstance().create(client2);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_client) FROM client");
		resId.last();
		int id = resId.getInt(1);
		client.setId_client(id-1);
		client2.setId_client(id);
		data.add(client);
		data.add(client2);
		
		ArrayList<Client> clients = MySQLClientDAO.getInstance().getAll();
		assertTrue(clients.containsAll(data));
	}
	
	@After
	public void logout() {
		/* Version IUT
		String url = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/fauvet5u_java";
		url += "?serverTimezone=Europe/Paris";
		String login = "fauvet5u_appli";
		String pwd = "31806272"; */

		String url = "jdbc:mysql://localhost/java_td1";
		String login = "root";
		String pwd = "";

		Connection connect = null;

		try {
			connect = DriverManager.getConnection(url, login, pwd);
		} catch(SQLException sqle) {
			System.out.println("Erreur de connexion : " + sqle.getMessage());
		}
		
		 // Use TRUNCATE
	    String sql = "TRUNCATE client";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM client";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
