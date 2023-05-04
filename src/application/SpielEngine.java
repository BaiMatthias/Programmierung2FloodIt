/**
 * Diese Klasse repraesentiert die Engine des Spiels, die Zugriff auf die Spieler und das Spielfeld hat und die Interaktionen zwischen diesen steuert
 * Sie beherbergt auﬂerdem die gesamte Spiellogik
 * @author Matze & Ali
 */

package application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javafx.beans.property.SimpleIntegerProperty;

public class SpielEngine {

	private Spielbrett spielbrett;
	private Spieler spieler;
	private Spieler spieler2;
	private boolean isMehrspieler;
	private List<Highscore> highscoreList;
	private List<Zelle> tmpZellList;

	// Attribute fuer Zug zurueck
	private Spieler letzterSpielerstand;
	private Spielbrett letztesSpielbrett;

	/**
	 * Erstellt eine neue Engine, hier werden auch Objekte des Spielfelds und der
	 * Spieler erstellt, sollte es sich um einen Mehrspieler handeln, werden 2
	 * Spieler erstellt, wobei ein Spieler zufaellig der aktive Spieler wird
	 * 
	 * @param groesse
	 *            Die groesse, die der Spieler eingestellt hat, wird an das
	 *            Spielbrett uebergeben
	 * @param farben
	 *            Die Farben, die zur Verfuegung stehen und vom Spieler in der GUI
	 *            eingestellt wurden
	 * @param isMehrspieler
	 *            Der boolscher Wert, der angibt, ob es sich um ein
	 *            Mehrspieler-Spiel handelt, bei true ist es Mehrspieler-Spiel, bei
	 *            false nicht
	 */
	public SpielEngine(int groesse, int farben, boolean isMehrspieler) {
		this.spielbrett = new Spielbrett(groesse, farben);
		this.spieler = new Spieler(this.spielbrett.getFelder()[0][0].getFarbe(), groesse, false);
		this.tmpZellList = new ArrayList<Zelle>();
		this.highscoreList = new ArrayList<Highscore>();
		this.isMehrspieler = isMehrspieler;
		if (this.isMehrspieler) {
			this.spieler2 = new Spieler(this.spielbrett.getFelder()[groesse - 1][groesse - 1].getFarbe(), groesse,
					true);
			this.sucheFelder(spieler.getFarbe());
			this.sucheFelder(spieler2.getFarbe());
			wuerfelZugAus();
		} else {
			spieler.setAktiv(true);
			this.spieler2 = new Spieler(groesse);
			this.sucheFelder(spieler.getFarbe());
			this.spieler.setZuege(0);

		}

	}

	/**
	 * Gibt das Spielfeld der Engine zurueck
	 * 
	 * @return Das Spielbrett der Engine
	 */
	public Spielbrett getSpielbrett() {
		return this.spielbrett;
	}

	/**
	 * Gibt die Groesse des Spielfelds an
	 * 
	 * @return Die Groesse des Spielfelds
	 */
	public int getGroesse() {
		return this.spielbrett.getFelder().length;
	}

	/**
	 * Gibt die gesamte Liste mit allen Highscores zurueck
	 * 
	 * @return Die Liste, die die Highscores enthaelt
	 */
	public List<Highscore> getHighscoreList() {
		return this.highscoreList;
	}

	/**
	 * Eine Property, die die Zuege des Spielers enthaelt, um dieses an die GUI zu
	 * binden
	 * 
	 * @return Die Property, die die Zuege des Spielers enthaelt
	 */
	public SimpleIntegerProperty getZuegeSpieler() {
		return this.spieler.zuegeProperty();
	}

	/**
	 * Eine Property,die die Anzahl der Felder von Spieler 1 enthaelt, um dieses an
	 * die GUI zu binden
	 * 
	 * @return Die Property, die die Anzahl der Felder von Spieler 1 enthaelt
	 */
	public SimpleIntegerProperty getAnzFelderPropertySp1() {
		return this.spieler.anzFelderProperty();
	}

