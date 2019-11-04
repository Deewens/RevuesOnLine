package cpoa.projet.dao;

import cpoa.projet.pojo.Periodicite;

public interface PeriodiciteDAO extends DAO<Periodicite> {
	
	public Periodicite getById(int id) throws Exception;

}
