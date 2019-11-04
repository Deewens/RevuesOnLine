package cpoa.projet.dao.lm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpoa.projet.Persistance;
import cpoa.projet.dao.RevueBufferDAO;
import cpoa.projet.factory.DAOFactory;
import cpoa.projet.pojo.Client;
import cpoa.projet.pojo.Periodicite;
import cpoa.projet.pojo.Revue;
import cpoa.projet.pojo.RevueBuffer;

public class ListeMemoireRevueBufferDAO implements RevueBufferDAO {
	private static int id;
	private static ListeMemoireRevueBufferDAO instance;
	private static DAOFactory dao = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
	
	private List<RevueBuffer> data;

	public static ListeMemoireRevueBufferDAO getInstance() {
		if(instance == null) {
			instance = new ListeMemoireRevueBufferDAO();
		}
		return instance;
	}

	@Override
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNb() {
		ArrayList<RevueBuffer> result = new ArrayList<>();
		ArrayList<Revue> listRevue = new ArrayList<>();
		ArrayList<Periodicite> listPeriod = new ArrayList<>();
		try {
			listRevue = dao.getRevueDAO().getAll();
			listPeriod = dao.getPeriodiciteDAO().getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<Revue> revueIterator = listRevue.iterator();
		
		while (revueIterator.hasNext()) {
			Revue datum = revueIterator.next();
			Iterator<Periodicite> periodIterator = listPeriod.iterator();
			while(periodIterator.hasNext()) {
				Periodicite period = periodIterator.next();
				if(datum.getId_periodicite() == period.getId()) {
					result.add(new RevueBuffer(datum, period, 0));
				}
			}
		}
		
		return (ArrayList<RevueBuffer>) result;
	}

	@Override
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNbByTitre(String titre) {
		Iterator<RevueBuffer> dataIterator = this.data.iterator();
		List<RevueBuffer> result = new ArrayList<RevueBuffer>();
		
		while(dataIterator.hasNext()) {
			RevueBuffer datum = dataIterator.next();
			if(datum.getRevue().getTitre().equals(titre)) {
				result.add(datum);
			}
		}
		return (ArrayList<RevueBuffer>) result;
	}

	@Override
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNbLessThanTarif_numero(double tarif) {
		Iterator<RevueBuffer> dataIterator = this.data.iterator();
		List<RevueBuffer> result = new ArrayList<RevueBuffer>();
		
		while(dataIterator.hasNext()) {
			RevueBuffer datum = dataIterator.next();
			if(datum.getRevue().getTarif_numero() >= tarif) {
				result.add(datum);
			}
		}
		return (ArrayList<RevueBuffer>) result;
	}
}