	/**
	 * Eine Property,die die Anzahl der Felder von Spieler 2 enthaelt, um dieses an
	 * die GUI zu binden
	 * 
	 * @return Die Property, die die Anzahl der Felder von Spieler 2 enthaelt
	 */
	public SimpleIntegerProperty getAnzFelderPropertySp2() {
		return this.spieler2.anzFelderProperty();
	}

	/**
	 * Macht zufaellig einen Spieler zum aktiven Spieler, der dann den ersten Zug
	 * machen darf
	 */
	private void wuerfelZugAus() {
		List<Spieler> tmpSpielerList = new ArrayList<Spieler>();
		tmpSpielerList.add(spieler);
		tmpSpielerList.add(spieler2);
		Random rnd = new Random();
		int index = rnd.nextInt(2);
		tmpSpielerList.get(index).setAktiv(true);
	}

	/**
	 * Gibt den aktiven Spieler zurueck
	 * 
	 * @return der aktive Spieler
	 */
	private Spieler getAktivSpieler() {
		if (spieler.isAktiv())
			return spieler;
		else
			return spieler2;
	}

	/**
	 * Gibt den inaktiven Spieler zurueck
	 * 
	 * @return der inaktive Spieler
	 */
	private Spieler getInaktivSpieler() {
		if (spieler.isAktiv())
			return spieler2;
		else
			return spieler;
	}

	/**
	 * Nach einem Zug werden der aktive und inaktive Spieler ausgetauscht, damit der
	 * andere Spieler einen Zug machen kann
	 */
	private void wechselZug() {
		if (spieler.isAktiv()) {
			spieler2.setAktiv(true);
			spieler.setAktiv(false);
		} else {
			spieler.setAktiv(true);
			spieler2.setAktiv(false);
		}
	}

	/**
	 * Gibt einen String zurueck, der den Namen des aktiven Spielers enthaelt
	 * 
	 * @return Den String des aktiven Spielers
	 */
	public String getAktivSpielerString() {
		if (spieler.isAktiv()) {
			return "Spieler 1";
		}

		else {
			return "Spieler 2";
		}
	}

	/**
	 * Prueft, ob die Auswahl der Farbe des Spielers gueltig ist, sie ist nur
	 * gueltig, wenn der aktive Spieler und der inaktive Spieler nicht bereits die
	 * uebergebene Farbe haben
	 * 
	 * @param c
	 *            Die Farbe, die geprueft werden muss
	 * @return True, wenn die Auswahl gueltig ist, False, wenn nicht
	 */
	public boolean isAuswahlGueltig(Color c) {
		if (getAktivSpieler().getFarbe() == c)
			return false; // Spieler hat die Farbe schon
		if (isMehrspieler && getInaktivSpieler().getFarbe() == c)
			return false; // Spieler 2 hat Farbe schon
		return true;

	}

	/**
	 * Sucht nach Feldern, die sich in der Naehe des Feldes des Spielers befinden
	 * und wandelt diese Feldern mit der uebergebenen Farbe um
	 * 
	 * @param c
	 *            Die Farbe, nach denen die gefundenen Felder umgewandelt werden
	 *            sollen
	 */
	public void sucheFelder(Color c) {
		// Speicher fuer Zug zurueck
		this.letzterSpielerstand = new Spieler(getAktivSpieler());
		this.letztesSpielbrett = new Spielbrett(spielbrett);

		for (int i = 0; i < getAktivSpieler().getSpielerFeld().length; i++) {
			for (int j = 0; j < getAktivSpieler().getSpielerFeld()[i].length; j++) {
				if (getAktivSpieler().getSpielerFeld()[i][j] != null) {
					Zelle zelle = spielbrett.getZelle(i, j);
					aendereFelder(zelle, c);
				}
			}
		}
		getAktivSpieler().macheZug(c);
		for (Zelle z : tmpZellList) {
			getAktivSpieler().setFeld(z);
		}
		tmpZellList.clear();
		umfaerbenZellen(c);
		if (isMehrspieler)
			wechselZug();

	}

