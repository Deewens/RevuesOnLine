package fr.iutmetz.td2.dao.lm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.iutmetz.td2.dao.ClientDAO;
import fr.iutmetz.td2.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td2.pojo.Client;

public class ListeMemoireClientDAO implements ClientDAO {
	private static int id = 2;
	private static ListeMemoireClientDAO instance;
	private List<Client> data;
	
	public ListeMemoireClientDAO() {
		this.data = new ArrayList<Client>();
		
		this.data.add(new Client(1, "Dudon", "Adrien", "37", "rue des cyprets", "57565", "Congo", "Afrique"));
		this.data.add(new Client(2, "Adrien", "Dudon", "37", "rue des cyprets", "57565", "Congo", "Afrique"));
	}
	
	public static ListeMemoireClientDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireClientDAO();
		}
		return instance;
	}
	
	@Override
	public boolean create(Client obj) {		
		obj.setId_client(++id);
		
		boolean create = this.data.add(obj);
		return create;
	}

	@Override
	public boolean delete(Client obj) throws NonExistentDataObjectException {
		Client delete;
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
	public boolean update(Client obj) throws NonExistentDataObjectException {
		ListIterator<Client> dataListIterator = data.listIterator();
		
		while(dataListIterator.hasNext()) {
			Client client = dataListIterator.next();
			if(obj.getId_client() == client.getId_client()) {
				dataListIterator.set(obj);
				return true;
			}
		}
		throw new NonExistentDataObjectException("Clé primaire inexistante.");
	}

	@Override
	public ArrayList<Client> getAll() {
		return (ArrayList<Client>) data;
	}

	@Override
	public Client getById(int id) {
		Iterator<Client> dataIterator = this.data.iterator();
		
		if(id < 0) {
			throw new IllegalArgumentException("id must be > 0");
		}
		
		while (dataIterator.hasNext()) {
			Client datum = dataIterator.next();
			if(datum.getId_client() == id) {
				return datum;
			}
		}
		
		return null;
	}

	@Override
	public List<Client> getByNomPrenom(String nom, String prenom) {
		Iterator<Client> dataIterator = this.data.iterator();
		List<Client> result = new ArrayList<Client>();
		
		while(dataIterator.hasNext()) {
			Client datum = dataIterator.next();
			if(datum.getNom().equals(nom) && datum.getPrenom().equals(prenom)) {
				result.add(datum);
			}
		}
		return result;
	}

}
