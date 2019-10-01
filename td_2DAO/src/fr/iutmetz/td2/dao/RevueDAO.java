package fr.iutmetz.td2.dao;

import fr.iutmetz.td2.pojo.Revue;

public interface RevueDAO extends DAO<Revue> {
	public abstract Revue getById(int id) throws Exception;
}
