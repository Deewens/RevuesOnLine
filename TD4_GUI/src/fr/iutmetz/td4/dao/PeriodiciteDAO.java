package fr.iutmetz.td4.dao;

import fr.iutmetz.td4.dao.DAO;
import fr.iutmetz.td4.pojo.Periodicite;

public interface PeriodiciteDAO extends DAO<Periodicite> {
	
	public abstract Periodicite getById(int id) throws Exception;

}
