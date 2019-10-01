package fr.iutmetz.td2.factory;

import fr.iutmetz.td2.dao.AbonnementDAO;
import fr.iutmetz.td2.dao.ClientDAO;
import fr.iutmetz.td2.dao.PeriodiciteDAO;
import fr.iutmetz.td2.dao.RevueDAO;
import fr.iutmetz.td2.dao.mysql.MySQLAbonnementDAO;
import fr.iutmetz.td2.dao.mysql.MySQLClientDAO;
import fr.iutmetz.td2.dao.mysql.MySQLPeriodiciteDAO;
import fr.iutmetz.td2.dao.mysql.MySQLRevueDAO;

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
