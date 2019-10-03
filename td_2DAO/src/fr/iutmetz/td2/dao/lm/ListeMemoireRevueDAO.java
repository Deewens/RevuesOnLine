package fr.iutmetz.td2.dao.lm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.iutmetz.td2.dao.RevueDAO;
import fr.iutmetz.td2.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td2.pojo.Revue;

public class ListeMemoireRevueDAO implements RevueDAO {
	private static int id = 2;
	private static ListeMemoireRevueDAO instance;
	private List<Revue> data;
	
	public static ListeMemoireRevueDAO getInstance() {
		if(instance == null) {
			instance = new ListeMemoireRevueDAO();
		}
		return instance;
	}
	
	public ListeMemoireRevueDAO() {
		this.data = new ArrayList<Revue>();
		
		this.data.add(new Revue(1, "Le monde", "Journal du soir", 1.5, "lemonde.jpg", 1));
		this.data.add(new Revue(2, "Charlie Hebdo", "Journal satirisque", 2, "charliehebdo.jpg", 2));
	}
	
	@Override
	public boolean create(Revue obj) {
		obj.setId_revue(++id);
		
		boolean create = this.data.add(obj);
		return create;
	}

	@Override
	public boolean delete(Revue obj) throws NonExistentDataObjectException {
		Revue delete;
		int idx = this.data.indexOf(obj);
		
		if(idx == -1) {
			throw new NonExistentDataObjectException("Tentative de suppresion d'un objet inexistant.");
		}
		else {
			delete = this.data.remove(idx);
		}
		
		return obj.equals(delete);
	}

	@Override
	public boolean update(Revue obj) throws NonExistentDataObjectException {
		ListIterator<Revue> dataListIterator = data.listIterator();
		
		while(dataListIterator.hasNext()) {
			Revue revue = dataListIterator.next();
			if(obj.getId_revue() == revue.getId_revue()) {
				dataListIterator.set(obj);
				return true;
			}
		}
		throw new NonExistentDataObjectException("Clé primaire inexistante.");
	}

	@Override
	public ArrayList<Revue> getAll() {
		return (ArrayList<Revue>) data;
	}

	@Override
	public Revue getById(int id) {
		Iterator<Revue> dataIterator = this.data.iterator();
		
		if(id < 0) {
			throw new IllegalArgumentException("id must be > 0");
		}
		
		while (dataIterator.hasNext()) {
			Revue datum = dataIterator.next();
			if(datum.getId_revue() == id) {
				return datum;
			}
		}
		return null;
	}

}
