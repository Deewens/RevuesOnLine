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

		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dateDebut = LocalDate.parse("10/02/2019", formatage);
		LocalDate dateFin = LocalDate.parse("20/02/2019", formatage);
		
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		try {
			daos.getAbonnementDAO().create(abo);
		} catch(Exception e) {
			System.out.println("[ListeMemoire] Erreur ajout \"Abonnement\" : " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			List<Abonnement> abos = daos.getAbonnementDAO().getAll();
			if(!abos.isEmpty()) {
				abos.forEach(aboI -> {
					System.out.println(aboI.getId_client()); 
					System.out.println(aboI.getId_revue());
					System.out.println(aboI.getDate_debut());
					System.out.println(aboI.getDate_fin());
				});
			}
			else {
				System.out.println("La liste \"Abonnement\" ne contient aucune données.");
			}
		} catch (Exception e) {
			System.out.println("[ListeMemoire] Erreur de lecture : " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			daos.getAbonnementDAO().delete(abo);
		} catch (Exception e) {
			System.out.println("[ListeMemoire] Erreur de suppresion : " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			List<Abonnement> abos = daos.getAbonnementDAO().getAll();
			if(!abos.isEmpty()) {
				abos.forEach(aboI -> {
					System.out.println(aboI.getId_client()); 
					System.out.println(aboI.getId_revue());
					System.out.println(aboI.getDate_debut());
					System.out.println(aboI.getDate_fin());
				});
			}
			else {
				System.out.println("La liste \"Abonnement\" ne contient aucune données.");
			}
		} catch (Exception e) {
			System.out.println("[ListeMemoire] Erreur de lecture : " + e.getMessage());
			e.printStackTrace();
		}
	}
}