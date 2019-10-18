package fr.iutmetz.td4.dao;

import fr.iutmetz.td4.pojo.Revue;

public interface RevueDAO extends DAO<Revue> {
	
	public abstract Revue getById(int id) throws Exception;
}
