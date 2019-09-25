package fr.iutmetz.td2;

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
	
	// Rajouter les autres get
}
