package fr.iutmetz.td2;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		DAOFactory daos = null;
		String persistance = "Persistance inconnue";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println("Bienvenue. Ici vous pouvez gérer vos données stockés dans différents système.\n"
				+ " Ce mini-programme en ligne de commandes va vous permettre d'ajouter, supprimer, modifier et voir les données d'une base de données et d'une Liste en mémoire.");
		System.out.println("Instructions d'utilisation :");
		System.out.println("Pour naviguer dans le programme, vous devez indiquer le numéro du menu auquel vous souhaitez accéder et appuyer sur entrer.");
		System.out.println();

		int storageChoice = 0;
		int dataChoice = 0;
		int optionChoice = 0;

		int readAboChoice = 0;

		boolean repeat;

		/* ---------------------------
		 * Choix du mode de stockage
		 * --------------------------- */

		do {
			storageChoice = storageChoice();
			switch(storageChoice) {
			case 1:
				daos = DAOFactory.getDAOFactory(Persistance.MySQL);
				persistance = "[MySQL]";
				repeat = false;
				break;
			case 2:
				daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
				persistance = "[ListeMemoire]";
				repeat = false;
				break;
			case 3:
				System.out.println("Au revoir...");
				System.exit(1);
			default:
				System.out.println("Merci de choisir 1, 2 ou 3.");
				repeat = true;
			}
		} while(repeat);

		/* ---------------------------
		 * Choix des données
		 * --------------------------- */
		do {
			dataChoice = dataChoice();
			switch(dataChoice) {
			case 1: // Abonnement
				do {
					Abonnement abo = new Abonnement();
					optionChoice = optionChoice();
					switch(optionChoice) {
					case 1: // Lire abonnement
						do {
							repeat = false;
							readAboChoice = readAboChoice();
							if(readAboChoice == 1) {
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
										System.out.println("\"Abonnement\" ne contient aucune données.");
									}
								} catch (Exception e) {
									System.out.println("Erreur de lecture : " + e.getMessage());
								}
							}
							else if(readAboChoice == 2) {
								System.out.print("ID Client : ");
								while (!sc.hasNextInt()) {
									String input = sc.next();
									System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
									System.out.print(">> ");
								}
								int idClient = sc.nextInt();
								sc.nextLine();
								
								System.out.print("ID Revue : ");
								while (!sc.hasNextInt()) {
									String input = sc.next();
									System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
									System.out.print(">> ");
								}
								int idRevue = sc.nextInt();
								sc.nextLine();
								
								try {
									Abonnement abos = daos.getAbonnementDAO().getByIds(idClient, idRevue);
									System.out.println(abos.getId_client() + "\t" + abos.getId_revue() + "\t" + abos.getDate_debut() + "\t" + abos.getDate_fin());

								} catch (Exception e) {
									System.out.println(persistance + "Erreur \"Abonnement\" : " + e.getMessage());
								}
							}
							else if(readAboChoice == 3) {
								String dateDebut;
								do {
									System.out.print("Date de début d'abonnement (JJ/MM/AAAA) : ");
									dateDebut = sc.nextLine();
									System.out.println();
									if(!dateDebut.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
										System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
									}
								} while(!dateDebut.matches("^^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$"));
								
								LocalDate dateDebutConvert = LocalDate.parse(dateDebut, format);
								
								try {
									List<Abonnement> abos = daos.getAbonnementDAO().getByDate_debut(dateDebutConvert);
									if(!abos.isEmpty()) {
										abos.forEach(aboI -> {
											System.out.println(aboI.getId_client()); 
											System.out.println(aboI.getId_revue());
											System.out.println(format.format(aboI.getDate_debut()));
											System.out.println(format.format(aboI.getDate_fin()));
										});
									}
									else {
										System.out.println("La liste \"Abonnement\" ne contient aucune données pour cette date.");
									}

								} catch(Exception e) {
									System.out.println("Erreur : " + e.getMessage());
								}
							}
							else if(readAboChoice == 4) {
								String dateFin;
								do {
									System.out.print("Date de début d'abonnement (JJ/MM/AAAA) : ");
									dateFin = sc.nextLine();
									System.out.println();
									if(!dateFin.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
										System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
									}
								} while(!dateFin.matches("^^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$"));
								
								LocalDate dateFinConvert = LocalDate.parse(dateFin, format);
								
								try {
									List<Abonnement> abos = daos.getAbonnementDAO().getByDate_debut(dateFinConvert);
									if(!abos.isEmpty()) {
										abos.forEach(aboI -> {
											System.out.println(aboI.getId_client()); 
											System.out.println(aboI.getId_revue());
											System.out.println(format.format(aboI.getDate_debut()));
											System.out.println(format.format(aboI.getDate_fin()));
										});
									}
									else {
										System.out.println("La liste \"Abonnement\" ne contient aucune données pour cette date.");
									}

								} catch(Exception e) {
									System.out.println("Erreur : " + e.getMessage());
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
						abo = formAbonnement();
						try {
							boolean ok = daos.getAbonnementDAO().create(abo);
							if(!ok) {
								System.out.println("Le client n°" + abo.getId_client() + " que vous avez choisi est déjà abonné à la revue n°" + abo.getId_revue() + ".");
							}
						} catch(Exception e) {
							System.out.println("Erreur ajout \"Abonnement\" : " + e.getMessage());
						}
						repeat = false;
						break;
						
					case 3: // Modifier abonnement
						abo = formAbonnement();
						try {
							boolean update = daos.getAbonnementDAO().update(abo);
							if(!update) System.out.println("Vous ne pouvez pas modifier un abonnement qui n'existe pas.");
						} catch (Exception e) {
							System.out.println("Erreur update \"Abonnement\" : " + e.getMessage());
						}
						repeat = false;
						break;
						
					case 4:
						System.out.print("ID Client : ");
						while (!sc.hasNextInt()) {
							String input = sc.next();
							System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
							System.out.print("ID Client : ");
						}
						int id_client = sc.nextInt();
						System.out.println();
						sc.nextLine();

						System.out.print("ID Revue : ");
						while (!sc.hasNextInt()) {
							String input = sc.next();
							System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
							System.out.print("ID Revue : ");
						}
						int id_revue = sc.nextInt();
						System.out.println();
						sc.nextLine();
						abo.setId_client(id_client);
						abo.setId_revue(id_revue);

						try {
							boolean delete = daos.getAbonnementDAO().delete(abo);
							if(!delete) {
								System.out.println("Vous ne pouvez pas supprimer un abonnement qui n'existe pas.");
							}
						} catch (Exception e) {
							System.out.println("Erreur suppresion \"Abonnement\" : " + e.getMessage());
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
			case 2: // Client
				optionChoice = optionChoice();
				switch(optionChoice) {
				case 1: // Lire client
					int readClientChoice = 0;
					do {
						readClientChoice = readClientChoice();
						if(readClientChoice == 1) {
							try {
								List<Client> clients = daos.getClientDAO().getAll();
								if(!clients.isEmpty()) {
									clients.forEach(clientI -> {
										System.out.println(clientI.getId_client() + "\t" + clientI.getNom() + "\t" + clientI.getPrenom() + "\t" + clientI.getNo_rue()
										+ "\t" + clientI.getVoie() + "\t" + clientI.getVille() + "\t" + clientI.getPays()); 
									});
								}
								else {
									System.out.println("\"Client\" ne contient aucune données.");
								}
							} catch (Exception e) {
								System.out.println("Erreur lecture \"Client\" : " + e.getMessage());
							}
							repeat = false;
						}
						else if(readClientChoice == 2) {
							System.out.print("ID : ");
							while (!sc.hasNextInt()) {
								String input = sc.next();
								System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
								System.out.print("ID : ");
							}
							int id = sc.nextInt();
							System.out.println();
							sc.nextLine();
							
							try {
								Client client = daos.getClientDAO().getById(id);
								System.out.println(client.getId_client() + "\t" + client.getNom() + "\t" + client.getPrenom() + "\t" + client.getNo_rue()
								+ "\t" + client.getVoie() + "\t" + client.getVille() + "\t" + client.getPays());
							} catch (Exception e) {
								System.out.println("Erreur lecture par ID \"Client\" : " + e.getMessage());
							}
							
							repeat = false;
						}
						else if(readClientChoice == 3) {
							System.out.print("Nom :");
							String nom = sc.nextLine();
							
							System.out.print("Prenom :");
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
									System.out.println("\"Client\" ne contient aucune données.");
								}
								daos.getClientDAO().getByNomPrenom(nom, prenom);
							} catch (Exception e) {
								System.out.println("Erreur lecture par nom et prénom : " + e.getMessage());
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
					
				case 2: // ajouter client
					Client client = formClient();
					
					try {
						daos.getClientDAO().create(client);
					} catch (Exception e) {
						System.out.println("Erreur ajout \"Client\" : " + e.getMessage());
					}
					repeat = false;
					break;
				case 3: // modifier client
					System.out.println("Modifier client");
					repeat = false;
					break;
				case 4:
					System.out.println("Supprimer client");
					repeat = false;
					break;
				default:
					System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
					repeat = true;
				}
				repeat = false;
				break;
			case 3: // Periodicite
				optionChoice = optionChoice();
				switch(optionChoice) {
				case 1: // Lire periodicite

					repeat = false;
					break;
					
				case 2: // ajouter periodicite
					System.out.println("Ajouter periodicite");
					repeat = false;
					break;
				case 3: // modifier period
					System.out.println("Modifier period");
					repeat = false;
					break;
				case 4:
					System.out.println("Supprimer period");
					repeat = false;
					break;
				default:
					System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
					repeat = true;
				}
				repeat = false;
				break;
			case 4: // Revue
				optionChoice = optionChoice();
				switch(optionChoice) {
				case 1: // Lire revue
					System.out.println("Lire revue");
					repeat = false;
					break;
					
				case 2: // ajouter revue
					System.out.println("Ajouter revue");
					repeat = false;
					break;
				case 3: // modifier revue
					System.out.println("Modifier revue");
					repeat = false;
					break;
				case 4: // Supprimer period
					System.out.println("Supprimer revue");
					repeat = false;
					break;
				default:
					System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
					repeat = true;
				}
				repeat = false;
				break;
			default:
				System.out.println("Merci de choisir entre les choix 1, 2, 3 ou 4.");
				repeat = true;	
			}
			
			repeat = false;
			
			boolean restart = false;
			do {
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
			} while(restart);
		} while(repeat);
	}
	
	/* ---------
	 * METHODES
	   --------- */
	
	public static int storageChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Type de stockage que vous voulez utiliser : ");
		System.out.println("1. Base de donnée\n"
				+ "2. Liste en mémoire\n"
				+ "3. Quitter");
		System.out.print(">> ");

		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}

		return sc.nextInt();
	}

	public static int dataChoice() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Choix de la table :");
		System.out.println("1. Abonnement\n"
				+ "2. Client\n"
				+ "3. Periodicite\n"
				+ "4. Revue");
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
		System.out.println("1. Lire des données\n"
				+ "2. Ajouter des données\n"
				+ "3. Modifier des données\n"
				+ "4. Supprimer des données");
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
				+ "3. Lire en fonction de \"Date début\"\n"
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
				+ "3. Lire en fonction du nom et du prénom.");
		System.out.print(">> ");

		while(!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print(">> ");
		}
		return sc.nextInt();
	}
	
	public static Abonnement formAbonnement() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Scanner sc = new Scanner(System.in);
		
		System.out.print("ID Client : ");
		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print("ID Client : ");
		}
		int id_client = sc.nextInt();
		System.out.println();
		sc.nextLine();

		System.out.print("ID Revue : ");
		while (!sc.hasNextInt()) {
			String input = sc.next();
			System.out.printf("\"%s\" n'est pas un nombre valide.\n", input);
			System.out.print("ID Revue : ");
		}
		int id_revue = sc.nextInt();
		System.out.println();
		sc.nextLine();

		// ------------------------------
		// DATE DEBUT -------------------
		// ------------------------------
		String dateDebut;
		do {
			System.out.print("Date de début d'abonnement (JJ/MM/AAAA) : ");
			dateDebut = sc.nextLine();
			System.out.println();
			if(!dateDebut.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
				System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
			}
		} while(!dateDebut.matches("^^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$"));
		
		LocalDate dateDebutConvert = LocalDate.parse(dateDebut, format);
		
		// ------------------------------
		// DATE FIN ---------------------
		// ------------------------------
		String dateFin;
		do {
			System.out.print("Date de début d'abonnement (JJ/MM/AAAA) : ");
			dateFin = sc.nextLine();
			System.out.println();
			if(!dateFin.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$")) {
				System.out.println("Vous devez entrer une date au format JJ/MM/AAAA ! Veuillez recommencer.");
			}
		} while(!dateFin.matches("^^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$"));
		
		LocalDate dateFinConvert = LocalDate.parse(dateFin, format);
		
		return new Abonnement(id_client, id_revue, dateDebutConvert, dateFinConvert);
	}
	
	public static Client formClient() {
		Scanner sc = new Scanner(System.in);

		// Nom client
		System.out.print("Nom du client : ");
		String nom = sc.nextLine();
		System.out.println();

		// Prénom client
		System.out.print("Prénom du client : ");
		String prenom = sc.nextLine();
		System.out.println();

		// Numéro rue
		System.out.print("Numéro de rue : ");
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
}