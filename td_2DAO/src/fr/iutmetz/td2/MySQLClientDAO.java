package fr.iutmetz.td2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLClientDAO implements ClientDAO {
	private static MySQLClientDAO instance;

	public static MySQLClientDAO getInstance() {
		if(instance == null) {
			instance = new MySQLClientDAO();
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
	public boolean create(Client obj) throws Exception {
		PreparedStatement query = this.connect().prepareStatement("INSERT INTO client(id_client, nom, prenom, no_rue, voie, code_postal, ville, pays) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
		query.setInt(1, obj.getId_client());
		query.setString(2, obj.getNom());
		query.setString(3, obj.getPrenom());
		query.setString(4, obj.getNo_rue());
		query.setString(5, obj.getVoie());
		query.setString(6, obj.getCode_postal());
		query.setString(7, obj.getVille());
		query.setString(8, obj.getPays());
		

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean delete(Client obj) throws Exception {
		PreparedStatement query = this.connect().prepareStatement("DELETE FROM client WHERE id_client = ?");
		query.setInt(1, obj.getId_client());

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean update(Client obj) throws Exception {
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
	public List<Client> getAll() throws Exception {
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


		return clientList;
	}

	@Override
	public Client getById(int id) throws SQLException {
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
		else {
			throw new IllegalArgumentException("le client que vous recherchez n'existe pas");
		}
	}

	@Override
	public List<Client> getByNomPrenom(String nom, String prenom) throws SQLException {
		List<Client> clientList = new ArrayList<>();

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
