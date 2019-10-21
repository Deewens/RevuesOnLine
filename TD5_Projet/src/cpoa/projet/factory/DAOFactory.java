package cpoa.projet.factory;

import cpoa.projet.Persistance;
import cpoa.projet.dao.AbonnementDAO;
import cpoa.projet.dao.ClientDAO;
import cpoa.projet.dao.PeriodiciteDAO;
import cpoa.projet.dao.RevueDAO;

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
