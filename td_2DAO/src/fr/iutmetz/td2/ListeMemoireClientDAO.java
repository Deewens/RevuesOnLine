package fr.iutmetz.td2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListeMemoireClientDAO implements ClientDAO {
	private static ListeMemoireClientDAO instance;
	private List<Client> data;
	
	public ListeMemoireClientDAO() {
		this.data = new ArrayList<Client>();
		
		/* this.data.add(new Client(1, "Adrien", "Alah", "37", "rue des cyprets", "57565", "Afrique du sud", "Afrique"));
		this.data.add(new Client(2, "Adrien", "Alah", "37", "rue des cyprets", "57565", "Afrique du sud", "Afrique")); */
	}
	
	public static ListeMemoireClientDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireClientDAO();
		}
		return instance;
	}
	
	@Override
	public boolean create(Client obj) throws SQLException {
		for(int i = 0; i<data.size()+1; i++) {
			obj.setId_client(obj.getId_client() + 1);
		}
		
		this.data.add(obj);
		return true;
	}

	@Override
	public boolean delete(Client obj) throws SQLException {
		Iterator<Client> dataIterator = this.data.iterator();
		
		while (dataIterator.hasNext()) {
			Client datum = dataIterator.next();
			if(obj.getId_client() == datum.getId_client()) {
				dataIterator.remove();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean update(Client obj) throws SQLException {
		ListIterator<Client> dataListIterator = this.data.listIterator();
		int idx = this.data.indexOf(obj);

		while (dataListIterator.hasNext()) {
			Client datum = dataListIterator.next();
			if(obj.getId_client() == datum.getId_client()) {
				dataListIterator.set(obj);
				return true;
			}
		}	
		return false;
	}

	@Override
	public List<Client> getAll() throws SQLException {
		return data;
	}

	@Override
	public Client getById(int id) throws SQLException {
		Iterator<Client> dataIterator = this.data.iterator();
		
		while (dataIterator.hasNext()) {
			Client datum = dataIterator.next();
			if(datum.getId_client() == id) {
				return datum;
			}
		}
		throw new IllegalArgumentException("le client que vous recherchez n'existe pas");
	}

	@Override
	public List<Client> getByNomPrenom(String nom, String prenom) throws SQLException {
		Iterator<Client> dataIterator = this.data.iterator();
		List<Client> result = new ArrayList<Client>();
		
		while(dataIterator.hasNext()) {
			Client datum = dataIterator.next();
			if(datum.getNom() == nom && datum.getPrenom() == prenom) {
				result.add(datum);
			}
		}
		return result;
	}

}
