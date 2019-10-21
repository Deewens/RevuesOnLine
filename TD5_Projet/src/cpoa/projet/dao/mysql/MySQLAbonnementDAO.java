package cpoa.projet.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cpoa.projet.dao.AbonnementDAO;
import cpoa.projet.pojo.Abonnement;

public class MySQLAbonnementDAO implements AbonnementDAO {
	private static MySQLAbonnementDAO instance;

	private MySQLAbonnementDAO() {}

	public static MySQLAbonnementDAO getInstance() {
		if(instance == null) {
			instance = new MySQLAbonnementDAO();
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
	public boolean create(Abonnement obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("INSERT INTO Abonnement(id_client, id_revue, date_debut, date_fin) VALUES(?, ?, ?, ?)");
		query.setInt(1, obj.getId_client());
		query.setInt(2, obj.getId_revue());

		query.setDate(3, Date.valueOf(obj.getDate_debut()));
		query.setDate(4, Date.valueOf(obj.getDate_fin()));

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean delete(Abonnement obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("DELETE FROM abonnement WHERE id_client = ? AND id_revue = ?");
		query.setInt(1, obj.getId_client());
		query.setInt(2, obj.getId_revue());

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public boolean update(Abonnement obj) throws SQLException {
		PreparedStatement query = this.connect().prepareStatement("UPDATE abonnement SET date_debut = ?, date_fin = ? WHERE id_client = ? AND id_revue = ?");
		query.setDate(1, Date.valueOf(obj.getDate_debut()));
		query.setDate(2, Date.valueOf(obj.getDate_fin()));
		query.setInt(3, obj.getId_client());
		query.setInt(4, obj.getId_revue());

		int rows = query.executeUpdate();

		return rows==1;
	}

	@Override
	public ArrayList<Abonnement> getAll() throws SQLException {
		List<Abonnement> aboList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM abonnement");
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Abonnement abo = new Abonnement();

			abo.setId_client(res.getInt(1));
			abo.setId_revue(res.getInt(2));
			abo.setDate_debut(res.getDate(3).toLocalDate());
			abo.setDate_fin(res.getDate(4).toLocalDate());

			aboList.add(abo);
		}

		return (ArrayList<Abonnement>) aboList;
	}

	@Override
	public Abonnement getByIds(int idClient, int idRevue) throws SQLException {
		if(idClient < 0) {
			throw new IllegalArgumentException("idClient must be > 0"); 
		}
		if(idRevue < 0) {
			throw new IllegalArgumentException("idRevue must be > 0");
		}
		
		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM abonnement WHERE id_client = ? AND id_revue = ?");
		query.setInt(1, idClient);
		query.setInt(2, idRevue);
		
		ResultSet res = query.executeQuery();
		if(res.first()) {
			Abonnement abo = new Abonnement();
			
			abo.setId_client(res.getInt(1));
			abo.setId_revue(res.getInt(2));
			abo.setDate_debut(res.getDate(3).toLocalDate());
			abo.setDate_fin(res.getDate(4).toLocalDate());
			
			return abo;
		}
		
		return null;
	}

	@Override
	public List<Abonnement> getByDate_debut(LocalDate date_debut) throws SQLException {
		List<Abonnement> aboList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM abonnement WHERE date_debut = ?");
		query.setDate(1, Date.valueOf(date_debut));
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Abonnement abo = new Abonnement();

			abo.setId_client(res.getInt(1));
			abo.setId_revue(res.getInt(2));
			abo.setDate_debut(res.getDate(3).toLocalDate());
			abo.setDate_fin(res.getDate(4).toLocalDate());

			aboList.add(abo);
		}
		
		return aboList;
	}

	@Override
	public List<Abonnement> getByDate_fin(LocalDate date_fin) throws SQLException {
		List<Abonnement> aboList = new ArrayList<>();

		PreparedStatement query = this.connect().prepareStatement("SELECT * FROM abonnement WHERE date_fin = ?");
		query.setDate(1, Date.valueOf(date_fin));
		ResultSet res = query.executeQuery();

		while(res.next()) {
			Abonnement abo = new Abonnement();

			abo.setId_client(res.getInt(1));
			abo.setId_revue(res.getInt(2));
			abo.setDate_debut(res.getDate(3).toLocalDate());
			abo.setDate_fin(res.getDate(4).toLocalDate());

			aboList.add(abo);
		}
		
		return aboList;
	}

}
