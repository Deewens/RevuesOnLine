package fr.iutmetz.td3.dao;

import java.sql.SQLException;
import java.util.List;

import fr.iutmetz.td3.pojo.Client;

public interface ClientDAO extends DAO<Client> {
	
	public Client getById(int id) throws Exception;
	public List<Client> getByNomPrenom(String nom, String prenom) throws Exception;
}
