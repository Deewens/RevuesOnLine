package cpoa.projet.dao;


import java.util.List;

import cpoa.projet.pojo.Client;

public interface ClientDAO extends DAO<Client> {
	
	public Client getById(int id) throws Exception;
	public List<Client> getByNomPrenom(String nom, String prenom) throws Exception;
}
