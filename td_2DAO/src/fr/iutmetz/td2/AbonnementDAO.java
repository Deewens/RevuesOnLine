package fr.iutmetz.td2;

import java.time.*;
import java.util.List;

public interface AbonnementDAO extends DAO<Abonnement> {
	public Abonnement getByIds(int idClient, int idRevue) throws Exception;
	public List<Abonnement> getByDate_debut(LocalDate date_debut) throws Exception;
	public List<Abonnement> getByDate_fin(LocalDate date_fin) throws Exception;
}