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

import cpoa.projet.dao.ClientDAO;
import cpoa.projet.pojo.Client;

public class MySQLClientDAO implements ClientDAO {
	private static MySQLClientDAO instance;

	public static MySQLClientDAO getInstance() {
		if(instance == null) {
			instance = new MySQLClientDAO();
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
	public boolean create(Client obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("INSERT INTO client(nom, prenom, no_rue, voie, code_postal, ville, pays) VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		query.setString(1, obj.getNom());
		query.setString(2, obj.getPrenom());
		query.setString(3, obj.getNo_rue());
		query.setString(4, obj.getVoie());
		query.setString(5, obj.getCode_postal());
		query.setString(6, obj.getVille());
		query.setString(7, obj.getPays());
		

		int rows = query.executeUpdate();
		ResultSet rs = query.getGeneratedKeys();
		if(rs.next()){
			obj.setId_client(rs.getInt(1));
		}

		return rows==1;
	}

	@Override
	public boolean delete(Client obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("DELETE FROM client WHERE id_client = ?");
		query.setInt(1, obj.getId_client());

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean update(Client obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("UPDATE  client SET nom = ?, prenom = ?, no_rue = ?, voie = ?, code_postal = ?, ville = ?, pays = ? WHERE id_client = ?");
		query.setString(1, obj.getNom());
		query.setString(2, obj.getPrenom());
		query.setString(3, obj.getNo_rue());
		query.setString(4, obj.getVoie());
		query.setString(5, obj.getCode_postal());
		query.setString(6, obj.getVille());
		query.setString(7, obj.getPays());
		query.setInt(8, obj.getId_client());
		

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public ArrayList<Client> getAll() throws SQLException {
		List<Client> clientList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM client");
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Client client = new Client();
			
			client.setId_client(res.getInt(1));
			client.setNom(res.getString(2));
			client.setPrenom(res.getString(3));
			client.setNo_rue(res.getString(4));
			client.setVoie(res.getString(5));
			client.setCode_postal(res.getString(6));
			client.setVille(res.getString(7));
			client.setPays(res.getString(8));

			clientList.add(client);
		}
		
		return (ArrayList<Client>) clientList;
	}

	@Override
	public Client getById(int id) throws SQLException {
		if(id < 0) {
			throw new IllegalArgumentException("id must be > 0");
		}
		
		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM client WHERE id_client = ?");
		query.setInt(1, id);
		
		ResultSet res = query.executeQuery();
		if(res.first()) {
			Client client = new Client();
			
			client.setId_client(res.getInt(1));
			client.setNom(res.getString(2));
			client.setPrenom(res.getString(3));
			client.setNo_rue(res.getString(4));
			client.setVoie(res.getString(5));
			client.setCode_postal(res.getString(6));
			client.setVille(res.getString(7));
			client.setPays(res.getString(8));
			
			return client;
		}
		
		return null;
	}
	
	@Override
	public ArrayList<Client> getByNom(String nom) throws SQLException {
		ArrayList<Client> clientList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM client WHERE nom = ?");
		query.setString(1, nom);
		
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Client client = new Client();
			
			client.setId_client(res.getInt(1));
			client.setNom(res.getString(2));
			client.setPrenom(res.getString(3));
			client.setNo_rue(res.getString(4));
			client.setVoie(res.getString(5));
			client.setCode_postal(res.getString(6));
			client.setVille(res.getString(7));
			client.setPays(res.getString(8));

			clientList.add(client);
		}
		
		return clientList;
	}

	@Override
	public ArrayList<Client> getByNomPrenom(String nom, String prenom) throws SQLException {
		ArrayList<Client> clientList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM client WHERE nom = ? AND prenom = ?");
		query.setString(1, nom);
		query.setString(2, prenom);
		
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Client client = new Client();
			
			client.setId_client(res.getInt(1));
			client.setNom(res.getString(2));
			client.setPrenom(res.getString(3));
			client.setNo_rue(res.getString(4));
			client.setVoie(res.getString(5));
			client.setCode_postal(res.getString(6));
			client.setVille(res.getString(7));
			client.setPays(res.getString(8));

			clientList.add(client);
		}
		
		return clientList;
	}

}
