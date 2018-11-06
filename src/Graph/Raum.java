package Graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 
 * @author l.hofer
 *
 */
public class Raum {
	
	private int hoehe;
	private int breite;
	private int n; 		// Anzahl der Kugeln
	private double radius;
	private Set<Kante> kanten;
	private Set<Kugel> kugeln;
	
	/**
	 * 
	 * @param n
	 * @param hoehe
	 * @param breite
	 * @param radius
	 * @throws Exception
	 */
	public Raum(int n, int hoehe, int breite, double radius) throws Exception{
		
		this.n = n;
		this.radius = radius;
		this.breite = breite;
		this.hoehe = hoehe;
		
		kugeln = new HashSet<>();
		if(!verteileKugeln()) {
			throw new Exceptions.KugelException("Die Kugeln konnten nicht"
					+ " akkurat verteilt werden.");
		}
	}
	
	public int getHoehe() {
		return hoehe;
	}

	public void setHoehe(int hoehe) {
		this.hoehe = hoehe;
	}

	public int getBreite() {
		return breite;
	}

	public void setBreite(int breite) {
		this.breite = breite;
	}

	public Set<Kugel> getKugeln() {
		return kugeln;
	}



	public void setKugeln(Set<Kugel> kugeln) {
		this.kugeln = kugeln;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return Wahrheitswert, ob die Kugel innerhalb der Grenzen liegen
	 */
	private boolean istImRaum(int x, int y, double radius) {
		if(Math.round(x-radius) < 0 || Math.round(y-radius) < 0 ||
				Math.round(x+radius) > this.breite || Math.round(y+radius) > hoehe) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return Wahrheitswert, ob das Verteilen erfolgreich war
	 */
	private boolean verteileKugeln() {
		
		int versuche = 200;
		Random verteilung  = new Random(); 		// verwendet automatisch die aktuelle Systemzeit als Seed

		
		 while(kugeln.size() < this.n && versuche > 0) {
			 
			 int x = verteilung.nextInt(this.breite);
			 int y = verteilung.nextInt(this.hoehe);
			 
			 if(istImRaum(x, y, this.radius)) {
				kugeln.add(new Kugel(x, y, this.radius));
			} else {
				versuche--;
				System.out.println("x = "+x+"\t y = "+y);
			}
		 }
		 
		if(kugeln.size() == this.n) {
			return true;
		}
		
		return false;
	}
}
