package fr.iutmetz.td2;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO extends DAO<Client> {
	public Client getById(int id) throws SQLException;
	public List<Client> getByNomPrenom(String nom, String prenom) throws SQLException;
}
