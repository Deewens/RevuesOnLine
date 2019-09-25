package fr.iutmetz.td2;

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
}
