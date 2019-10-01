package fr.iutmetz.td2.dao.lm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.iutmetz.td2.dao.PeriodiciteDAO;
import fr.iutmetz.td2.exceptions.NonExistentDataListException;
import fr.iutmetz.td2.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td2.pojo.Abonnement;
import fr.iutmetz.td2.pojo.Client;
import fr.iutmetz.td2.pojo.Periodicite;

public class ListeMemoirePeriodiciteDAO implements PeriodiciteDAO {
	private static ListeMemoirePeriodiciteDAO instance;
	
	private List<Periodicite> data;
	
	public ListeMemoirePeriodiciteDAO() {
		this.data = new ArrayList<Periodicite>();
		
		this.data.add(new Periodicite(1, "Adrien"));
		this.data.add(new Periodicite(2, "Lol"));
	}

	public static ListeMemoirePeriodiciteDAO getInstance() {
		if(instance == null) {
			instance = new ListeMemoirePeriodiciteDAO();
		}
		return instance;
	}

	@Override
	public boolean create(Periodicite obj) {
		for(int i = 0; i<data.size()+1; i++) {
			obj.setId(obj.getId() + 1);
		}
		
		boolean create = this.data.add(obj);
		return create;
	}

	@Override
	public boolean delete(Periodicite obj) throws NonExistentDataObjectException {
		Periodicite delete;
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
	public boolean update(Periodicite obj, String[] params) throws NonExistentDataObjectException {
		int idx = this.data.indexOf(obj);
		
		if(idx == -1) {
			throw new NonExistentDataObjectException("Tentative de modification d'un object inexistant.");
		}
		
		this.data.set(idx, obj);
		return true;
	}

	@Override
	public ArrayList<Periodicite> getAll() {
		return (ArrayList<Periodicite>) data;
	}

	@Override
	public Periodicite getById(int id) {
		Iterator<Periodicite> dataIterator = this.data.iterator();
		
		if(id < 0) {
			throw new IllegalArgumentException("id must be > 0");
		}
		
		while (dataIterator.hasNext()) {
			Periodicite datum = dataIterator.next();
			if(datum.getId() == id) {
				return datum;
			}
		}
		
		return null;
	}

}
