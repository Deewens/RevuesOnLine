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

import cpoa.projet.dao.RevueDAO;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Revue;


public class MySQLRevueDAO implements RevueDAO {
	private static MySQLRevueDAO instance;

	public static MySQLRevueDAO getInstance() {
		if(instance == null) {
			instance = new MySQLRevueDAO();
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
	public boolean create(Revue obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("INSERT INTO Revue(titre, description, tarif_numero, visuel, id_periodicite) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		query.setString(1, obj.getTitre());
		query.setString(2, obj.getDescription());
		query.setDouble(3, obj.getTarif_numero());
		query.setString(4, obj.getVisuel());
		query.setInt(5, obj.getId_periodicite());
		
		int rows = query.executeUpdate();
		ResultSet rs = query.getGeneratedKeys();
		if(rs.next()){
			obj.setId_revue(rs.getInt(1));
		}
		
		return rows==1;
	}

	@Override
	public boolean delete(Revue obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("DELETE FROM Revue WHERE id_revue = ?");
		query.setInt(1, obj.getId_revue());

		int rows = query.executeUpdate();
		return rows==1;
	}

	@Override
	public boolean update(Revue obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("UPDATE Revue SET titre = ?, description = ?, tarif_numero = ?, visuel = ?, id_periodicite = ? WHERE id_revue = ?");
		query.setString(1, obj.getTitre());
		query.setString(2, obj.getDescription());
		query.setDouble(3, obj.getTarif_numero());
		query.setString(4, obj.getVisuel());
		query.setInt(5, obj.getId_periodicite());
		query.setInt(6, obj.getId_revue());

		int rows = query.executeUpdate();
		return rows==1;
	}

	@Override
	public ArrayList<Revue> getAll() throws SQLException {
		List<Revue> revueList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM revue");
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue();

			revue.setId_revue(res.getInt(1));
			revue.setTitre(res.getString(2));
			revue.setDescription(res.getString(3));
			revue.setTarif_numero(res.getDouble(4));
			revue.setVisuel(res.getString(5));
			revue.setId_periodicite(res.getInt(6));

			revueList.add(revue);
		}
		
		return (ArrayList<Revue>) revueList;
	}

	@Override
	public Revue getById(int id) throws SQLException {
		if(id < 0) {
			throw new IllegalArgumentException("id must be > 0");
		}
		
		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM revue WHERE id_revue = ?");
		query.setInt(1, id);
		
		ResultSet res = query.executeQuery();
		if(res.first()) {
			Revue revue = new Revue();
			
			revue.setId_revue(res.getInt(1));
			revue.setTitre(res.getString(2));
			revue.setDescription(res.getString(3));
			revue.setTarif_numero(res.getDouble(4));
			revue.setVisuel(res.getString(5));
			revue.setId_periodicite(res.getInt(6));
			
			return revue;
		}
		
		return null;
	}
	
	@Override
	public ArrayList<Revue> getByTitre(String titre) throws SQLException {
		ArrayList<Revue> revueList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM revue WHERE titre = ?");
		query.setString(1, titre);
		
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue();
			
			revue.setId_revue(res.getInt(1));
			revue.setTitre(res.getString(2));
			revue.setDescription(res.getString(3));
			revue.setTarif_numero(res.getDouble(4));
			revue.setVisuel(res.getString(5));
			revue.setId_periodicite(res.getInt(6));

			revueList.add(revue);
		}
		
		return revueList;
	}
	
	@Override
	public ArrayList<Revue> getLessThanTarif_numero(double tarif) throws SQLException {
		ArrayList<Revue> revueList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM revue WHERE tarif_numero >= ?");
		query.setDouble(1, tarif);
		
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue();
			
			revue.setId_revue(res.getInt(1));
			revue.setTitre(res.getString(2));
			revue.setDescription(res.getString(3));
			revue.setTarif_numero(res.getDouble(4));
			revue.setVisuel(res.getString(5));
			revue.setId_periodicite(res.getInt(6));

			revueList.add(revue);
		}
		
		return revueList;
	}

	@Override
	public ArrayList<Revue> getByIdPeriodicite(int id) throws Exception {
		ArrayList<Revue> revueList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM revue WHERE id_periodicite = ?");
		query.setInt(1, id);
		
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue();
			
			revue.setId_revue(res.getInt(1));
			revue.setTitre(res.getString(2));
			revue.setDescription(res.getString(3));
			revue.setTarif_numero(res.getDouble(4));
			revue.setVisuel(res.getString(5));
			revue.setId_periodicite(res.getInt(6));

			revueList.add(revue);
		}
		
		return revueList;
	}
}
