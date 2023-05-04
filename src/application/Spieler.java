/**
 * Diese Klasse repraesentiert einen Spieler, der mit dem Spiel interagiert.
 * Bei der Erstellung bekommt der Spieler eine Farbe zugeteilt, die sich aber je nach Auswahl des Spielers aendert. Auch seine gemachten Zuege werden zur Darstellung
 * in der GUI  gespeichert. Fuer den Mehrspieler ist es wichtig den aktiven Spieler zu erkennen und die Felder, die dem jeweiligen Spieler gehoeren, zu zaehlen.
 * @author Matze & Ali
 */

package application;

import java.awt.Color;

import javafx.beans.property.SimpleIntegerProperty;

public class Spieler {

	private SimpleIntegerProperty zuege;
	private Color farbe;
	private boolean isAktiv;
	private SimpleIntegerProperty anzFelder;

	private Color[][] spielerFeld;

	/**
	 * Erstellt einen Spieler
	 * 
	 * @param farbe
	 *            Die Farbe, die der Spieler zu Beginn des Spiels hat
	 * @param groesse
	 *            Die Groesse des Spielfelds, die der Spieler eingestellt hat
	 * @param isZweiterSpieler
	 *            Ob der Spieler der zweite Spieler bei einem Mehrspieler-Spiel ist
	 */
	public Spieler(Color farbe, int groesse, boolean isZweiterSpieler) {
		this.farbe = farbe;
		this.zuege = new SimpleIntegerProperty();
		this.zuege.set(0);
		spielerFeld = new Color[groesse][groesse];
		anzFelder = new SimpleIntegerProperty();
		anzFelder.set(1);
		if (!isZweiterSpieler)
			spielerFeld[0][0] = farbe;
		else
			spielerFeld[groesse - 1][groesse - 1] = farbe;
	}

	/**
	 * Erstellt eine Kopie eines uebergebenen Spieler-Objekts
	 * 
	 * @param spieler
	 *            Das Spieler-Objet, das kopiert werden soll
	 */
	public Spieler(Spieler spieler) {
		this.farbe = spieler.farbe;
		this.zuege = new SimpleIntegerProperty();
		this.zuege.set(spieler.getZuege());
		this.spielerFeld = new Color[spieler.getSpielerFeld().length][spieler.getSpielerFeld().length];
		this.isAktiv = spieler.isAktiv;
		for (int i = 0; i < this.spielerFeld.length; i++)
			for (int j = 0; j < this.spielerFeld.length; j++) {
				this.spielerFeld[i][j] = spieler.spielerFeld[i][j];
			}

	}

	/**
	 * Erstellt einen Spieler, der nur ein leeres Spielfeld besitzt, als Ersatz für
	 * einen fehlenden zweiten Spieler im Einzelspieler
	 * 
	 * @param groesse
	 *            Die groesse des Spielfelds
	 */
	public Spieler(int groesse) {
		this.spielerFeld = new Color[groesse][groesse];
	}

	/**
	 * Gibt das Feld des Spielers zurueck
	 * 
	 * @return Das Feld des Spielers
	 */
	public Color[][] getSpielerFeld() {
		return spielerFeld;
	}

	/**
	 * Gibt die aktuelle Farbe des Spielers zurueck
	 * 
	 * @return Die aktuelle Farbe des Spielers
	 */
	public Color getFarbe() {
		return farbe;
	}

	/**
	 * Zaehlt die aktuelle Anzahl der Felder, die dem Spieler gehoeren, Jedes Feld,
	 * das nicht null ist, gehoert dem Spieler
	 * 
	 * @return Die Felder, die dem Spieler gehoeren
	 */
	public int getAnzahlFelder() {
		int tmpZahl = 0;
		for (int i = 0; i < this.spielerFeld.length; i++)
			for (int j = 0; j < this.spielerFeld.length; j++) {
				if (this.spielerFeld[i][j] != null) {
					tmpZahl++;

				}
			}
		this.anzFelder.set(tmpZahl);
		return this.anzFelder.get();
	}

	/**
	 * Eine Property der Felder des Spielers, um dieses an die GUI zu binden
	 * 
	 * @return Die Property der Felder des Spielers
	 */
	public SimpleIntegerProperty anzFelderProperty() {
		return this.anzFelder;
	}

	/**
	 * Gibt die Zuege des Spielers zurueck
	 * 
	 * @return Die Zuege des Spielers
	 */
	public int getZuege() {
		return this.zuege.get();
	}

	/**
	 * Setzt die Zuege des Spielers auf den uebergebenen Wert
	 * 
	 * @param i
	 *            Der Wert, auf den die Zuege gestellt werden soll
	 */
	public void setZuege(int i) {
		this.zuege.set(i);
	}

	/**
	 * Eine Property der Zuege des Spielers, um dieses an die GUI zu binden
	 * 
	 * @return Die Property der Zuege des Spielers
	 */
	public SimpleIntegerProperty zuegeProperty() {
		return this.zuege;
	}

	/**
	 * Gibt zurueck ob der Spieler gerade aktiv ist
	 * 
	 * @return Ob der Spieler gerade aktiv ist, bei true ist er aktiv, bei false
	 *         nicht
	 */
	public boolean isAktiv() {
		return isAktiv;
	}

	/**
	 * Setzt den Wert, ob der Spieler gerade aktiv ist, auf den uebergebenen Wert
	 * 
	 * @param isAktiv
	 *            Der boolsche Wert, auf den das Attribut gesetzt werden soll
	 */
	public void setAktiv(boolean isAktiv) {
		this.isAktiv = isAktiv;
	}

	/**
	 * macht einen Zug des Spielers, dabei veraendert sich seine Farbe auf die der
	 * uebergebenen Farbe und seine gemachten Zuege erhoehen sich um 1
	 * 
	 * @param c
	 *            Die Farbe, die der Spieler ausgewaehlt hat
	 */
	public void macheZug(Color c) {
		this.farbe = c;
		this.zuege.set(this.zuege.get() + 1);
	}

	/**
	 * Fuegt die angegebene Zelle dem Spieler hinzu, d.h. dem Spieler wird die Zelle
	 * zu seinem Feld hinzugefuegt
	 * 
	 * @param zelle
	 *            Die Zelle, die der Spieler zu seinem Feld dazu erhaelt
	 */
	public void setFeld(Zelle zelle) {
		int x = (int) zelle.getPunkt().getX();
		int y = (int) zelle.getPunkt().getY();
		this.spielerFeld[y][x] = zelle.getFarbe();
	}

	/**
	 * Gibt die Farbe aus dem Feld auf den angegebenen Indexes zurueck
	 * 
	 * @param i
	 *            Der i-te Index
	 * @param j
	 *            Der j-te Index im i-ten Array
	 * @return Die Farbe auf der Position i,j
	 */
	public Color getFeld(int i, int j) {
		return this.spielerFeld[i][j];
	}

}
