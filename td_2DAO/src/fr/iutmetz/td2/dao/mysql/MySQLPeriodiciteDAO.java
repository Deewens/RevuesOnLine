package fr.iutmetz.td2.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.iutmetz.td2.dao.PeriodiciteDAO;
import fr.iutmetz.td2.exceptions.NonExistentDataListException;
import fr.iutmetz.td2.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td2.pojo.Periodicite;

public class MySQLPeriodiciteDAO implements PeriodiciteDAO {
	private static MySQLPeriodiciteDAO instance;

	public static MySQLPeriodiciteDAO getInstance() {
		if(instance == null) {
			instance = new MySQLPeriodiciteDAO();
		}
		return instance;
	}
	
	private Connection connect() {
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

		return connect;
	}
	
	@Override
	public boolean create(Periodicite obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("INSERT INTO periodicite(id_periodicite, libelle) VALUES(?, ?)");
		query.setInt(1, obj.getId());
		query.setString(2, obj.getLibelle());
		
		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean delete(Periodicite obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("DELETE FROM periodicite WHERE id_periodicite = ?");
		query.setInt(1, obj.getId());

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean update(Periodicite obj, String[] params) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("UPDATE periodicite SET libelle = ? WHERE id_periodicite = ?");
		query.setString(1, obj.getLibelle());
		query.setInt(2, obj.getId());
		
		int rows = query.executeUpdate();
		
		return rows==1;
	}

	@Override
	public ArrayList<Periodicite> getAll() throws SQLException {
		List<Periodicite> periodList = new ArrayList<>();
		
		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM periodicite");
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Periodicite period = new Periodicite();
			
			period.setId(res.getInt(1));
			period.setLibelle(res.getString(2));

			periodList.add(period);
		}
		
		return (ArrayList<Periodicite>) periodList;
	}

	@Override
	public Periodicite getById(int id) throws SQLException {
		if(id < 0) {
			throw new IllegalArgumentException("id must be > 0");
		}
		
		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM periodicite WHERE id_periodicite = ?");
		query.setInt(1, id);
		
		ResultSet res = query.executeQuery();
		if(res.first()) {
			Periodicite period = new Periodicite();
			
			period.setId(res.getInt(1));
			period.setLibelle(res.getString(2));
			
			return period;
		}
		return null;
	}

}
