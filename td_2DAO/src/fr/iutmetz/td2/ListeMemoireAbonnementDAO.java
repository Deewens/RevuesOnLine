package fr.iutmetz.td2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ListeMemoireAbonnementDAO implements AbonnementDAO {
	private static ListeMemoireAbonnementDAO instance;
	private List<Abonnement> data;
	
	private ListeMemoireAbonnementDAO() {
		this.data = new ArrayList<Abonnement>();
	}

	public static ListeMemoireAbonnementDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireAbonnementDAO();
		}
		return instance;
	}
	
	@Override
	public boolean create(Abonnement obj) throws Exception {
		if(obj == null) {
			return false;
		}
		
		data.add(obj);
		return true;
	}

	@Override
	public boolean delete(Abonnement obj) throws Exception {
		Iterator<Abonnement> dataIterator = data.iterator();
		
		while (dataIterator.hasNext()) {
			Abonnement abo = dataIterator.next();
			if(obj.getId_client() == abo.getId_client() && obj.getId_revue() == abo.getId_revue()) {
				dataIterator.remove();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean update(Abonnement obj) throws Exception {
		ListIterator<Abonnement> dataListIterator = data.listIterator();
		
		while (dataListIterator.hasNext()) {
			Abonnement abo = dataListIterator.next();
			if(obj.getId_client() == abo.getId_client() && obj.getId_revue() == abo.getId_revue()) {
				dataListIterator.set(obj);
				return true;
			}
		}	
		return false;
	}

	@Override
	public Abonnement getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Abonnement> getAll() throws Exception {
		return data;
	}

	@Override
	public List<Abonnement> getByDate_debut(LocalDate date_debut) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Abonnement> getByDate_fin(LocalDate date_fin) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
