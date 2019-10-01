package fr.iutmetz.td2.factory;

import fr.iutmetz.td2.dao.AbonnementDAO;
import fr.iutmetz.td2.dao.ClientDAO;
import fr.iutmetz.td2.dao.PeriodiciteDAO;
import fr.iutmetz.td2.dao.RevueDAO;
import fr.iutmetz.td2.dao.lm.ListeMemoireAbonnementDAO;
import fr.iutmetz.td2.dao.lm.ListeMemoireClientDAO;
import fr.iutmetz.td2.dao.lm.ListeMemoirePeriodiciteDAO;
import fr.iutmetz.td2.dao.lm.ListeMemoireRevueDAO;

public class ListeMemoireDAOFactory extends DAOFactory {
	
	public ListeMemoireDAOFactory() {
		
	}

	@Override
	public AbonnementDAO getAbonnementDAO() {
		// TODO Auto-generated method stub
		return ListeMemoireAbonnementDAO.getInstance();
	}
	
	public ClientDAO getClientDAO() {
		return ListeMemoireClientDAO.getInstance();
	}

	@Override
	public PeriodiciteDAO getPeriodiciteDAO() {
		// TODO Auto-generated method stub
		return ListeMemoirePeriodiciteDAO.getInstance();
	}
	
	public RevueDAO getRevueDAO() {
		return ListeMemoireRevueDAO.getInstance();
	}

}
