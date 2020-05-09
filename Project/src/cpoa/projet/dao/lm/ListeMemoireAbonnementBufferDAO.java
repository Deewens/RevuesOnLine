package cpoa.projet.dao.lm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpoa.projet.Persistance;
import cpoa.projet.dao.AbonnementBufferDAO;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Abonnement;
import cpoa.projet.pojo.AbonnementBuffer;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Revue;

public class ListeMemoireAbonnementBufferDAO implements AbonnementBufferDAO {
	private static ListeMemoireAbonnementBufferDAO instance;
	private static DAOFactory dao = DAOFactory.getDAOFactory(Persistance.ListeMemoire);

	public static ListeMemoireAbonnementBufferDAO getInstance() {
		if(instance == null) {
			instance = new ListeMemoireAbonnementBufferDAO();
		}
		return instance;
	}
	
	@Override
	public ArrayList<AbonnementBuffer> getAboWithNameAndTitle() {
		ArrayList<AbonnementBuffer> result = new ArrayList<>();
		ArrayList<Abonnement> listAbo = new ArrayList<>();
		ArrayList<Client> listClient = new ArrayList<>();
		ArrayList<Revue> listRevue = new ArrayList<>();
		
		try {
			listAbo = dao.getAbonnementDAO().getAll();
			listClient = dao.getClientDAO().getAll();
			listRevue = dao.getRevueDAO().getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<Abonnement> aboIterator = listAbo.iterator();
		
		while (aboIterator.hasNext()) {
			Abonnement abo = aboIterator.next();
			Iterator<Client> clientIterator = listClient.iterator();
			while(clientIterator.hasNext()) {
				Client client = clientIterator.next();
				Iterator<Revue> revueIterator = listRevue.iterator();
				while(revueIterator.hasNext()) {
					Revue revue = revueIterator.next();
					if(abo.getId_client() == client.getId_client() && abo.getId_revue() == revue.getId_revue()) {
						result.add(new AbonnementBuffer(abo, client, revue, abo.getDate_debut(), abo.getDate_fin()));
					}
				}
			}
		}
		
		return result;
	}

}
