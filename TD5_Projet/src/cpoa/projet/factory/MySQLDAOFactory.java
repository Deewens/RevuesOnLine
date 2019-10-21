package cpoa.projet.factory;

import cpoa.projet.dao.AbonnementDAO;
import cpoa.projet.dao.ClientDAO;
import cpoa.projet.dao.PeriodiciteDAO;
import cpoa.projet.dao.RevueDAO;
import cpoa.projet.dao.mysql.MySQLAbonnementDAO;
import cpoa.projet.dao.mysql.MySQLClientDAO;
import cpoa.projet.dao.mysql.MySQLPeriodiciteDAO;
import cpoa.projet.dao.mysql.MySQLRevueDAO;

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
