package fr.iutmetz.td4.dao;

import java.util.ArrayList;
import java.util.List;

public interface DAO<T> {
	
	public boolean create(T obj) throws Exception;
		
	public boolean delete(T obj) throws Exception;
		
	public boolean update(T obj) throws Exception;
	
	public ArrayList<T> getAll() throws Exception;
}
