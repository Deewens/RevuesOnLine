package fr.iutmetz.td2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Abonnement {
	private int id_client;
	private int id_revue;
	private LocalDate date_debut;
	private LocalDate date_fin;
	
	public Abonnement() {} // Constructeur par défaut
	
	public Abonnement(int id_client, int id_revue, LocalDate date_debut, LocalDate date_fin) {
		this.id_client = id_client;
		this.id_revue = id_revue;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public int getId_revue() {
		return id_revue;
	}

	public void setId_revue(int id_revue) {
		this.id_revue = id_revue;
	}

	public LocalDate getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(LocalDate date_debut) {
		this.date_debut = date_debut;
	}

	public LocalDate getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(LocalDate date_fin) {
		this.date_fin = date_fin;
	}
}
