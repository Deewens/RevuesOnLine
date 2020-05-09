package cpoa.projet.dao;


import java.time.LocalDate;
import java.util.ArrayList;

import cpoa.projet.pojo.Abonnement;

public interface AbonnementDAO extends DAO<Abonnement> {
	
	public Abonnement getByIds(int idClient, int idRevue) throws Exception;
	public ArrayList<Abonnement> getByIdClient(int idClient) throws Exception;
	public ArrayList<Abonnement> getByIdRevue(int idRevue) throws Exception;
	public ArrayList<Abonnement> getByDate_debut(LocalDate date_debut) throws Exception;
	public ArrayList<Abonnement> getByDate_fin(LocalDate date_fin) throws Exception;
}