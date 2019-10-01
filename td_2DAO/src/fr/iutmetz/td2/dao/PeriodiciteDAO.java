package fr.iutmetz.td2.dao;

import fr.iutmetz.td2.pojo.Periodicite;

public interface PeriodiciteDAO extends DAO<Periodicite> {
	
	public abstract Periodicite getById(int id) throws Exception;

}
