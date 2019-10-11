package fr.iutmetz.td3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import fr.iutmetz.td3.factory.DAOFactory;
import fr.iutmetz.td3.pojo.Abonnement;
import fr.iutmetz.td3.pojo.Client;
import fr.iutmetz.td3.pojo.Periodicite;
import fr.iutmetz.td3.pojo.Revue;

public class Main {

	public static void main(String[] args) {
		int dataChoice = 0;
		int optionChoice = 0;

		int readAboChoice = 0; 

		boolean repeat; 

		Scanner sc = new Scanner(System.in);
		DAOFactory daos = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println("Bienvenue. Ici vous pouvez gï¿½rer vos donnï¿½es stockï¿½s dans diffï¿½rents systï¿½me.\n"
				+ " Ce mini-programme en ligne de commandes va vous permettre d'ajouter, supprimer, modifier et voir les donnï¿½es d'une base de donnï¿½es et d'une Liste en mï¿½moire.");
		System.out.println("Instructions d'utilisation :");
		System.out.println("Pour naviguer dans le programme, vous devez indiquer le numï¿½ro du menu auquel vous souhaitez accï¿½der et appuyer sur entrer.");
		System.out.println();
		
		/* ---------------------------
		 * Choix du mode de stockage
		 * --------------------------- */
		daos = storageChoice();

		/* ---------------------------
		 * Choix des donnï¿½es
		 * --------------------------- */
		do {
			/* |----------------------------------| *
			 * |----------- ABONNEMENT -----------| *
			 * |----------------------------------| */
			boolean restart = true; // Boolean de restart du formulaire
			dataChoice = dataChoice();
			switch(dataChoice) {
			case 1: // Abonnement
				do {
					Abonnement abo = new Abonnement();
					boolean aboKeyValid = false;
					int idClientAbo = 0;
					int idRevueAbo = 0;

					optionChoice = optionChoice();
					switch(optionChoice) {
					case 1: // Lire abonnement
						do {
							repeat = false;
							readAboChoice = readAboChoice();
							if(readAboChoice == 1) { // Lire abonnement tout
								try {
									List<Abonnement> abos = daos.getAbonnementDAO().getAll();
									if(!abos.isEmpty()) {
										abos.forEach(aboI -> {
											System.out.println(aboI.getId_client() + "\t" + aboI.getId_revue() + "\t" + aboI.getDate_debut() + "\t" + aboI.getDate_fin());
										});
									}
									else {
										System.out.println("\"Abonnement\" est vide.");
									}
								} catch(Exception e) {
									System.out.println("Erreur : " + e.getMessage());
								}
							}
							else if(readAboChoice == 2) { // Lire abonnement par Id
								do {
									System.out.print("ID Client : ");
									idClientAbo = scanInt();
									if(idClientAbo < 1) {
										System.out.println("ID Client doit ï¿½tre supï¿½rieur ï¿½ 0.");
									}
								} while(idClientAbo < 1);

								do {
									System.out.print("ID Revue : ");
									idRevueAbo = scanInt();
									if(idRevueAbo < 1) {
										System.out.println("ID Revue doit ï¿½tre supï¿½rieur ï¿½ 0.");
									}
								} while(idRevueAbo < 1);

								try {
									Abonnement abos = daos.getAbonnementDAO().getByIds(idClientAbo, idRevueAbo);
									if(abos != null) {
										System.out.println(abos.getId_client() + "\t" + abos.getId_revue() + "\t" + abos.getDate_debut() + "\t" + abos.getDate_fin());
									}
									else {
										System.out.println("Il n'existe pas d'abonnement pour cette clï¿½.");
									}

								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
							}
							else if(readAboChoice == 3) { // Lire en fonction d'une date
								String dateDebut;
								do {
									System.out.print("Date de dï¿½but d'abonnement (JJ/MM/AAAA) : ");
									dateDebut = sc.nextLine();
									System.out.println();
									if(!dateDebut.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
										System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
									}
								} while(!dateDebut.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$"));

								LocalDate dateDebutConvert = LocalDate.parse(dateDebut, format);

								try {
									List<Abonnement> abos = daos.getAbonnementDAO().getByDate_debut(dateDebutConvert);
									if(!abos.isEmpty()) {
										abos.forEach(aboI -> {
											System.out.println(aboI.getId_client() + "\t" + aboI.getId_revue() + "\t" + aboI.getDate_debut() + "\t" + aboI.getDate_fin());
										});
									}
									else {
										System.out.println("La liste \"Abonnement\" ne contient aucune donnï¿½es pour cette date.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
							}
							else if(readAboChoice == 4) { // Lire en fonction d'une date de fin
								String dateFin;
								do {
									System.out.print("Date de fin d'abonnement (JJ/MM/AAAA) : ");
									dateFin = sc.nextLine();
									System.out.println();
									if(!dateFin.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
										System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
									}
								} while(!dateFin.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$"));

								LocalDate dateFinConvert = LocalDate.parse(dateFin, format);

								try {
									List<Abonnement> abos = daos.getAbonnementDAO().getByDate_fin(dateFinConvert);
									if(!abos.isEmpty()) {
										abos.forEach(aboI -> {
											System.out.println(aboI.getId_client() + "\t" + aboI.getId_revue() + "\t" + aboI.getDate_debut() + "\t" + aboI.getDate_fin());
										});
									}
									else {
										System.out.println("La liste \"Abonnement\" ne contient aucune donnï¿½es pour cette date de fin.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR :" + e.getMessage());
									e.printStackTrace();
								}
							}
							else {
								System.out.println("Vous devez choisir une option entre 1, 2, 3 et 4.");
								repeat = true;
							}
						} while(repeat);
						repeat = false;
						break;
					case 2: // Ajouter abonnement
						do {
							do {
								System.out.print("ID Client : ");
								idClientAbo = scanInt();
								if(idClientAbo < 1) {
									System.out.println("ID Client doit être supérieur à 0.");
								}
							} while(idClientAbo < 1);

							do {
								System.out.print("ID Revue : ");
								idRevueAbo = scanInt();
								if(idRevueAbo < 1) {
									System.out.println("ID Revue doit êtretre supérieur à 0.");
								}
							} while(idRevueAbo < 1);

							try {
								abo = daos.getAbonnementDAO().getByIds(idClientAbo, idRevueAbo);
								if(abo != null) {
									System.out.println("Un abonnement existe déjà pour cette clé primaire. Merci de réessayer.");

									aboKeyValid = false;
								}
								else {
									aboKeyValid = true;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!aboKeyValid);

						abo = formAbonnement(false);
						abo.setId_client(idClientAbo);
						abo.setId_revue(idRevueAbo);

						if(aboKeyValid) {
							try {
								boolean isCreated = daos.getAbonnementDAO().create(abo);
								if(isCreated) {
									System.out.println("Ajout de l'abonnement rï¿½ussi !");
								}
								else {
									System.out.println("Une erreur est survenue lors de l'ajout de l'abonnement.");
								}
							} catch (Exception e) {
								System.out.println("ERREUR :" + e.getMessage());
								e.printStackTrace();
							}
						}

						repeat = false;
						break;

					case 3: // Modifier un abonnement
						do {
							do {
								System.out.print("ID Client : ");
								idClientAbo = scanInt();
								if(idClientAbo < 1) {
									System.out.println("ID Client doit être supérieur à 0.");
								}
							} while(idClientAbo < 1);

							do {
								System.out.print("ID Revue : ");
								idRevueAbo = scanInt();
								if(idRevueAbo < 1) {
									System.out.println("ID Revue doit être supérieur à 0.");
								}
							} while(idRevueAbo < 1);

							try {
								abo = daos.getAbonnementDAO().getByIds(idClientAbo, idRevueAbo);
								if(abo != null) {
									aboKeyValid = true;
									System.out.println(abo.getId_client() + "\t" + abo.getId_revue() + "\t" + abo.getDate_debut() + "\t" + abo.getDate_fin());
								}
								else {
									System.out.println("L'abonnement que vous voulez modifier n'existe pas. Merci de réessayer.");
									aboKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!aboKeyValid);

						abo = formAbonnement(true);
						abo.setId_client(idClientAbo);
						abo.setId_revue(idRevueAbo);

						try {
							boolean isUpdated = daos.getAbonnementDAO().update(abo);
							if(isUpdated) System.out.println("Modification de l'abonnement réussi !");
							else System.out.println("Une erreur est survenue lors de la modification de l'abonnement.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;

					case 4: // Supprimer un abonnement
						do {
							do {
								System.out.print("ID Client : ");
								idClientAbo = scanInt();
								if(idClientAbo < 1) {
									System.out.println("ID Client doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idClientAbo < 1);

							do {
								System.out.print("ID Revue : ");
								idRevueAbo = scanInt();
								if(idRevueAbo < 1) {
									System.out.println("ID Revue doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idRevueAbo < 1);

							try {
								abo = daos.getAbonnementDAO().getByIds(idClientAbo, idRevueAbo);
								if(abo != null) {
									aboKeyValid = true;
								}
								else {
									System.out.println("L'abonnement que vous voulez supprimer n'existe pas. Merci de rï¿½essayer.");
									aboKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!aboKeyValid);


						try {
							boolean isDeleted = daos.getAbonnementDAO().delete(abo);
							if(isDeleted) System.out.println("Suppresion de l'abonnement rï¿½ussi !");
							else System.out.println("Une erreur est survenue lors de la suppresion de l'abonnement.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						} 
						repeat = false;
						break;

					default:
						System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
						repeat = true;
					}
				} while(repeat);
				repeat = false;
				break;
				/* |----------------------------------| *
				 * |-------------- CLIENT ------------| *
				 * |----------------------------------| */
			case 2: // Client
				do {
					optionChoice = optionChoice();
					Client client = new Client();
					int idClient;
					boolean clientKeyValid = false;

					switch(optionChoice) {
					case 1: // Lire client
						int readClientChoice = 0;
						do {
							readClientChoice = readClientChoice();
							if(readClientChoice == 1) { // Lire tout
								try {
									List<Client> clients = daos.getClientDAO().getAll();
									if(!clients.isEmpty()) {
										clients.forEach(clientI -> {
											System.out.println(clientI.getId_client() + "\t" + clientI.getNom() + "\t" + clientI.getPrenom() + "\t" + clientI.getNo_rue()
											+ "\t" + clientI.getVoie() + "\t" + clientI.getVille() + "\t" + clientI.getPays()); 
										});
									}
									else {
										System.out.println("\"Client\" ne contient aucune donnï¿½es.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
								repeat = false;
							}
							else if(readClientChoice == 2) { // Lire en fonction de l'ID
								do {
									System.out.print("ID : ");
									idClient = scanInt();
									if(idClient < 1) {
										System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
									}
								} while(idClient < 1);

								try {
									client = daos.getClientDAO().getById(idClient);
									if(client != null) {
										System.out.println(client.getId_client() + "\t" + client.getNom() + "\t" + client.getPrenom() + "\t" + client.getNo_rue()
										+ "\t" + client.getVoie() + "\t" + client.getVille() + "\t" + client.getPays());
									}
									else {
										System.out.println("Il n'existe pas de client pour cette clï¿½.");
									}

								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
								repeat = false;
							}
							else if(readClientChoice == 3) { // Lire en fonction du nom et du prï¿½nom
								System.out.print("Nom : ");
								String nom = sc.nextLine();

								System.out.print("Prenom : ");
								String prenom = sc.nextLine();
								try {
									List<Client> clients = daos.getClientDAO().getByNomPrenom(nom, prenom);
									if(!clients.isEmpty()) {
										clients.forEach(clientI -> {
											System.out.println(clientI.getId_client() + "\t" + clientI.getNom() + "\t" + clientI.getPrenom() + "\t" + clientI.getNo_rue()
											+ "\t" + clientI.getVoie() + "\t" + clientI.getVille() + "\t" + clientI.getPays()); 
										});
									}
									else {
										System.out.println("\"Client\" ne contient pas de donnï¿½es pour ce nom et de prï¿½nom.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}

								repeat = false;
							}
							else {
								System.out.println("Vous devez choisir une option entre 1, 2 et 3.");
								repeat = true;
							}
						} while(repeat);
						repeat = false;
						break;

					case 2: // Ajouter un client
						client = formClient(false);

						try {
							boolean isCreated = daos.getClientDAO().create(client);
							if(isCreated) System.out.println("Ajout d'un client rï¿½ussit !");
							else System.out.println("Une erreur s'est produite lors de l'ajout du client.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					case 3: // modifier un client
						do {
							do {
								System.out.print("ID : ");
								idClient = scanInt();
								if(idClient < 1) {
									System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idClient < 1);

							try {
								client = daos.getClientDAO().getById(idClient);
								if(client != null) {
									System.out.println(client.getId_client() + "\t" + client.getNom() + "\t" + client.getPrenom() + "\t" + client.getNo_rue()
									+ "\t" + client.getVoie() + "\t" + client.getVille() + "\t" + client.getPays());
									clientKeyValid = true;
								}
								else {
									System.out.println("Le client que vous voulez modifier n'existe pas. Merci de rï¿½essayer.");
									clientKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!clientKeyValid);

						client = formClient(true);
						client.setId_client(idClient);

						try {
							boolean isUpdated = daos.getClientDAO().update(client);
							if(isUpdated) System.out.println("Modification du client rï¿½ussi !");
							else System.out.println("La modification du client a ï¿½chouï¿½.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					case 4: // Supprimer un client
						do {
							do {
								System.out.print("ID : ");
								idClient = scanInt();
								if(idClient < 1) {
									System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idClient < 1);

							try {
								client = daos.getClientDAO().getById(idClient);
								if(client != null) {
									clientKeyValid = true;
								}
								else {
									System.out.println("Le client que vous voulez supprimer n'existe pas. Merci de rï¿½essayer.");
									clientKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!clientKeyValid);

						try {
							boolean isDeleted = daos.getClientDAO().delete(client);
							if(isDeleted) System.out.println("La suppresion du client a ï¿½tï¿½ effectuï¿½ avec succï¿½s !");
							else System.out.println("La suppresion du client a ï¿½chouï¿½.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					default:
						System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
						repeat = true;
					}
				} while(repeat);
				repeat = false;
				break;
				/* |----------------------------------| *
				 * |----------- PERIODICITE ----------| *
				 * |----------------------------------| */
			case 3: // Periodicite
				do {
					optionChoice = optionChoice();
					Periodicite period = new Periodicite();
					int idPeriod;
					boolean periodKeyValid = false;
					switch(optionChoice) {
					case 1: // Lire periodicite
						int readPeriodChoice;
						do {
							repeat = false;
							readPeriodChoice = readPeriodChoice();

							if(readPeriodChoice == 1) { // Lire tout
								try {
									List<Periodicite> periods = daos.getPeriodiciteDAO().getAll();
									if(!periods.isEmpty()) {
										periods.forEach(periodI -> {
											System.out.println(periodI.getId() + "\t" + periodI.getLibelle()); 
										});
									}
									else {
										System.out.println("\"Periodicite\" ne contient aucune donnï¿½es.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
								repeat = false;
							}
							else if(readPeriodChoice == 2) { // Lire periodicite par ID
								do {
									System.out.print("ID : ");
									idPeriod = scanInt();
									if(idPeriod < 1) {
										System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
									}
								} while(idPeriod < 1);

								try {
									period = daos.getPeriodiciteDAO().getById(idPeriod);
									if(period != null) {
										System.out.println(period.getId() + "\t" + period.getLibelle());
									}
									else {
										System.out.println("Il n'existe pas de pï¿½riode pour cette clï¿½.");
									}

								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
								repeat = false;
							}
							else {
								System.out.println("Vous devez choisir entre l'option 1 ou 2.");
								repeat = true;
							}
						} while(repeat);
						repeat = false;
						break;

					case 2: // Ajouter une periodicite
						period = formPeriod(false);

						try {
							boolean isCreated = daos.getPeriodiciteDAO().create(period);

							if(isCreated) System.out.println("Ajout rï¿½ussi !");
							else System.out.println("Une erreur s'est produite lors de l'ajout.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					case 3: // modifier une periodicitï¿½
						do {
							do {
								System.out.print("ID : ");
								idPeriod = scanInt();
								if(idPeriod < 1) {
									System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idPeriod < 1);

							try {
								period = daos.getPeriodiciteDAO().getById(idPeriod);
								if(period != null) {
									System.out.println(period.getId() + "\t" + period.getLibelle());
									periodKeyValid = true;
								}
								else {
									System.out.println("La pï¿½riode que vous voulez modifier n'existe pas. Merci de rï¿½essayer.");
									periodKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!periodKeyValid);

						period = formPeriod(true);
						period.setId(idPeriod);


						try {
							boolean isUpdated = daos.getPeriodiciteDAO().update(period);

							if(isUpdated) System.out.println("Modification rï¿½ussie !");
							else System.out.println("Une erreur s'est produite lors de la modification.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					case 4:
						do {
							do {
								System.out.print("ID : ");
								idPeriod = scanInt();
								if(idPeriod < 1) {
									System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idPeriod < 1);

							try {
								period = daos.getPeriodiciteDAO().getById(idPeriod);
								if(period != null) {
									System.out.println(period.getId() + "\t" + period.getLibelle());
									periodKeyValid = true;
								}
								else {
									System.out.println("La pï¿½riode que vous voulez supprimer n'existe pas. Merci de rï¿½essayer.");
									periodKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!periodKeyValid);

						try {
							boolean isDeleted = daos.getPeriodiciteDAO().delete(period);

							if(isDeleted) System.out.println("Suppresion rï¿½ussi !");
							else System.out.println("Une errerur s'est produite lors de la suppresion.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					default:
						System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
						repeat = true;
					}
				} while(repeat);
				repeat = false;
				break;
				/* |----------------------------------| *
				 * |-------------- REVUE -------------| *
				 * |----------------------------------| */
			case 4: // Revue
				do {
					optionChoice = optionChoice();
					Revue revue = new Revue();
					int idRevue;
					boolean revueKeyValid = false;
					switch(optionChoice) {
					case 1: // Lire Revue
						int readRevueChoice;
						do {
							repeat = false;
							readRevueChoice = readRevueChoice();

							if(readRevueChoice == 1) { // Lire tout
								try {
									List<Revue> revues = daos.getRevueDAO().getAll();
									if(!revues.isEmpty()) {
										revues.forEach(revueI -> {
											System.out.println(revueI.getId_revue() + "\t" + revueI.getTitre() + "\t" + revueI.getDescription() + "\t" + revueI.getTarif_numero() + "\t" + revueI.getVisuel() + "\t" + revueI.getId_periodicite()); 
										});
									}
									else {
										System.out.println("\"Revue\" ne contient aucune donnï¿½es.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR : " + e.getMessage());
									e.printStackTrace();
								}
								repeat = false;
							}
							else if(readRevueChoice == 2) { // Lire par ID
								do {
									System.out.print("ID : ");
									idRevue = scanInt();
									if(idRevue < 1) {
										System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
									}
								} while(idRevue < 1);

								try {
									revue = daos.getRevueDAO().getById(idRevue);
									if(revue != null) {
										System.out.println(revue.getId_revue() + "\t" + revue.getTitre() + "\t" + revue.getDescription() + "\t" + revue.getTarif_numero() + "\t" + revue.getVisuel() + "\t" + revue.getId_periodicite());
									}
									else {
										System.out.println("Il n'existe pas de revue pour cette clï¿½.");
									}
								} catch (Exception e) {
									System.out.println("ERREUR :" + e.getMessage());
									e.printStackTrace();
								}
								repeat = false;
							}
							else {
								System.out.println("Vous devez choisir entre l'option 1 ou 2.");
								repeat = true;
							}
						} while(repeat);
						repeat = false;
						break;

					case 2: // ajouter une revue

						revue = formRevue(false);

						try {
							boolean isCreated = daos.getRevueDAO().create(revue);

							if(isCreated) System.out.println("Ajout d'une revue rï¿½ussi !");
							else System.out.println("Une erreur est survenue lors de l'ajout d'une revue.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}

						repeat = false;
						break;
					case 3: // modifier revue
						do {
							do {
								System.out.print("ID : ");
								idRevue = scanInt();
								if(idRevue < 1) {
									System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idRevue < 1);

							try {
								revue = daos.getRevueDAO().getById(idRevue);
								if(revue != null) {
									System.out.println(revue.getId_revue() + "\t" + revue.getTitre() + "\t" + revue.getDescription() + "\t" + revue.getTarif_numero() + "\t" + revue.getVisuel() + "\t" + revue.getId_periodicite());
									revueKeyValid = true;
								}
								else {
									System.out.println("La revue que vous voulez modifier n'existe pas. Merci de rï¿½essayer.");
									revueKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!revueKeyValid);

						revue = formRevue(true);
						revue.setId_revue(idRevue);

						try {
							boolean isUpdated = daos.getRevueDAO().update(revue);

							if(isUpdated) System.out.println("Modification d'une revue rï¿½ussie !");
							else System.out.println("Une erreur est survenue lors de la modification de la revue.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}
						repeat = false;
						break;
					case 4: // Supprimer revue
						do {
							do {
								System.out.print("ID : ");
								idRevue = scanInt();
								if(idRevue < 1) {
									System.out.println("La clï¿½ primaire doit ï¿½tre supï¿½rieur ï¿½ 0.");
								}
							} while(idRevue < 1);

							try {
								revue = daos.getRevueDAO().getById(idRevue);
								if(revue != null) {
									System.out.println(revue.getId_revue() + "\t" + revue.getTitre() + "\t" + revue.getDescription() + "\t" + revue.getTarif_numero() + "\t" + revue.getVisuel() + "\t" + revue.getId_periodicite());
									revueKeyValid = true;
								}
								else {
									System.out.println("La revue que vous voulez supprimer n'existe pas. Merci de rï¿½essayer.");
									revueKeyValid = false;
								}

							} catch (Exception e) {
								System.out.println("ERREUR : " + e.getMessage());
								e.printStackTrace();
							}
						} while(!revueKeyValid);

						try {
							boolean isDeleted = daos.getRevueDAO().delete(revue);

							if(isDeleted) System.out.println("Suppresion d'une revue rï¿½ussi !");
							else System.out.println("Une erreur s'est produite lors de la suppresion d'une revue.");
						} catch (Exception e) {
							System.out.println("ERREUR : " + e.getMessage());
							e.printStackTrace();
						}

						repeat = false;
						break;
					default:
						System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
						repeat = true;
					}
				} while(repeat);
				repeat = false;
				break;

			case 5:
				daos = storageChoice();
				restart = false;
				repeat = true;
				break;
			default:
				System.out.println("Merci de choisir entre les choix 1, 2, 3, 4 ou 5.");
				restart = false;
				repeat = true;
			}


			while(restart) {
				System.out.print("Voulez-vous continuer ? (y/n) ");
				String choiceContinue = sc.nextLine();

				if(choiceContinue.equals("y")) {
					restart = false;
					repeat = true;
				}
				else if(choiceContinue.equals("n")) {
					restart = false;
					repeat = false;
				}
				else {
					System.out.println("Vous devez taper y ou n.");
					restart = true;
				}
			}
		} while(repeat);
	}

	/* ---------------------------------------------------------------------------------------------
	 * 										METHODES
	   --------------------------------------------------------------------------------------------- */

	public static int scanInt() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre entier valide.\n", input);
			System.out.print(">> ");
		}
		int nb = sc.nextInt();
		sc.nextLine();
		return nb;
	}

	public static int storageChoiceForm() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Type de stockage que vous voulez utiliser : ");
		System.out.println("1. Base de donnï¿½e\n"
				+ "2. Liste en mï¿½moire\n"
				+ "3. Quitter");
		System.out.print(">> ");

		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}

		return sc.nextInt();
	}

	public static DAOFactory storageChoice() {
		int storageChoice = 0;
		do {
			storageChoice = storageChoiceForm();
			switch(storageChoice) {
			case 1:
				return DAOFactory.getDAOFactory(Persistance.MySQL);
			case 2:
				return DAOFactory.getDAOFactory(Persistance.ListeMemoire);
			case 3:
				System.out.println("Au revoir...");
				System.exit(1);
			default:
				System.out.println("Merci de choisir 1, 2 ou 3.");
			}
		} while(true);
	}

	public static int dataChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Choix de la table :");
		System.out.println("1. Abonnement\n"
				+ "2. Client\n"
				+ "3. Periodicite\n"
				+ "4. Revue\n"
				+ "\n"
				+ "5. Changer le type de stockage");
		System.out.print(">> ");

		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}

		return sc.nextInt();
	}

	public static int optionChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Que voulez-vous faire ?");
		System.out.println("1. Lire des donnï¿½es\n"
				+ "2. Ajouter des donnï¿½es\n"
				+ "3. Modifier des donnï¿½es\n"
				+ "4. Supprimer des donnï¿½es");
		System.out.print(">> ");

		while(!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}
		return sc.nextInt();
	}

	public static int readAboChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Lire tout\n"
				+ "2. Lire en fonction de l'ID\n"
				+ "3. Lire en fonction de \"Date dï¿½but\"\n"
				+ "4. Lire en fonction de \"Date fin\"\n");
		System.out.print(">> ");

		while(!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}
		return sc.nextInt();
	}

	public static int readClientChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Lire tout\n"
				+ "2. Lire en fonction de l'ID\n"
				+ "3. Lire en fonction du nom et du prï¿½nom.");
		System.out.print(">> ");

		while(!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}
		return sc.nextInt();
	}

	public static int readPeriodChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Lire tout\n"
				+ "2. Lire en fonction de l'ID");
		System.out.print(">> ");

		while(!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}
		return sc.nextInt();
	}

	public static int readRevueChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Lire tout\n"
				+ "2. Lire en fonction de l'ID");
		System.out.print(">> ");

		while(!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}
		return sc.nextInt();
	}

	public static Abonnement formAbonnement(boolean update) {
		System.out.println("ABONNEMENT");
		System.out.println("----------");
		if(update) {
			System.out.println("Veuillez indiquer les donnï¿½es ï¿½ modifier :");
			System.out.println();
		}

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Scanner sc = new Scanner(System.in);



		// ------------------------------
		// DATE DEBUT -------------------
		// ------------------------------
		String dateDebut;
		do {
			System.out.print("Date de dï¿½but d'abonnement (JJ/MM/AAAA) : ");
			dateDebut = sc.nextLine();
			System.out.println();
			if(!dateDebut.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
				System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
			}
		} while(!dateDebut.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$"));

		LocalDate dateDebutConvert = LocalDate.parse(dateDebut, format);

		// ------------------------------
		// DATE FIN ---------------------
		// ------------------------------
		String dateFin;
		do {
			System.out.print("Date de fin d'abonnement (JJ/MM/AAAA) : ");
			dateFin = sc.nextLine();
			System.out.println();
			if(!dateFin.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
				System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
			}
		} while(!dateFin.matches("^^\\d{2}\\/\\d{2}\\/\\d{4}$"));

		LocalDate dateFinConvert = LocalDate.parse(dateFin, format);

		return new Abonnement(0, 0, dateDebutConvert, dateFinConvert);
	}

	public static Client formClient(boolean update) {
		Scanner sc = new Scanner(System.in);

		if(update) {
			System.out.println("Modification :");
			System.out.println("--------------");
			System.out.println("Veuillez indiquer les donnï¿½es ï¿½ modifier.");
			System.out.println();
		}

		// Nom client
		System.out.print("Nom du client : ");
		String nom = sc.nextLine();
		System.out.println();

		// Prï¿½nom client
		System.out.print("Prï¿½nom du client : ");
		String prenom = sc.nextLine();
		System.out.println();

		// Numï¿½ro rue
		System.out.print("Numï¿½ro de rue : ");
		String no_rue = sc.nextLine();
		System.out.println();

		// Voie
		System.out.print("Voie : ");
		String voie = sc.nextLine();
		System.out.println();

		// Code postal
		System.out.print("Code postal : ");
		String codePostal = sc.nextLine();
		System.out.println();

		// Code postal
		System.out.print("Ville : ");
		String ville = sc.nextLine();
		System.out.println();

		// Pays
		System.out.print("Pays : ");
		String pays = sc.nextLine();
		System.out.println();

		return new Client(0, nom, prenom, no_rue, voie, codePostal, ville, pays);
	}

	public static Periodicite formPeriod(boolean update) {
		Scanner sc = new Scanner(System.in);

		if(update) {
			System.out.println("Modification :");
			System.out.println("--------------");
			System.out.println("Veuillez indiquer les donnï¿½es de la pï¿½riodicitï¿½ ï¿½ modifier.");
			System.out.println();
		}

		System.out.print("Libelle : ");
		String libelle = sc.nextLine();
		System.out.println();

		return new Periodicite(0, libelle);
	}

	public static Revue formRevue(boolean update) {
		Scanner sc = new Scanner(System.in);

		if(update) {
			System.out.println("Modification :");
			System.out.println("--------------");
			System.out.println("Veuillez indiquer les donnï¿½es de la revue ï¿½ modifier.");
			System.out.println();
		}

		System.out.print("Titre : ");
		String titre = sc.nextLine();
		System.out.println();

		System.out.print("Description : ");
		String description = sc.nextLine();
		System.out.println();

		System.out.print("Tarif : ");
		while (!sc.hasNextDouble()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre a virgule valide.\n", input);
			System.out.print("Tarif : ");
		}

		double tarif = sc.nextDouble();
		System.out.println();
		sc.nextLine();

		System.out.print("Visuel : ");
		String visuel = sc.nextLine();
		System.out.println();

		System.out.print("ID Periodicite : ");
		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print("ID Periodicite : ");
		}

		int idPeriod = sc.nextInt();
		System.out.println();
		sc.nextLine();

		return new Revue(0, titre, description, tarif, visuel, idPeriod);
	}
}