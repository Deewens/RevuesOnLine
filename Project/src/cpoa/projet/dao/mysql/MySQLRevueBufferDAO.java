package cpoa.projet.dao.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cpoa.projet.dao.RevueBufferDAO;
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
import cpoa.projet.pojo.RevueBuffer;

public class MySQLRevueBufferDAO implements RevueBufferDAO {
	private static MySQLRevueBufferDAO instance;

	public static MySQLRevueBufferDAO getInstance() {
		if(instance == null) {
			instance = new MySQLRevueBufferDAO();
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
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNb() throws SQLException {
		List<RevueBuffer> revueBufferList = new ArrayList<>();
		
		PreparedStatement query = this.connect().prepareStatement("SELECT id_revue, titre, description, tarif_numero, visuel, revue.id_periodicite, libelle\r\n" + 
				"FROM revue\r\n" + 
				"INNER JOIN periodicite ON revue.id_periodicite=periodicite.id_periodicite");
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(6));
			Periodicite period = new Periodicite(res.getInt(6), res.getString(7));
			RevueBuffer revueBuffer = new RevueBuffer(revue, period, 0);

			revueBufferList.add(revueBuffer);
		}
		
		return (ArrayList<RevueBuffer>)revueBufferList;
	}

	@Override
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNbByTitre(String titre) throws SQLException {
		List<RevueBuffer> revueBufferList = new ArrayList<>();
		
		PreparedStatement query = this.connect().prepareStatement("SELECT id_revue, titre, description, tarif_numero, visuel, revue.id_periodicite, libelle\r\n" + 
				"FROM revue\r\n" + 
				"INNER JOIN periodicite ON revue.id_periodicite=periodicite.id_periodicite\r\n" +
				"WHERE titre = ?");
		
		query.setString(1, titre);
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(6));
			Periodicite period = new Periodicite(res.getInt(6), res.getString(7));
			RevueBuffer revueBuffer = new RevueBuffer(revue, period, 0);

			revueBufferList.add(revueBuffer);
		}
		
		return (ArrayList<RevueBuffer>)revueBufferList;
	}

	@Override
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNbLessThanTarif_numero(double tarif) throws SQLException {
		List<RevueBuffer> revueBufferList = new ArrayList<>();
		
		PreparedStatement query = this.connect().prepareStatement("SELECT id_revue, titre, description, tarif_numero, visuel, revue.id_periodicite, libelle\r\n" + 
				"FROM revue\r\n" + 
				"INNER JOIN periodicite ON revue.id_periodicite=periodicite.id_periodicite\r\n" +
				"WHERE tarif_numero = ?");
		
		query.setDouble(1, tarif);
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Revue revue = new Revue(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(6));
			Periodicite period = new Periodicite(res.getInt(6), res.getString(7));
			RevueBuffer revueBuffer = new RevueBuffer(revue, period, 0);

			revueBufferList.add(revueBuffer);
		}
		
		return (ArrayList<RevueBuffer>)revueBufferList;
	}
}
