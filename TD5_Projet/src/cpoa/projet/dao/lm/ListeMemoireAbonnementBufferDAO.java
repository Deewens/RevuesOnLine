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
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
import cpoa.projet.pojo.RevueBuffer;

public class ListeMemoireAbonnementBufferDAO implements AbonnementBufferDAO {
	private static int id;
	private static ListeMemoireAbonnementBufferDAO instance;
	private static DAOFactory dao = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
	
	private List<AbonnementBuffer> data;

	public static ListeMemoireAbonnementBufferDAO getInstance() {
		if(instance == null) {
			instance = new ListeMemoireAbonnementBufferDAO();
		}
		return instance;
	}
	
	public ListeMemoireAbonnementBufferDAO() {
		this.data = new ArrayList<AbonnementBuffer>();
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
						this.data.add(new AbonnementBuffer(abo, client, revue, abo.getDate_debut(), abo.getDate_fin()));
					}
				}
			}
		}
		
		id = this.data.size();
	}
	@Override
	public ArrayList<AbonnementBuffer> getAboWithNameAndTitle() {
		// TODO Auto-generated method stub
		return (ArrayList<AbonnementBuffer>) data;
	}

}
