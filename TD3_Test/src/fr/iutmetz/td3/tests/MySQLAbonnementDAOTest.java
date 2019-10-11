package fr.iutmetz.td3.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.iutmetz.td3.dao.lm.ListeMemoireAbonnementDAO;
import fr.iutmetz.td3.dao.mysql.MySQLAbonnementDAO;
import fr.iutmetz.td3.exceptions.ExistingCompositeKeyException;
import fr.iutmetz.td3.pojo.Abonnement;

public class MySQLAbonnementDAOTest {
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
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

		Connection connect = null;

		try {
			connect = DriverManager.getConnection(url, login, pwd);
		} catch(SQLException sqle) {
			System.out.println("Erreur de connexion : " + sqle.getMessage());
		}
		
		 // Use TRUNCATE
	    String sql = "TRUNCATE abonnement";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM abonnement";
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
	public void testCreateIsSame() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		MySQLAbonnementDAO.getInstance().create(abo);
		Abonnement aboDB = MySQLAbonnementDAO.getInstance().getByIds(abo.getId_client(), abo.getId_revue());
		assertEquals(abo.hashCode(), aboDB.hashCode());
	}
	
	@Test
	public void testCreateOk() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		boolean create = MySQLAbonnementDAO.getInstance().create(abo);
		assertTrue(create);
	}
	
	@Test
	public void testCreatePrimaryKeyExistException() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		Abonnement aboDB = MySQLAbonnementDAO.getInstance().getByIds(abo.getId_client(), abo.getId_revue());
		if(aboDB == null) MySQLAbonnementDAO.getInstance().create(abo);
		
		try {
			MySQLAbonnementDAO.getInstance().create(abo);
			fail("Exception non lancé !");
		} catch(SQLException e) {
			// Nothing
		}
	}
	
	/*
	 * UPDATE
	 */
	
	@Test
	public void testUpdateIsSame() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		MySQLAbonnementDAO.getInstance().create(abo);
		
		dateFin = LocalDate.parse("31/12/2000", this.format);
		Abonnement aboUpdate = new Abonnement(1, 2, dateDebut, dateFin);
		MySQLAbonnementDAO.getInstance().update(aboUpdate);
		Abonnement aboCheck = MySQLAbonnementDAO.getInstance().getByIds(abo.getId_client(), abo.getId_revue());
		assertEquals(aboUpdate.hashCode(), aboCheck.hashCode());
	}
	
	@Test
	public void testUpdateOk() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		MySQLAbonnementDAO.getInstance().create(abo);
		
		dateFin = LocalDate.parse("31/12/2000", this.format);
		Abonnement aboUpdate = new Abonnement(1, 2, dateDebut, dateFin);
		boolean update = MySQLAbonnementDAO.getInstance().update(aboUpdate);
		
		assertTrue(update);
	}
	
	/*
	 * DELETE
	 */
	
	@Test
	public void testDeleteOk() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		MySQLAbonnementDAO.getInstance().create(abo);
		
		boolean delete = MySQLAbonnementDAO.getInstance().delete(abo);
		
		assertTrue(delete);
	}
	
	@Test
	public void testDeleteNotExist() throws SQLException {
		LocalDate dateDebut = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", this.format);	
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		MySQLAbonnementDAO.getInstance().create(abo);

		MySQLAbonnementDAO.getInstance().delete(abo);
		Abonnement aboCheck = MySQLAbonnementDAO.getInstance().getByIds(abo.getId_client(), abo.getId_revue());
		assertNull(aboCheck);
	}
	
	@Test
	public void testGetAllOk() throws SQLException {
		LocalDate dateDebut1 = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin1 = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut1, dateFin1);
		MySQLAbonnementDAO.getInstance().create(abo);
		
		LocalDate dateDebut2 = LocalDate.parse("01/01/1990", this.format);
		LocalDate dateFin2 = LocalDate.parse("31/12/1990", this.format);
		Abonnement abo2 = new Abonnement(2, 3, dateDebut2, dateFin2);
		MySQLAbonnementDAO.getInstance().create(abo2);
		
		ArrayList<Abonnement> abonnements = MySQLAbonnementDAO.getInstance().getAll();
		assertFalse(abonnements.isEmpty());
	}
	
	@Test
	public void testGetAllIsSame() throws SQLException {
		List<Abonnement> data = new ArrayList<Abonnement>();
		
		LocalDate dateDebut1 = LocalDate.parse("01/01/1900", this.format);
		LocalDate dateFin1 = LocalDate.parse("31/12/1900", this.format);
		Abonnement abo = new Abonnement(1, 2, dateDebut1, dateFin1);
		MySQLAbonnementDAO.getInstance().create(abo);
		
		LocalDate dateDebut2 = LocalDate.parse("01/01/1990", this.format);
		LocalDate dateFin2 = LocalDate.parse("31/12/1990", this.format);
		Abonnement abo2 = new Abonnement(2, 3, dateDebut2, dateFin2);
		MySQLAbonnementDAO.getInstance().create(abo2);
		
		data.add(abo);
		data.add(abo2);
		
		ArrayList<Abonnement> abonnements = MySQLAbonnementDAO.getInstance().getAll();
		assertTrue(abonnements.containsAll(data));
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
	    String sql = "TRUNCATE abonnement";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Use DELETE
	    sql = "DELETE FROM abonnement";
	    // Execute deletion
	    try {
			connect.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
