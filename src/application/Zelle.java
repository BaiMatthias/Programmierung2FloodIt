/**
 * Diese Klasse repraesentiert eine Zelle, damit ist ein Feld im Spielfeld gemeint. Die Zellen erhalten jeweils eine Position sowie eine zufaellige Zahl bei der Erstellung.
 * Des Weiteren kann eine Zelle ihre Farbe aendern, wenn der Spieler dies anstoesst. Für weitere Methoden in anderen Klassen muss außerdem bekannt sein, ob die Zelle schon einmal 
 * besucht wurde, d.h. ob sie bereits zwecks Farbe ueberprueft wurde
 * @author Matze & Ali
 */

package application;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Zelle {

	private Point punkt;
	private Color farbeZelle;
	private boolean besucht;

	Random rnd = new Random();

	/**
	 * Erstellt eine neue Zelle
	 * 
	 * @param x
	 *            Die X-Koordinate, auf der sich die Zelle befinden soll
	 * @param y
	 *            Die Y-Koordinate, auf der sich die Zelle befinden soll
	 * @param farben
	 *            Die moeglichen Farben, die eine Zelle annehmen kann
	 */
	public Zelle(int x, int y, int farben) {
		this.punkt = new Point(x, y);
		int index = rnd.nextInt(farben);
		this.besucht = false;
		this.farbeZelle = Farbensammlung.getFarben()[index];
	}

	/**
	 * Erstellt eine neue Zelle, mit anderen Parametern
	 * 
	 * @param x
	 *            Die X-Koordinate, auf der sich die Zelle befinden soll
	 * @param y
	 *            Die Y-Koordinate, auf der sich die Zelle befinden soll
	 * @param c
	 *            Die Farbe, die die Zelle annehmen soll
	 */
	public Zelle(int x, int y, Color c) {
		this.punkt = new Point(x, y);
		this.farbeZelle = c;
		this.besucht = false;
	}

	/**
	 * Gibt die Farbe der Zelle zurueck
	 * 
	 * @return Die Farbe der Zelle
	 */
	public Color getFarbe() {
		return farbeZelle;
	}

	/**
	 * Aendert die Farbe der Zelle auf den uebergebenen Wert
	 * 
	 * @param farbe
	 *            Zelle Die Farbe, die die Zelle annehmen soll
	 */
	public void setFarbe(Color farbeZelle) {
		this.farbeZelle = farbeZelle;
	}

	/**
	 * Gibt die Koordinaten der Zelle zurueck
	 * 
	 * @return Die Koordinaten der Zelle
	 */
	public Point getPunkt() {
		return this.punkt;
	}

	/**
	 * Aendert den Status, ob die Zelle besucht wurde oder noch unbesucht ist
	 * 
	 * @param besucht
	 *            Der Wert, der angibt, ob die Zelle besucht(true) oder
	 *            unbesucht(false) ist
	 */
	public void setBesucht(boolean besucht) {
		this.besucht = besucht;
	}

	/**
	 * Gibt den Besucht-Status der Zelle zurueck
	 * 
	 * @return Der aktuelle Wert, der angibt, ob die Zelle besucht wurde oder noch
	 *         unbesucht ist
	 */
	public boolean getBesucht() {
		return this.besucht;
	}

}
