package fr.iutmetz.td3.factory;

import fr.iutmetz.td3.dao.AbonnementDAO;
import fr.iutmetz.td3.dao.ClientDAO;
import fr.iutmetz.td3.dao.RevueDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoireAbonnementDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoireClientDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoirePeriodiciteDAO;
import fr.iutmetz.td3.dao.lm.ListeMemoireRevueDAO;
import fr.iutmetz.td3.dao.PeriodiciteDAO;

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
