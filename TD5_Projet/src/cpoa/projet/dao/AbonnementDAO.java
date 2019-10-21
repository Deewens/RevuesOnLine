package cpoa.projet.dao;


import java.time.LocalDate;
import java.util.List;

import cpoa.projet.pojo.Abonnement;

public interface AbonnementDAO extends DAO<Abonnement> {
	
	public Abonnement getByIds(int idClient, int idRevue) throws Exception;
	public List<Abonnement> getByDate_debut(LocalDate date_debut) throws Exception;
	public List<Abonnement> getByDate_fin(LocalDate date_fin) throws Exception;
}