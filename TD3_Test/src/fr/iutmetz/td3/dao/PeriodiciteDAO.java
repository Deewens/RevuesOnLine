package fr.iutmetz.td3.dao;

import fr.iutmetz.td3.dao.DAO;
import fr.iutmetz.td3.pojo.Periodicite;

public interface PeriodiciteDAO extends DAO<Periodicite> {
	
	public abstract Periodicite getById(int id) throws Exception;

}
