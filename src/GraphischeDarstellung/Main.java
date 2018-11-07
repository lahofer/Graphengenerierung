package GraphischeDarstellung;
	
import java.util.Optional;

import Exceptions.KugelException;
import Graph.Raum;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Main extends Application {
	
	Stage fenster;
	Raum raum;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		/*
		 * erstellt das Hauptfenster
		 */
		this.fenster = primaryStage;
		this.fenster.setTitle("Graphengenerierung");
		Pane hauptFenster = new Pane();
		hauptFenster.setPadding(new Insets(8, 8, 8, 8));
		this.raum = new Raum(0, 500, 500, 1);
		
		/*
		 *erstellt Buttonleiste fuer grundlegende Funktionen 
		 */
		HBox buttonLeiste = new HBox();
		buttonLeiste.getStyleClass().add("hbox");
		
		Button neuerGraphButton = new Button("Neu");
		neuerGraphButton.setOnAction((ActionEvent e) ->{
			Dialog<Raum> dialog = new Dialog<>();
			dialog.setTitle("Neuen Graphen erzeugen");
			dialog.setHeaderText("Neue Werte eingeben:");
			
			Label breite_label = new Label("Breite: ");
			TextField breite_text = new TextField(Integer.toString(this.raum.getBreite()));
			Label hoehe_label = new Label("Höhe: ");
			TextField hoehe_text = new TextField(Integer.toString(this.raum.getHoehe()));
			Label radius_label = new Label("Radius: ");
			TextField radius_text = new TextField(Double.toString(this.raum.getRadius()));
			Label anzahl_label = new Label("Anzahl der Kugeln");
			TextField anzahl_text = new TextField(Integer.toString(this.raum.getN()));
			
			GridPane gitterLayout = new GridPane();
			gitterLayout.add(breite_label, 1, 1);
			gitterLayout.add(breite_text, 2, 1);
			gitterLayout.add(hoehe_label, 1, 2);
			gitterLayout.add(hoehe_text, 2, 2);
			gitterLayout.add(radius_label, 1, 3);
			gitterLayout.add(radius_text, 2, 3);
			gitterLayout.add(anzahl_label, 1, 4);
			gitterLayout.add(anzahl_text, 2, 4);
			
			dialog.getDialogPane().setContent(gitterLayout);
			ButtonType buttonTypeStart = new ButtonType("Start", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeStart);
			
			dialog.setResultConverter(new Callback<ButtonType, Raum>(){
				@Override
				public Raum call(ButtonType b) {
					if(b==buttonTypeStart) {
						try {
							if( Integer.parseInt(breite_text.getText()) > 0 &&
									Integer.parseInt(hoehe_text.getText()) > 0 &&
									Double.parseDouble(radius_text.getText()) > 0 &&
									Integer.parseInt(anzahl_text.getText()) >= 0) {
								
								Raum r = new Raum(Integer.parseInt(anzahl_text.getText()),
										Integer.parseInt(hoehe_text.getText()),
										Integer.parseInt(breite_text.getText()),
										Double.parseDouble(radius_text.getText()));
								return r;
								
							}
							else {
								Alert ungueltigeEingabeNachricht = new Alert(AlertType.ERROR);
								ungueltigeEingabeNachricht.setTitle("Fehler");
								ungueltigeEingabeNachricht.setContentText("Die angegebenen Werte"
										+ " sind ungültig.");
								ungueltigeEingabeNachricht.showAndWait();
								
							}
						
						} catch(Exception e) {
							Alert ungueltigeEingabeNachricht = new Alert(AlertType.ERROR);
							ungueltigeEingabeNachricht.setTitle("Fehler");
							ungueltigeEingabeNachricht.setContentText("Die angegebenen Werte"
									+ " sind ungültig.");
							ungueltigeEingabeNachricht.showAndWait();
						}
		
					}
					return null;
				}
			});
			
			Optional<Raum> ergebnis = dialog.showAndWait();
			
			if(ergebnis.isPresent()) {
				this.raum = ergebnis.get();
				dialog.close();
			}
			
		});
		
		Button beendenButton = new Button("Beenden");
		beendenButton.setOnAction((ActionEvent e) ->{
			Alert beendeNachricht = new Alert(AlertType.CONFIRMATION);
			beendeNachricht.setTitle("Beenden");
			String nachricht = "Wollen Sie die Anwendung wirklich beenden?";
			beendeNachricht.setContentText(nachricht);
			
			Optional<ButtonType> antwort = beendeNachricht.showAndWait();
			
			if( antwort.isPresent() && antwort.get() == ButtonType.OK) {
				System.exit(0);
			}
			
		});
		
		buttonLeiste.getChildren().addAll(neuerGraphButton, beendenButton);
		BorderPane.setAlignment(buttonLeiste, Pos.TOP_CENTER);
		
		
		Zeichenwand z = new Zeichenwand(this.raum);
		Pane zeichenPane = new Pane();
		zeichenPane.getStyleClass().add("pane");
		zeichenPane.setMaxSize(this.raum.getBreite(), this.raum.getHoehe());
		zeichenPane.getChildren().add(z);
		BorderPane.setAlignment(zeichenPane, Pos.BOTTOM_CENTER);
		
		
		BorderPane layout = new BorderPane(zeichenPane, buttonLeiste, null, null, null);
		Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.fenster.setScene(scene);
		this.fenster.setResizable(false);
		this.fenster.show();
		
	}	
	
	public static void main(String[] args) {
		launch(args);
	}
}
