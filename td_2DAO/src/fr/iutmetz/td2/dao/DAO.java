package fr.iutmetz.td2.dao;

import java.util.ArrayList;
import java.util.List;

public interface DAO<T> {
	public boolean create(T obj) throws Exception;
		
	public boolean delete(T obj) throws Exception;
		
	public boolean update(T obj, String[] params) throws Exception;
	
	public ArrayList<T> getAll() throws Exception;
}