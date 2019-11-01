package cpoa.projet.dao;

import java.util.List;

import cpoa.projet.pojo.Revue;

public interface RevueDAO extends DAO<Revue> {
	
	public abstract Revue getById(int id) throws Exception;
	public abstract List<Revue> getByTitre(String titre) throws Exception;
	public abstract List<Revue> getLessThanTarif_numero(double tarif) throws Exception;
}
