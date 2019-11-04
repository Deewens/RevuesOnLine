package cpoa.projet.pojo;

public class RevueBuffer {
	private Revue revue;
	private Periodicite period;
	private int nbCurrentAbo;
	
	public RevueBuffer(Revue revue, Periodicite period, int nbCurrentAbo) {
		this.revue = revue;
		this.period = period;
		this.nbCurrentAbo = nbCurrentAbo;
	}
	
	public Revue getRevue() {
		return revue;
	}
	public void setRevue(Revue revue) {
		this.revue = revue;
	}
	
	public int getNbCurrentAbo() {
		return nbCurrentAbo;
	}

	public void setNbCurrentAbo(int nbCurrentAbo) {
		this.nbCurrentAbo = nbCurrentAbo;
	}

	public Periodicite getPeriod() {
		return period;
	}
	public void setPeriod(Periodicite period) {
		this.period = period;
	}
	
}
