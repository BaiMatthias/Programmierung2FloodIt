/**
 * Diese Klasse repraesentiert das Spielfeld, mit dem der Spieler interagiert. Jedes Feld auf dem Spielfeld besteht aus einem Zelle-Objekt. 
 * Da andere Klassen das Spielfeld manipulieren können, stellt das Spielfeld Methoden bereit, um Zellen abzufragen oder deren Farbe zu veraendern
 * @author Matze & Ali
 */

package application;

import java.awt.Color;

public class Spielbrett {

	private Zelle[][] felder;

	/**
	 * Erstellt ein neues Spielbrett-Objekt
	 * 
	 * @param groesse
	 *            Gibt die Groesse des zu erstellenden Objekts an, hierbei handelt
	 *            es sich um ein groesse*groesse-Feld
	 * @param farben
	 *            Die Farben, die fuer das Spielbrett verwendet werden koennen
	 */
	public Spielbrett(int groesse, int farben) {
		felder = new Zelle[groesse][groesse];
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[i].length; j++) {
				felder[i][j] = new Zelle(j, i, farben);
			}
		}
	}

	/**
	 * Erstellt eine Kopie eines Spielbretts auf Grundlage eines uebergebenen
	 * Spielbretts
	 * 
	 * @param spielbrett
	 *            Das Spielbrett, das kopiert werden soll
	 */
	public Spielbrett(Spielbrett spielbrett) {
		this.felder = new Zelle[spielbrett.felder.length][spielbrett.felder.length];
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[i].length; j++) {
				this.felder[i][j] = new Zelle(j, i, spielbrett.getZelleFarbe(i, j));
			}
		}
	}

	/**
	 * Gibt das gesamte Spielfeld zurueck
	 * 
	 * @return Das Spielfeld
	 */
	public Zelle[][] getFelder() {
		return felder;
	}

	/**
	 * Gibt die Farbe einer angegebenen Zelle zurueck
	 * 
	 * @param i
	 *            Die i-te Koordinate im Array
	 * @param j
	 *            Die j-te Koordinate im Array
	 * @return Die Farbe an der Position i,j
	 */
	public Color getZelleFarbe(int i, int j) {
		return felder[i][j].getFarbe();
	}

	/**
	 * Setzt die Farbe der Zelle an der angegebenen Position
	 * 
	 * @param i
	 *            Die i-te Koordinate im Array
	 * @param j
	 *            Die j-te Koordinate im Array
	 * @param c
	 *            Die Farbe, die die Zelle annehmen soll
	 */
	public void setZelleFarbe(int i, int j, Color c) {
		this.felder[i][j].setFarbe(c);
	}

	/**
	 * Gibt eine Zelle an einer angegebenen Position zurueck
	 * 
	 * @param i
	 *            Die i-te Koordinate im Array
	 * @param j
	 *            Die j-te Koordinate im Array
	 * @return Die Zelle an der Position i,j
	 */
	public Zelle getZelle(int i, int j) {
		return felder[i][j];
	}

}
