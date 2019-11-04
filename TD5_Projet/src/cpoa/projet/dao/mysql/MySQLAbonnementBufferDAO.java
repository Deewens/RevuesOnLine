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

import cpoa.projet.dao.AbonnementBufferDAO;
import cpoa.projet.pojo.Abonnement;
import cpoa.projet.pojo.AbonnementBuffer;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Revue;

public class MySQLAbonnementBufferDAO implements AbonnementBufferDAO {
	private static MySQLAbonnementBufferDAO instance;

	public static MySQLAbonnementBufferDAO getInstance() {
		if(instance == null) {
			instance = new MySQLAbonnementBufferDAO();
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
	public ArrayList<AbonnementBuffer> getAboWithNameAndTitle() throws SQLException {
		ArrayList<AbonnementBuffer> aboBufferList = new ArrayList<>();
		
		PreparedStatement query = this.connect().prepareStatement("SELECT abonnement.id_client, abonnement.id_revue, nom, prenom, no_rue, voie, code_postal, ville, pays, titre, description, tarif_numero, visuel, id_periodicite, date_debut, date_fin\r\n" + 
				"FROM abonnement\r\n" + 
				"INNER JOIN client ON client.id_client=abonnement.id_client\r\n" + 
				"INNER JOIN revue ON revue.id_revue=abonnement.id_revue");
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Abonnement abo = new Abonnement(res.getInt("id_client"), res.getInt("id_revue"), res.getDate("date_debut").toLocalDate(), res.getDate("date_fin").toLocalDate());
			Client client = new Client(res.getInt("id_client"), res.getString("nom"), res.getString("prenom"), res.getString("no_rue"), res.getString("voie"), res.getString("code_postal"), res.getString("ville"), res.getString("pays"));
			Revue revue = new Revue(res.getInt("id_revue"), res.getString("titre"), res.getString("description"), res.getDouble("tarif_numero"), res.getString("visuel"), res.getInt("id_periodicite"));
			
			
			AbonnementBuffer aboBuffer = new AbonnementBuffer(abo, client, revue, res.getDate("date_debut").toLocalDate(), res.getDate("date_fin").toLocalDate());
			aboBufferList.add(aboBuffer);
		}
		
		return aboBufferList;
	}
}
