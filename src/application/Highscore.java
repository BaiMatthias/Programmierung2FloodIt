/**
 * Diese Klasse repraesentiert einen Highscore, dieser wird erstellt, wenn der Spieler im Einzelspieler das Spiel gewinnt
 * Diese Klasse beinhaltet die vom Spieler gemachten Zuege sowie die eingestellte Feldgroesse
 * @author Matze & Ali
 */

package application;

import javafx.beans.property.SimpleIntegerProperty;

public class Highscore {

	private SimpleIntegerProperty zuege;
	private SimpleIntegerProperty feldgroesse;

	/**
	 * Erstellt ein neues Highscore-Objekt
	 * 
	 * @param zuegeAnz
	 *            die Zuege, die der Spieler zur Beendigung des Spiels gebraucht hat
	 * @param felder
	 *            die Feldgroesse, die der Spieler vor Beginn des Spiels eingestellt
	 *            hat
	 */
	public Highscore(int zuegeAnz, int felder) {
		this.zuege = new SimpleIntegerProperty(zuegeAnz);
		this.feldgroesse = new SimpleIntegerProperty(felder);
	}

	/**
	 * Fragt die Feldgroesse des Highscore-Objekts ab
	 * 
	 * @return die gespeicherte Feldgroesse
	 */
	public int getFeldgroesse() {
		return this.feldgroesse.get();
	}

	/**
	 * Fragt die gemachten Zuege des Spielers des Highscore-Objekts ab
	 * 
	 * @return die gemachten Zuege
	 */
	public int getZuege() {
		return this.zuege.get();
	}

}
