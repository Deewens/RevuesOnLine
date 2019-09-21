package fr.iutmetz.td2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListeMemoireAbonnementDAO implements AbonnementDAO {
	
	private static ListeMemoireAbonnementDAO instance;
	
	private List<Abonnement> data;
	
	private ListeMemoireAbonnementDAO() {
		this.data = new ArrayList<Abonnement>();
		
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse("10/05/2019", formatage);
		LocalDate dateFin = LocalDate.parse("20/09/2019", formatage);
		
		this.data.add(new Abonnement(1, 1, dateDebut, dateFin));
	}

	public static ListeMemoireAbonnementDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireAbonnementDAO();
		}
		return instance;
	}
	
	@Override
	public boolean create(Abonnement obj) throws Exception {
		
		data.add(obj);
		
		return false;
	}

	@Override
	public boolean delete(Abonnement obj) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Abonnement obj) throws Exception {
		int idxClient = this.data.indexOf(obj.getId_client());
		int idxRevue =  this.data.indexOf(obj.getId_revue());
		
		for(int i = 0; i < data.size(); i++) {
			if(obj.getId_client() == data.get(i).getId_client() && obj.getId_revue() == data.get(i).getId_revue()) {
				this.data.set(i, obj);
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
