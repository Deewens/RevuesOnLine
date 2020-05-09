package cpoa.projet.dao;

import java.util.ArrayList;
import java.util.List;

import cpoa.projet.pojo.AbonnementBuffer;

public interface AbonnementBufferDAO {
	public ArrayList<AbonnementBuffer> getAboWithNameAndTitle() throws Exception; 
}
