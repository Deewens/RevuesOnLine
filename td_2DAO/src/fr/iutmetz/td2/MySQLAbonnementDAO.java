package fr.iutmetz.td2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.time.*;
import java.sql.Date;

public class MySQLAbonnementDAO implements AbonnementDAO {
	private static MySQLAbonnementDAO instance;
	
	private MySQLAbonnementDAO() {}
	
	public static MySQLAbonnementDAO getInstance() {
		if(instance == null) {
			instance = new MySQLAbonnementDAO();
		}
		return instance;
	}
	
	public static Connection connect() {
		/* Version IUT */
		 String url = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/fauvet5u_java";
		 url += "?serverTimezone=Europe/Paris";
		 String login = "fauvet5u_appli";
		 String pwd = "31806272";
		
		/* String url = "jdbc:mysql://localhost/java_td1";
		String login = "root";
		String pwd = ""; */
		
		Connection connect = null;
		
		try {
			connect = DriverManager.getConnection(url, login, pwd);
		} catch(SQLException sqle) {
			System.out.println("Erreur de connexion : " + sqle.getMessage());
		}
		
		return connect;
}
	
	@Override
	public boolean create(Abonnement obj) throws SQLException {
		PreparedStatement query = MySQLAbonnementDAO.connect().prepareStatement("INSERT INTO Abonnement(id_client, id_revue, date_debut, date_fin) VALUES(?, ?, ?, ?)");
		query.setInt(1, obj.getId_client());
		query.setInt(2, obj.getId_revue());
		
		query.setDate(3, Date.valueOf(obj.getDate_debut()));
		query.setDate(4, Date.valueOf(obj.getDate_fin()));
		
		int rows = query.executeUpdate();
		
		return rows==1;
	}

	@Override
	public boolean delete(Abonnement obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Abonnement obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Abonnement> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Abonnement getById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Abonnement> getByDate_debut(LocalDate date_debut) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Abonnement> getByDate_fin(LocalDate date_fin) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
