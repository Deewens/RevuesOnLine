package fr.iutmetz.td2;

import java.util.List;

public interface DAO<T> {
	public boolean create(T obj) throws Exception;
		
	public boolean delete(T obj) throws Exception;
		
	public boolean update(T obj) throws Exception;
	
	public List<T> getAll() throws Exception;
		
	public T getById(int id) throws Exception;
}
