package fr.iutmetz.td4.factory;

import fr.iutmetz.td4.dao.PeriodiciteDAO;
import fr.iutmetz.td4.dao.RevueDAO;
import fr.iutmetz.td4.dao.lm.ListeMemoirePeriodiciteDAO;
import fr.iutmetz.td4.dao.lm.ListeMemoireRevueDAO;

public class ListeMemoireDAOFactory extends DAOFactory {
	
	public ListeMemoireDAOFactory() {
		
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