	/**
	 * Setzt alle Felder des Spielers auf die gewaehlte Farbe und aendert ebenfalls
	 * das Spielbrett auf den gleichen Positionen
	 * 
	 * @param c
	 *            Die Farbe, die der Spieler ausgewaehlt hat
	 */
	private void umfaerbenZellen(Color c) {

		for (int i = 0; i < spielbrett.getFelder().length; i++)
			for (int j = 0; j < spielbrett.getFelder()[i].length; j++) {
				if (getAktivSpieler().getSpielerFeld()[i][j] != null) {
					if (getAktivSpieler().getSpielerFeld()[i][j] != c) {
						getAktivSpieler().getSpielerFeld()[i][j] = c;
					}

					spielbrett.setZelleFarbe(i, j, c);
				}
			}

	}

	/**
	 * Springt von der Ausgangszelle zu Nachbarszellen, um zu pruefen, ob diese die
	 * gleiche Farbe haben. Hierbei wird von jeder Zelle aus nach oben, unten,
	 * rechts und links gesucht. Die Nachbarszellen duerfen auch nicht schon besucht
	 * worden sein oder dem anderen Spieler gehoeren, eine Zelle gilt als besucht,
	 * wenn sie schon geprueft wurde. Wenn zu einer Nachbarszelle gesprungen wurde,
	 * gilt diese als Ausgangszelle, von der aus nach oben, unten,links und rechts
	 * geprueft wird. Dies wiederholt sich, bis keine Zellen mehr vorhanden sind,
	 * die die Kriterien erfuellen Zellen, die die Kriterien erfuellen, werden
	 * temporaer in einer Liste gespeichert, die in einer anderen Methode
	 * ausgewertet wird
	 * 
	 * @param zelle
	 *            Die Zelle, von der aus gestartet werden soll
	 * @param c
	 *            Die Farbe, nach der gesucht werden soll
	 */
	private void aendereFelder(Zelle zelle, Color c) {
		// j = X
		// i = Y
		if (zelle.getBesucht())
			return;
		zelle.setBesucht(true);
		Zelle tmpZelle;
		Stack<Zelle> s = new Stack<Zelle>();
		s.push(zelle);
		int tmpPunktX;
		int tmpPunktY;
		while (!s.isEmpty()) {
			tmpZelle = s.peek();
			tmpPunktX = (int) tmpZelle.getPunkt().getX();
			tmpPunktY = (int) tmpZelle.getPunkt().getY();
			if (tmpZelle.getPunkt().getY() - 1 >= 0 && spielbrett.getZelleFarbe(tmpPunktY - 1, tmpPunktX) == c
					&& spielbrett.getZelle(tmpPunktY - 1, tmpPunktX).getBesucht() == false
					&& getInaktivSpieler().getFeld((int) tmpZelle.getPunkt().getY() - 1, tmpPunktX) == null) {

				tmpZelle = spielbrett.getZelle(tmpPunktY - 1, tmpPunktX);
				tmpZelle.setBesucht(true);
				tmpZellList.add(tmpZelle);
				s.push(tmpZelle);

				System.out.println(tmpPunktX + ", " + tmpPunktY);
				continue;
			}
			if (tmpZelle.getPunkt().getX() - 1 >= 0 && spielbrett.getZelleFarbe(tmpPunktY, tmpPunktX - 1) == c
					&& spielbrett.getZelle(tmpPunktY, tmpPunktX - 1).getBesucht() == false
					&& getInaktivSpieler().getFeld(tmpPunktY, (int) tmpPunktX - 1) == null) {

				tmpZelle = spielbrett.getZelle(tmpPunktY, tmpPunktX - 1);
				tmpZelle.setBesucht(true);

				tmpZellList.add(tmpZelle);
				s.push(tmpZelle);

				System.out.println(tmpPunktX + ", " + tmpPunktY);
				continue;

			}
			if (tmpZelle.getPunkt().getY() + 1 < spielbrett.getFelder().length
					&& spielbrett.getZelleFarbe(tmpPunktY + 1, tmpPunktX) == c
					&& spielbrett.getZelle(tmpPunktY + 1, tmpPunktX).getBesucht() == false
					&& getInaktivSpieler().getFeld((int) tmpPunktY + 1, tmpPunktX) == null) {

				tmpZelle = spielbrett.getZelle(tmpPunktY + 1, tmpPunktX);
				tmpZelle.setBesucht(true);
				tmpZellList.add(tmpZelle);

				s.push(tmpZelle);

				System.out.println(tmpPunktX + ", " + tmpPunktY);
				continue;

			}
			if (tmpZelle.getPunkt().getX() + 1 < spielbrett.getFelder().length
					&& spielbrett.getZelleFarbe(tmpPunktY, tmpPunktX + 1) == c
					&& spielbrett.getZelle(tmpPunktY, tmpPunktX + 1).getBesucht() == false
					&& getInaktivSpieler().getFeld(tmpPunktY, (int) tmpPunktX + 1) == null) {

				tmpZelle = spielbrett.getZelle(tmpPunktY, tmpPunktX + 1);
				tmpZelle.setBesucht(true);
				tmpZellList.add(tmpZelle);
				s.push(tmpZelle);

				System.out.println(tmpPunktX + ", " + tmpPunktY);
				continue;
			}
			tmpZelle.setBesucht(true);
			tmpZelle = s.pop();

		}
		setzeZueruck();

	}

