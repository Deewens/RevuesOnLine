package fr.iutmetz.td2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		/* DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MySQL);
		
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dateDebut = LocalDate.parse("10/02/2019", formatage);
		LocalDate dateFin = LocalDate.parse("20/02/2019", formatage);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		/* try {
			daos.getAbonnementDAO().create(abo);
		} catch (Exception e) {
			System.out.println("Erreur.");
			e.printStackTrace();
		} */
		
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);

		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dateDebut = LocalDate.parse("10/02/2019", formatage);
		LocalDate dateFin = LocalDate.parse("20/02/2019", formatage);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			daos.getAbonnementDAO().create(abo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			List<Abonnement> abo1 = daos.getAbonnementDAO().getAll();
			System.out.println(abo1.get(1).getDate_debut());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LocalDate dateDebut2 = LocalDate.parse("10/02/2018", formatage);
		LocalDate dateFin2 = LocalDate.parse("20/02/2018", formatage);
		
		abo = new Abonnement(1, 2, dateDebut2, dateFin2);
		
		try {
			daos.getAbonnementDAO().update(abo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Abonnement> abo1 = daos.getAbonnementDAO().getAll();
			System.out.println(abo1.get(1).getDate_debut());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
