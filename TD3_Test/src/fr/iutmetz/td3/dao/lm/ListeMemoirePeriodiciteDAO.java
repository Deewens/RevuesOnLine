package fr.iutmetz.td3.dao.lm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.iutmetz.td3.dao.PeriodiciteDAO;
import fr.iutmetz.td3.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td3.pojo.Periodicite;

public class ListeMemoirePeriodiciteDAO implements PeriodiciteDAO {
	private static int id = 2;
	private static ListeMemoirePeriodiciteDAO instance;
	
	private List<Periodicite> data;
	
	public ListeMemoirePeriodiciteDAO() {
		this.data = new ArrayList<Periodicite>();
		
		this.data.add(new Periodicite(1, "Journalier"));
		this.data.add(new Periodicite(2, "Mensuel"));
	}

	public static ListeMemoirePeriodiciteDAO getInstance() {
		if(instance == null) {
			instance = new ListeMemoirePeriodiciteDAO();
		}
		return instance;
	}

	@Override
	public boolean create(Periodicite obj) {
		obj.setId(++id);
		
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
	public boolean update(Periodicite obj) throws NonExistentDataObjectException {
		ListIterator<Periodicite> dataListIterator = data.listIterator();
		
		while(dataListIterator.hasNext()) {
			Periodicite period = dataListIterator.next();
			if(obj.getId() == period.getId()) {
				dataListIterator.set(obj);
				return true;
			}
		}
		throw new NonExistentDataObjectException("Cl� primaire inexistante.");
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
