package cpoa.projet.dao.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cpoa.projet.dao.PeriodiciteDAO;
import cpoa.projet.pojo.Periodicite;



public class MySQLPeriodiciteDAO implements PeriodiciteDAO {
	private static MySQLPeriodiciteDAO instance;

	public static MySQLPeriodiciteDAO getInstance() {
		if(instance == null) {
			instance = new MySQLPeriodiciteDAO();
		}
		return instance;
	}
	
	private Connection connect() {
		Properties dbAccess = new Properties();
		File fDb = new File("config/db.properties");
		try {
			FileInputStream source = new FileInputStream(fDb);
			dbAccess.loadFromXML(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://" + dbAccess.getProperty("ip") + dbAccess.getProperty("port") + "/" + dbAccess.getProperty("db");
		String login = dbAccess.getProperty("login");
		String pwd = dbAccess.getProperty("pwd");

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
		PreparedStatement query = this.connect().prepareStatement("INSERT INTO periodicite(libelle) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
		query.setString(1, obj.getLibelle());
		
		int rows = query.executeUpdate();
		ResultSet rs = query.getGeneratedKeys();
		if(rs.next()){
			obj.setId(rs.getInt(1));
		}

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
	public boolean update(Periodicite obj) throws SQLException {
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
			Periodicite period = new Periodicite(res.getInt(1), res.getString(2));

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
			Periodicite period = new Periodicite(res.getInt(1), res.getString(2));
			
			return period;
		}
		return null;
	}

}
