package fr.iutmetz.td3.pojo;

public class Periodicite {
	private int id;
	private String libelle;
	
	public Periodicite() {}

	
	public Periodicite(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Periodicite [id=" + id + ", libelle=" + libelle + "]";
	}
}