	/**
	 * Alle Felder werden auf unbesucht gestellt, damit wieder ueber diese
	 * gesprungen werden kann
	 */
	private void setzeZueruck() {
		for (int i = 0; i < spielbrett.getFelder().length; i++)
			for (int j = 0; j < spielbrett.getFelder().length; j++) {
				spielbrett.getZelle(i, j).setBesucht(false);
			}
	}

	/**
	 * Macht einen Zug zurueck, indem alte gespeicherte Versionen des Spielbretts
	 * und des Spielers als aktuelle Objekte gesetzt werden
	 */
	public void macheZugZurueck() {

		if (this.letzterSpielerstand != null && this.letztesSpielbrett != null) {
			{
				this.spieler = new Spieler(letzterSpielerstand);
				umfaerbenZellen(spieler.getFarbe());
				spieler.setZuege(spieler.getZuege() - 1);
			}
			this.spielbrett = new Spielbrett(letztesSpielbrett);

		}

	}

	/**
	 * Prueft, ob das Spiel vorbei ist. Bei einem Einzelspieler-Spiel ist das Spiel
	 * vorbei, wenn der Spieler alle Felder eingenommen hat Beim Mehrspieler-Modus
	 * gilt das Spiel als vorbei, wenn der erste und zweite Spieler gemeinsam alle
	 * Felder eingenommen hat
	 * 
	 * @return true, wenn das Spiel vorbei ist, false, wenn nicht
	 */
	public boolean istSpielVorbei() {
		if (!isMehrspieler) {
			for (int i = 0; i < spieler.getSpielerFeld().length; i++) {
				for (int j = 0; j < spieler.getSpielerFeld()[i].length; j++) {
					if (spieler.getSpielerFeld()[i][j] == null) {
						return false;
					}
				}
			}
			speichereHighscore();
			return true;
		} else if ((spieler.getAnzahlFelder() + spieler2.getAnzahlFelder()) == Math.pow(spielbrett.getFelder().length,
				2)) {
			return true;
		} else
			return false;

	}

	/**
	 * Speichert nach einem erfolgreichen Spiel den Highscore in einer Liste
	 */
	private void speichereHighscore() {
		Highscore h = new Highscore(spieler.getZuege(), this.getGroesse());
		highscoreList.add(h);
	}

	/**
	 * Gibt nach einem Mehrspieler-Spiel einen String zurueck, der den Gewinner
	 * deklariert
	 * 
	 * @return Einen String, mit dem Sieger
	 */
	public String getGewinnerString() {
		if (spieler.getAnzahlFelder() < spieler2.getAnzahlFelder())
			return "Spieler 2 hat gewonnen";
		else if (spieler.getAnzahlFelder() > spieler2.getAnzahlFelder())
			return "Spieler 1 hat gewonnen";
		else
			return "Unentschieden";
	}

}
