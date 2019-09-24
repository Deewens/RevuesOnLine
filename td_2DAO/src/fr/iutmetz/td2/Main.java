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
		
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MySQL);
		
		Abonnement abo = new Abonnement(1, 2, dateDebut, dateFin);
		
		// Create
		try {
			boolean ok = daos.getAbonnementDAO().create(abo);
			if(!ok) {
				System.out.println("Le client n°" + abo.getId_client() + " que vous avez choisi est déjà abonné à la revue n°" + abo.getId_revue() + ".");
			}
		} catch(Exception e) {
			System.out.println("Erreur ajout \"Abonnement\" : " + e.getMessage());
		}
		
		// Affichage
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
		
		dateFin = LocalDate.parse("20/02/2020", formatage);
		abo.setDate_fin(dateFin);
		
		try {
			boolean update = daos.getAbonnementDAO().update(abo);
			if(!update) System.out.println("Vous ne pouvez pas modifier un abonnement qui n'existe pas.");
		} catch (Exception e) {
			System.out.println("[ListeMemoire] Erreur update \"Abonnement\" : " + e.getMessage());
		}
		
		// Affichage
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
					System.out.println("[ListeMemoire] Erreur lecture \"Abonnement\": " + e.getMessage());
					e.printStackTrace();
				}
		
		// Suppresion
		/* try {
			boolean delete = daos.getAbonnementDAO().delete(abo);
			if(!delete) {
				System.out.println("Vous ne pouvez pas supprimer un abonnement qui n'existe pas.");
			}
		} catch (Exception e) {
			System.out.println("[ListeMemoire] Erreur suppresion \"Abonnement\" : " + e.getMessage());
		} */
		
		// Affichage
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
			System.out.println("[ListeMemoire] Erreur lecture : " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			Abonnement abos = daos.getAbonnementDAO().getByIds(1, 2);
			System.out.println(abos);
			
		} catch (Exception e) {
			System.out.println("[ListeMemoire] Erreur \"Abonnement\" : " + e.getMessage());
		}
		
		try {
			List<Abonnement> abos = daos.getAbonnementDAO().getByDate_fin(dateFin);
			if(!abos.isEmpty()) {
				abos.forEach(aboI -> {
					System.out.println(aboI.getId_client()); 
					System.out.println(aboI.getId_revue());
					System.out.println(aboI.getDate_debut());
					System.out.println(aboI.getDate_fin());
				});
			}
			else {
				System.out.println("La liste \"Abonnement\" ne contient aucune données pour cette date.");
			}
			
		} catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		} 
	}
}