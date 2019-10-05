package fr.iutmetz.td3.dao;

import fr.iutmetz.td3.pojo.Revue;

public interface RevueDAO extends DAO<Revue> {
	
	public abstract Revue getById(int id) throws Exception;
}
