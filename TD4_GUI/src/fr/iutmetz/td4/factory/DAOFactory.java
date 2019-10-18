package fr.iutmetz.td4.factory;

import fr.iutmetz.td4.dao.PeriodiciteDAO;
import fr.iutmetz.td4.dao.Persistance;
import fr.iutmetz.td4.dao.RevueDAO;

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
	
	public abstract PeriodiciteDAO getPeriodiciteDAO();
	public abstract RevueDAO getRevueDAO();
	
	// Rajouter les autres get
}
