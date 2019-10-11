package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.iutmetz.td3.dao.mysql.MySQLClientDAO;
import fr.iutmetz.td3.dao.mysql.MySQLPeriodiciteDAO;
import fr.iutmetz.td3.pojo.Client;
import fr.iutmetz.td3.pojo.Periodicite;

public class MySQLPeriodiciteDAOTest {
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
	    String sql = "TRUNCATE periodicite";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM periodicite";
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
		Periodicite period = new Periodicite(0, "Test");
		boolean create = MySQLPeriodiciteDAO.getInstance().create(period);
		
		assertTrue(create);
	}
	
	@Test
	public void testCreateIsSame() throws SQLException {
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		// On récupère l'ID du dernier élément inséré
		ResultSet res = connect.createStatement().executeQuery("SELECT MAX(id_periodicite), libelle FROM periodicite");
		res.last();
		int id = res.getInt(1);
		String libelle = res.getString(2);

		period.setId(id);
		
		Periodicite periodDB = new Periodicite(id, libelle);
		assertEquals(period.hashCode(), periodDB.hashCode());
	}
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateIsSame() throws SQLException {
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_periodicite) FROM periodicite");
		resId.last();
		int id = resId.getInt(1);
		period.setId(id);
		
		Periodicite periodUpdate = new Periodicite(id, "TestUpdate");
		MySQLPeriodiciteDAO.getInstance().update(periodUpdate);
		
		ResultSet res = connect.createStatement().executeQuery("SELECT libelle FROM periodicite WHERE id_periodicite = " + id);
		res.last();
		String libelle = res.getString(1);

		Periodicite periodCheck = new Periodicite(id, libelle);
		assertEquals(periodUpdate.hashCode(), periodCheck.hashCode());
	}
	
	@Test
	public void testUpdateOk() throws SQLException {
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_periodicite) FROM periodicite");
		resId.last();
		int id = resId.getInt(1);
		period.setId(id);
		
		Periodicite periodUpdate = new Periodicite(id, "TestUpdate");
		
		boolean update = MySQLPeriodiciteDAO.getInstance().update(periodUpdate);
		
		assertTrue(update);
	}
	
	/*
	 * DELETE
	 */
	
	@Test
	public void testDeleteOk() throws SQLException {
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_periodicite) FROM periodicite");
		resId.last();
		int id = resId.getInt(1);
		period.setId(id);
		
		boolean delete = MySQLPeriodiciteDAO.getInstance().delete(period);
		
		assertTrue(delete);
	}
	
	@Test
	public void testDeleteNotExist() throws SQLException {
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_periodicite) FROM periodicite");
		resId.last();
		int id = resId.getInt(1);
		period.setId(id);
		
		MySQLPeriodiciteDAO.getInstance().delete(period);
		Periodicite periodCheck = MySQLPeriodiciteDAO.getInstance().getById(period.getId());
		assertNull(periodCheck);
	}
	
	@Test
	public void testGetAllOk() throws SQLException {
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		Periodicite period2 = new Periodicite(0, "Test2");
		MySQLPeriodiciteDAO.getInstance().create(period2);
		
		ArrayList<Periodicite> periodicites = MySQLPeriodiciteDAO.getInstance().getAll();
		assertFalse(periodicites.isEmpty());
	}
	
	@Test
	public void testGetAllIsSame() throws SQLException {
		List<Periodicite> data = new ArrayList<Periodicite>();
		
		Periodicite period = new Periodicite(0, "Test");
		MySQLPeriodiciteDAO.getInstance().create(period);
		
		Periodicite period2 = new Periodicite(0, "Test2");
		MySQLPeriodiciteDAO.getInstance().create(period2);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_periodicite) FROM periodicite");
		resId.last();
		int id = resId.getInt(1);
		period.setId(id-1);
		period2.setId(id);
		data.add(period);
		data.add(period2);
		
		ArrayList<Periodicite> periodicites = MySQLPeriodiciteDAO.getInstance().getAll();
		assertTrue(periodicites.containsAll(data));
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
	    String sql = "TRUNCATE periodicite";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM periodicite";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
