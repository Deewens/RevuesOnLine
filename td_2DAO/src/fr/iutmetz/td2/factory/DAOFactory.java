package fr.iutmetz.td2.factory;

import fr.iutmetz.td2.Persistance;
import fr.iutmetz.td2.dao.AbonnementDAO;
import fr.iutmetz.td2.dao.ClientDAO;
import fr.iutmetz.td2.dao.PeriodiciteDAO;
import fr.iutmetz.td2.dao.RevueDAO;

public abstract class DAOFactory {
	public static DAOFactory getDAOFactory(Persistance cible) {
		DAOFactory daoF = null;
		
		switch(cible) {
		case MySQL:
			daoF = new MySQLDAOFactory();
			break;
			
		case ListeMemoire:
			daoF = new ListeMemoireDAOFactory();
			break;
		}
		
		return daoF;
	}
	
	public abstract AbonnementDAO getAbonnementDAO();
	public abstract ClientDAO getClientDAO();
	public abstract PeriodiciteDAO getPeriodiciteDAO();
	public abstract RevueDAO getRevueDAO();
	
	// Rajouter les autres get
}
