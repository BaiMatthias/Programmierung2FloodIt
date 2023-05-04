/**
 * Diese Klasse repraesentiert den Controller, eine Schnittstelle zwischen GUI und Spiellogik.
 * Diese Klasse fängt die Events ab, die von der GUI ausgehen und ruft die entsprechenden Methoden der Engine auf. Steuert ebenfalls, welche GUI-Elemente zu welcher Zeit aktiv sind
 * @author Matze & Ali
 */

package application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class SpielController extends Application {

	@Override
	public void start(Stage primarystage) throws Exception {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/FloodItGUI.fxml")); // Aufruf der FXML-Datei
			loader.setController(this);
			Parent lc = loader.load();
			Scene scene = new Scene(lc, 800, 800);
			primarystage.setTitle("Flood It");
			primarystage.setScene(scene);
			primarystage.show(); 
			highscoreList = new ArrayList<Highscore>();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	// ------------FXML--------------------
	@FXML private GridPane gridBasis;
	@FXML private Button btnEinzelspieler;
	@FXML private Button btnMehrspieler;
	@FXML private Slider slidFelder;
	@FXML private Slider slidFarben;
	@FXML private ButtonBar btnBarOben;
	@FXML private ButtonBar btnBarUnten;
	@FXML private Label lblZuganzeige; 
	@FXML private TableView<Highscore> tableView;
	@FXML private Button btnZugZurueck;
	@FXML private Label lblZug;
	@FXML private Label lblFelderSp1;
	@FXML private Label lblFelderSp2;
	@FXML private Label lblZugInfo;
    @FXML private Label lblZugSpielerInfo;
    @FXML private Label lblFelderSp1Info;
    @FXML private Label lblFelderSp2Info;
	
	
	// ----------------------------------
	private GridPane letztesGrid;
	private GridPane gridFarben;
	private boolean isMehrspieler;
	SpielEngine engine;
	ObservableList<Highscore> obsHighscoreList;
	List<Highscore> highscoreList;
	// ------------FXML-Events---------
	/**
	 * Wird aufgerufen, wenn der Benutzer auf den Button "Einzelspieler" klickt. Hier wird die entsprechende Engine erstellt, um 
	 * ein Einzelspieler-Spiel zu spielen. Bereitet ebenfalls die GUI auf den Modus vor, in dem das Spielfeld gemalt sowie 
	 * GUI-Elemente aktiviert werden
	 */
	@FXML
	public void starteEinzelspieler() {
		
		guiEinzelspieler();
		isMehrspieler = false;
		btnZugZurueck.setDisable(true);
		erstelleFarbenButtons();
		engine = new SpielEngine((int)slidFelder.getValue(), (int)slidFarben.getValue(), false);
		lblZuganzeige.textProperty().bind(engine.getZuegeSpieler().asString());
	   	zeichneFeld((int)slidFelder.getValue());
				 
		
	}
	/**
	 * Wird aufgerufen, wenn der Spieler auf den Button "Mehrspieler" klickt. Hier wird die Engine erstellt, um ein Mehrspieler-Spiel
	 * zu spielen. Bereitet ebenfalls die GUI auf den Modus vor, in dem das Spielfeld gemalt sowie 
	 * GUI-Elemente aktiviert werden
	 */
	@FXML
	public void starteMehrspieler() {
		
		if(slidFarben.getValue() < 3) {
			return;
		}
		guiMehrspieler();
		isMehrspieler = true;
		btnZugZurueck.setDisable(true);
		erstelleFarbenButtons();
		engine = new SpielEngine((int)slidFelder.getValue(), (int)slidFarben.getValue(), true);
		lblZug.setText(engine.getAktivSpielerString());
		lblFelderSp1.textProperty().bind(engine.getAnzFelderPropertySp1().asString());
		lblFelderSp2.textProperty().bind(engine.getAnzFelderPropertySp2().asString());
		zeichneFeld((int)slidFelder.getValue());
	}
	/**
	 * Wird aufgerufen, wenn der Spieler auf den Button "Zug zurueck" im Einzelspieler klickt.
	 * Hier wird die Methode der Engine aufgerufen, um den Zug zurueckzusetzen und das Spielfeld in der GUI aktualisiert
	 */
	@FXML
	public void setzeZugZurueck() {
		engine.macheZugZurueck();
		zeichneFeld(engine.getGroesse());
	}
	/**
	 * Wird aufgerufen, wenn der Spieler auf eine der farbigen Buttons klickt. Zunaechst muss herausgefunden
	 * werden, welche Farbe der Spieler ausgewaehlt hat. Anschließend wird die Engine zur Spiellogik verwendet, um 
	 * das Spieler-Objekt und das Spielbrett-Objekt zu veraendern. Schlußendlich wird das veraenderte Feld neugezeichnet.
	 * Sollte das Spieler danach vorbei sein, werden die entsprechenden Optionen ausgefuehrt, bei einem Einzelspieler-Spiel 
	 * wird die Highscore - Liste angezeigt, bei einem Mehrspieler-Modus wird der Sieger angezeigt
	 * @param event
	 */
	@FXML
	public void einfaerbenZelle(ActionEvent event) {
		btnZugZurueck.setDisable(false);
		
		// 1=cRot,2=cBlau, 3=cGruen,4=cPink, 5=cGelb, 6=cGrau
	
		Button btnFarbe = (Button) event.getSource();
		// Farbe rausbekommen
		Color c = null;
		if(btnFarbe.getId().equals("btn0")){ // Rot
			c = Color.RED;
		}
		if(btnFarbe.getId().equals("btn1")){ // Blau
			c = Color.BLUE;
		}
		if(btnFarbe.getId().equals("btn2")){ // Gruen
			c = Color.GREEN;
		}
		if (btnFarbe.getId().equals("btn3")) { // Pink
			c = Color.PINK;
		}
		if (btnFarbe.getId().equals("btn4")) { // Gelb
			c = Color.YELLOW;
		}
		if (btnFarbe.getId().equals("btn5")) { // Grau
			c = Color.GRAY;
		}
		if(c == null) System.out.println(btnFarbe.getId());
		if(engine.isAuswahlGueltig(c)){
			
		
		engine.sucheFelder(c);
		zeichneFeld(engine.getGroesse());
		lblZug.setText(engine.getAktivSpielerString());
		if(engine.istSpielVorbei()) {
			try {
				if(!isMehrspieler) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HighscoreGUI.fxml"));
					loader.setController(this);
					Parent lc = loader.load();
					Comparator<Highscore> comp = Comparator.comparingInt(Highscore::getZuege);
					
					
					highscoreList.addAll(engine.getHighscoreList());
					
					TableColumn<Highscore, Integer> zuegeCol = new TableColumn<Highscore, Integer>("Züge");
					zuegeCol.setMinWidth(200);
					TableColumn<Highscore, Integer> groesseCol = new TableColumn<Highscore, Integer>("Feldgröße");
					groesseCol.setMinWidth(200);
					
					zuegeCol.setCellValueFactory(new PropertyValueFactory<>("zuege"));
					groesseCol.setCellValueFactory(new PropertyValueFactory<>("feldgroesse"));
					
					obsHighscoreList = FXCollections.observableList(highscoreList);
					FXCollections.sort(obsHighscoreList, comp);
					
					tableView.getColumns().add(zuegeCol);
				    tableView.getColumns().add(groesseCol);
					tableView.setItems(obsHighscoreList);
					
					
					
					Scene scene = new Scene(lc, 600, 400);
					Stage highscoreStage = new Stage();	
					highscoreStage.setTitle("Highscore");
					highscoreStage.setScene(scene);
					//System.out.println("Highscore da");
					highscoreStage.show();
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Spiel vorbei");
					alert.setHeaderText(null);
					alert.setContentText(engine.getGewinnerString());

					alert.showAndWait();
				}
				deaktiviereButtons();
			}catch(Exception e) {
				System.out.println("Fehler beim Spiel vorbei");
				e.printStackTrace();
			}
			

		}
		}
		
	}

	// ----------------------------------------------------
	/**
	 * zeichnet die GUI mit farbigen nicht-anklickbaren Buttons
	 * @param groesse Die groesse des Feldes, das gezeichnet werden soll
	 */
	private void zeichneFeld(int groesse) {
		for(int i = 0; i<groesse; i++) 
			 for(int j = 0; j<groesse; j++) {
				 Button b = new Button();
				 Color c = engine.getSpielbrett().getZelleFarbe(i, j);
				 b.setBackground(new Background(new BackgroundFill(Paint.valueOf(Farbensammlung.getColorName(c)), CornerRadii.EMPTY, Insets.EMPTY)));
				 b.setMinHeight(790/groesse);
				 b.setMinWidth(524/groesse);
				 gridFarben.add(b, j, i);
				 
			 }

		 gridFarben.setMinHeight(700);
		 gridFarben.setMinWidth(500);
		 gridFarben.setPrefWidth(500);
		 gridFarben.setPrefHeight(700);
		 if(letztesGrid!=null) 
			 gridBasis.getChildren().remove(letztesGrid);
		 gridBasis.add(gridFarben,1,0); 
		 letztesGrid = gridFarben;
	}
	/**
	 * Erstellt die farbigen Buttons, die der Nutzer anklicken kann, je nach Auswahl des Nutzers werden
	 * bis zu 6 Buttons erstellt und mit Klick-Event versehen
	 */
	private void erstelleFarbenButtons() {
		
		int farben = (int) slidFarben.getValue();
		
		btnBarOben.getButtons().clear();
		btnBarUnten.getButtons().clear();
		gridFarben = new GridPane();
		
		for (int i = 0; i < farben; i++) {
			Button btnFarbe = new Button();
			btnFarbe.setId("btn"+i);
			btnFarbe.setOnAction(this::einfaerbenZelle);
			btnFarbe.setPrefHeight(50);
			Color c = Farbensammlung.getFarben()[i];
			btnFarbe.setBackground(new Background(new BackgroundFill(Paint.valueOf(Farbensammlung.getColorName(c)), CornerRadii.EMPTY, Insets.EMPTY)));
			if((int)i/3 == 0) {
				ButtonBar.setButtonData(btnFarbe, ButtonData.LEFT);
				btnBarOben.getButtons().add(btnFarbe);
				
			} else {
				ButtonBar.setButtonData(btnFarbe, ButtonData.LEFT);
				btnBarUnten.getButtons().add(btnFarbe);
			}
			
		}
	}
	/**
	 * Wenn das Spiel vorbei ist, werden alle Buttons deaktiviert, damit der Spieler keine Zuege mehr durchfuehren kann
	 */
	private void deaktiviereButtons() {
		for(Node n : btnBarOben.getButtons()) {
			n.setVisible(false);
		}
		for(Node n : btnBarUnten.getButtons()) {
			n.setVisible(false);
		}
		
		btnZugZurueck.setDisable(true);
	}
	
	/**
	 * Erstellt eine GUI massgeschneidert fuer den Einzelspieler
	 */
	private void guiEinzelspieler() {
		lblZugSpielerInfo.setVisible(false);
		lblFelderSp1Info.setVisible(false);
		lblFelderSp2Info.setVisible(false);
		lblZug.setVisible(false);
		lblFelderSp1.setVisible(false);
		lblFelderSp2.setVisible(false);
		
		lblZugInfo.setVisible(true);
		lblZuganzeige.setVisible(true);
	}
	/**
	 * Erstellt eine GUI massgeschneidert fuer den Mehrspieler
	 */
	private void guiMehrspieler() {
		lblZugInfo.setVisible(false);
		lblZuganzeige.setVisible(false);
		
		lblZugSpielerInfo.setVisible(true);
		lblFelderSp1Info.setVisible(true);
		lblFelderSp2Info.setVisible(true);
		lblZug.setVisible(true);
		lblFelderSp1.setVisible(true);
		lblFelderSp2.setVisible(true);
	}
}
