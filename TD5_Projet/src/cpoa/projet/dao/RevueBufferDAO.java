package cpoa.projet.dao;

import java.util.ArrayList;

import cpoa.projet.pojo.RevueBuffer;

public interface RevueBufferDAO {
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNb() throws Exception;
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNbByTitre(String titre) throws Exception;
	public ArrayList<RevueBuffer> getClientAndPeriodLibelleAndAboNbLessThanTarif_numero(double tarif) throws Exception;
}
