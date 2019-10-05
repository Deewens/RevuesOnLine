package fr.iutmetz.td3.factory;

import fr.iutmetz.td3.dao.AbonnementDAO;
import fr.iutmetz.td3.dao.ClientDAO;
import fr.iutmetz.td3.dao.PeriodiciteDAO;
import fr.iutmetz.td3.dao.RevueDAO;
import fr.iutmetz.td3.dao.mysql.MySQLAbonnementDAO;
import fr.iutmetz.td3.dao.mysql.MySQLClientDAO;
import fr.iutmetz.td3.dao.mysql.MySQLPeriodiciteDAO;
import fr.iutmetz.td3.dao.mysql.MySQLRevueDAO;

public class MySQLDAOFactory extends DAOFactory {
	public MySQLDAOFactory() {
		
	}
	
	@Override
	public AbonnementDAO getAbonnementDAO() {
		// TODO Auto-generated method stub
		return MySQLAbonnementDAO.getInstance();
	}

	@Override
	public ClientDAO getClientDAO() {
		// TODO Auto-generated method stub
		return MySQLClientDAO.getInstance();
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
