package fr.iutmetz.td3.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Abonnement {
	private int id_client;
	private int id_revue;
	private LocalDate date_debut;
	private LocalDate date_fin;
	
	public Abonnement() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse("01/01/1900", format);
		LocalDate dateFin = LocalDate.parse("31/12/1900", format);
		
		this.id_client = 0;
		this.id_revue = 0;
		this.date_debut = dateDebut;
		this.date_fin = dateFin;
	} // Constructeur par défaut
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_debut == null) ? 0 : date_debut.hashCode());
		result = prime * result + ((date_fin == null) ? 0 : date_fin.hashCode());
		result = prime * result + id_client;
		result = prime * result + id_revue;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Abonnement other = (Abonnement) obj;
		if (date_debut == null) {
			if (other.date_debut != null) {
				return false;
			}
		} else if (!date_debut.equals(other.date_debut)) {
			return false;
		}
		if (date_fin == null) {
			if (other.date_fin != null) {
				return false;
			}
		} else if (!date_fin.equals(other.date_fin)) {
			return false;
		}
		if (id_client != other.id_client) {
			return false;
		}
		if (id_revue != other.id_revue) {
			return false;
		}
		return true;
	}
}
