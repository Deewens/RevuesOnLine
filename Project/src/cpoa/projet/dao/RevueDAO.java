package cpoa.projet.dao;

import java.util.ArrayList;

import cpoa.projet.pojo.Revue;

public interface RevueDAO extends DAO<Revue> {
	
	public Revue getById(int id) throws Exception;
	public ArrayList<Revue> getByIdPeriodicite(int id) throws Exception;
	public ArrayList<Revue> getByTitre(String titre) throws Exception;
	public ArrayList<Revue> getLessThanTarif_numero(double tarif) throws Exception;
}
