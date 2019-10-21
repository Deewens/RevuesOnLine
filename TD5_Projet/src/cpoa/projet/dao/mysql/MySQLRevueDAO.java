package cpoa.projet.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cpoa.projet.dao.RevueDAO;
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

}
