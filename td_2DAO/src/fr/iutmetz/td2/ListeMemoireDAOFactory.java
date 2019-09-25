package fr.iutmetz.td2;

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
	
	
}
