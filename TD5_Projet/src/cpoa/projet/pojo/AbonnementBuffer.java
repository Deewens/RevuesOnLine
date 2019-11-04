package cpoa.projet.pojo;

import java.time.LocalDate;

public class AbonnementBuffer {
	private Abonnement abo;
	private Client client;
	private Revue revue;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	
	public AbonnementBuffer(Abonnement abo, Client client, Revue revue, LocalDate dateDebut, LocalDate dateFin) {
		this.abo = abo;
		this.client = client;
		this.revue = revue;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	public Abonnement getAbo() {
		return abo;
	}
	public void setAbo(Abonnement abo) {
		this.abo = abo;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Revue getRevue() {
		return revue;
	}
	public void setRevue(Revue revue) {
		this.revue = revue;
	}
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	public LocalDate getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
}
