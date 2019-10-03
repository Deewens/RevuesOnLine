package fr.iutmetz.td2.dao.lm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.iutmetz.td2.dao.AbonnementDAO;
import fr.iutmetz.td2.exceptions.ExistingCompositeKeyException;
import fr.iutmetz.td2.exceptions.NonExistentDataObjectException;
import fr.iutmetz.td2.pojo.Abonnement;

public class ListeMemoireAbonnementDAO implements AbonnementDAO {
	private static ListeMemoireAbonnementDAO instance;
	private List<Abonnement> data;
	
	private ListeMemoireAbonnementDAO() {
		this.data = new ArrayList<Abonnement>();
		
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dateDebut = LocalDate.parse("10/02/2019", formatage);
		LocalDate dateFin = LocalDate.parse("20/02/2019", formatage);
		
		this.data.add(new Abonnement(1, 2, dateDebut, dateFin));
		this.data.add(new Abonnement(2, 4, dateDebut, dateFin));
	}

	public static ListeMemoireAbonnementDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireAbonnementDAO();
		}
		return instance;
	}
	
	@Override
	public boolean create(Abonnement obj) throws ExistingCompositeKeyException {
		Iterator<Abonnement> dataIterator = data.iterator();
		
		while (dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(obj.getId_client() == datum.getId_client() && obj.getId_revue() == datum.getId_revue()) {
				throw new ExistingCompositeKeyException("La clé primaire existe déjà.");
				
			}
		}
		
		boolean create = this.data.add(obj);
		return create;
	}

	@Override
	public boolean delete(Abonnement obj) throws NonExistentDataObjectException {
		Abonnement delete;
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
	public boolean update(Abonnement obj) throws NonExistentDataObjectException {
		ListIterator<Abonnement> dataListIterator = data.listIterator();
		
		while(dataListIterator.hasNext()) {
			Abonnement abo = dataListIterator.next();
			if(obj.getId_client() == abo.getId_client() && obj.getId_client() == abo.getId_revue()) {
				dataListIterator.set(obj);
				return true;
			}
		}
		throw new NonExistentDataObjectException("Clé primaire inexistante.");
	}
	
	@Override
	public ArrayList<Abonnement> getAll() {
		return (ArrayList<Abonnement>) this.data;
	}
	
	@Override
	public Abonnement getByIds(int idClient, int idRevue) {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		
		if(idClient < 0) {
			throw new IllegalArgumentException("idClient must be > 0"); 
		}
		if(idRevue < 0) {
			throw new IllegalArgumentException("idRevue must be > 0");
		}
		
		while (dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(datum.getId_client() == idClient && datum.getId_revue() == idRevue) {
				return datum;
			}
		}
		return null;
	}

	@Override
	public List<Abonnement> getByDate_debut(LocalDate date_debut) {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		List<Abonnement> result = new ArrayList<Abonnement>();
		
		while(dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(datum.getDate_debut().isEqual(date_debut)) {
				result.add(datum);
			}
		}
		return result;
	}

	@Override
	public List<Abonnement> getByDate_fin(LocalDate date_fin) {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		List<Abonnement> result = new ArrayList<Abonnement>();
		
		while(dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(datum.getDate_fin().isEqual(date_fin)) {
				result.add(datum);
			}
		}
		
		return result;
	}

}
