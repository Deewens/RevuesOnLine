package cpoa.projet.dao;

import cpoa.projet.pojo.Revue;

public interface RevueDAO extends DAO<Revue> {
	
	public abstract Revue getById(int id) throws Exception;
}
