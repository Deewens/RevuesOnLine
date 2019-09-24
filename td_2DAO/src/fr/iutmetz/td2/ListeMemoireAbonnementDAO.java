package fr.iutmetz.td2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

public class ListeMemoireAbonnementDAO implements AbonnementDAO {
	private static ListeMemoireAbonnementDAO instance;
	private List<Abonnement> data;
	
	private ListeMemoireAbonnementDAO() {
		this.data = new ArrayList<Abonnement>();
		
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dateDebut = LocalDate.parse("10/02/2019", formatage);
		LocalDate dateFin = LocalDate.parse("20/02/2019", formatage);
		
		this.data.add(new Abonnement(1, 2, dateDebut, dateFin));
	}

	public static ListeMemoireAbonnementDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireAbonnementDAO();
		}
		return instance;
	}
	
	@Override
	public boolean create(Abonnement obj) throws Exception {
		Iterator<Abonnement> dataIterator = data.iterator();
		while (dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(obj.getId_client() == datum.getId_client() && obj.getId_revue() == datum.getId_revue()) {
				return false;
			}
		}
		
		this.data.add(obj);
		return true;
	}

	@Override
	public boolean delete(Abonnement obj) throws Exception {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		
		while (dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(obj.getId_client() == datum.getId_client() && obj.getId_revue() == datum.getId_revue()) {
				dataIterator.remove();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean update(Abonnement obj) throws Exception {
		ListIterator<Abonnement> dataListIterator = this.data.listIterator();
		int idx = this.data.indexOf(obj);

		while (dataListIterator.hasNext()) {
			Abonnement datum = dataListIterator.next();
			if(obj.getId_client() == datum.getId_client() && obj.getId_revue() == datum.getId_revue()) {
				dataListIterator.set(obj);
				return true;
			}
		}	
		return false;
	}
	
	@Override
	public List<Abonnement> getAll() throws Exception {
		return data;
	}
	
	@Override
	public Abonnement getByIds(int idClient, int idRevue) throws Exception {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		
		while (dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(datum.getId_client() == idClient && datum.getId_revue() == idRevue) {
				return datum;
			}
		}
		throw new IllegalArgumentException("l'abonnement que vous recherchez n'existe pas");
	}

	@Override
	public List<Abonnement> getByDate_debut(LocalDate date_debut) throws Exception {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		List<Abonnement> result = new ArrayList<Abonnement>();
		
		while(dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(datum.getDate_debut() == date_debut) {
				result.add(datum);
			}
		}
		return result;
	}

	@Override
	public List<Abonnement> getByDate_fin(LocalDate date_fin) throws Exception {
		Iterator<Abonnement> dataIterator = this.data.iterator();
		List<Abonnement> result = new ArrayList<Abonnement>();
		
		while(dataIterator.hasNext()) {
			Abonnement datum = dataIterator.next();
			if(datum.getDate_fin() == date_fin) {
				result.add(datum);
			}
		}
		return result;
	}

}
