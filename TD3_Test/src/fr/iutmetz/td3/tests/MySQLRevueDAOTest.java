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

import fr.iutmetz.td3.dao.mysql.MySQLPeriodiciteDAO;
import fr.iutmetz.td3.dao.mysql.MySQLRevueDAO;
import fr.iutmetz.td3.pojo.Periodicite;
import fr.iutmetz.td3.pojo.Revue;

public class MySQLRevueDAOTest {
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
	    String sql = "TRUNCATE revue";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM revue";
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
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		boolean create = MySQLRevueDAO.getInstance().create(revue);
		
		assertTrue(create);
	}
	
	@Test
	public void testCreateIsSame() throws SQLException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		// On récupère l'ID du dernier élément inséré
		ResultSet res = connect.createStatement().executeQuery("SELECT MAX(id_revue), titre, description, tarif_numero, visuel, id_periodicite FROM revue");
		res.last();
		int id = res.getInt(1);
		String titre = res.getString(2);
		String description = res.getString(3);
		double tarif_numero = res.getDouble(4);
		String visuel = res.getString(5);
		int id_periodicite = res.getInt(6);

		revue.setId_revue(id);
		
		Revue revueDB = new Revue(id, titre, description, tarif_numero, visuel, id_periodicite);
		assertEquals(revue.hashCode(), revueDB.hashCode());
	}
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateIsSame() throws SQLException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_revue) FROM revue");
		resId.last();
		int id = resId.getInt(1);
		revue.setId_revue(id);
		
		Revue revueUpdate = new Revue(id, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().update(revueUpdate);
		
		ResultSet res = connect.createStatement().executeQuery("SELECT titre, description, tarif_numero, visuel, id_periodicite FROM revue");
		res.last();
		String titre = res.getString(1);
		String description = res.getString(2);
		double tarif_numero = res.getDouble(3);
		String visuel = res.getString(4);
		int id_periodicite = res.getInt(5);

		Revue revueCheck = new Revue(id, titre, description, tarif_numero, visuel, id_periodicite);
		assertEquals(revueUpdate.hashCode(), revueCheck.hashCode());
	}
	
	@Test
	public void testUpdateOk() throws SQLException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_revue) FROM revue");
		resId.last();
		int id = resId.getInt(1);
		revue.setId_revue(id);
		
		Revue revueUpdate = new Revue(id, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		
		boolean update = MySQLRevueDAO.getInstance().update(revueUpdate);
		
		assertTrue(update);
	}
	
	/*
	 * DELETE
	 */
	
	@Test
	public void testDeleteOk() throws SQLException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_revue) FROM revue");
		resId.last();
		int id = resId.getInt(1);
		revue.setId_revue(id);
		
		boolean delete = MySQLRevueDAO.getInstance().delete(revue);
		
		assertTrue(delete);
	}
	
	@Test
	public void testDeleteNotExist() throws SQLException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_revue) FROM revue");
		resId.last();
		int id = resId.getInt(1);
		revue.setId_revue(id);
		
		MySQLRevueDAO.getInstance().delete(revue);
		Revue revueCheck = MySQLRevueDAO.getInstance().getById(revue.getId_revue());
		assertNull(revueCheck);
	}
	
	@Test
	public void testGetAllOk() throws SQLException {
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		Revue revue2 = new Revue(0, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue2);
		
		ArrayList<Revue> revues = MySQLRevueDAO.getInstance().getAll();
		assertFalse(revues.isEmpty());
	}
	
	@Test
	public void testGetAllIsSame() throws SQLException {
		List<Revue> data = new ArrayList<Revue>();
		
		Revue revue = new Revue(0, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue);
		
		Revue revue2 = new Revue(0, "01Net", "Journal du soir", 1.5, "lemonde.jpg", 1);
		MySQLRevueDAO.getInstance().create(revue2);
		
		// Récupération de l'ID
		ResultSet resId = connect.createStatement().executeQuery("SELECT MAX(id_revue) FROM revue");
		resId.last();
		int id = resId.getInt(1);
		revue.setId_revue(id-1);
		revue2.setId_revue(id);
		data.add(revue);
		data.add(revue2);
		
		ArrayList<Revue> revues = MySQLRevueDAO.getInstance().getAll();
		assertTrue(revues.containsAll(data));
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
