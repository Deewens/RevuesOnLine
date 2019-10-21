package cpoa.projet.factory;

import cpoa.projet.dao.AbonnementDAO;
import cpoa.projet.dao.ClientDAO;
import cpoa.projet.dao.PeriodiciteDAO;
import cpoa.projet.dao.RevueDAO;
import cpoa.projet.dao.lm.ListeMemoireAbonnementDAO;
import cpoa.projet.dao.lm.ListeMemoireClientDAO;
import cpoa.projet.dao.lm.ListeMemoirePeriodiciteDAO;
import cpoa.projet.dao.lm.ListeMemoireRevueDAO;

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
