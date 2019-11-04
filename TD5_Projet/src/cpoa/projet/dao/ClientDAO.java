package cpoa.projet.dao;


import java.util.ArrayList;

import cpoa.projet.pojo.Client;

public interface ClientDAO extends DAO<Client> {
	
	public Client getById(int id) throws Exception;
	public ArrayList<Client> getByNom(String nom) throws Exception;
	public ArrayList<Client> getByNomPrenom(String nom, String prenom) throws Exception;
}
