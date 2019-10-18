package fr.iutmetz.td4.factory;

import fr.iutmetz.td4.dao.PeriodiciteDAO;
import fr.iutmetz.td4.dao.RevueDAO;
import fr.iutmetz.td4.dao.mysql.MySQLPeriodiciteDAO;
import fr.iutmetz.td4.dao.mysql.MySQLRevueDAO;

public class MySQLDAOFactory extends DAOFactory {
	public MySQLDAOFactory() {
		
	}
	@Override
	public PeriodiciteDAO getPeriodiciteDAO() {
		// TODO Auto-generated method stub
		return MySQLPeriodiciteDAO.getInstance();
	}

	@Override
	public RevueDAO getRevueDAO() {
		// TODO Auto-generated method stub
		return MySQLRevueDAO.getInstance();
	}
}
