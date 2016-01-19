/**
 *
 *	Cette classe est le main de l'application.
 *
 *	@version 1.0
 *
 *	Automne 2014
 *
 *	@author Jonathan Samson et Jérémie Bolduc
 *
 */
package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import balles.Balle;

public class Controleur extends Application implements Initializable {

	public static final double maxY = 367.0;
	public static final double maxX = 773.0;
	public static final double varGlow = 0.1;
	protected DoubleProperty glow;
	protected boolean variationGlow;
	@FXML
	protected AnchorPane paneBalle;
	@FXML
	protected CheckBox effect;
	@FXML
	protected Label nb;
	@FXML
	protected Rectangle fond;
	@FXML
	protected Button quitter, aide, reinit, generer;
	@FXML
	protected ColorPicker colorPicker;
	@FXML
	protected Slider rayonSlider, vitesse, angle;
	private ArrayList<Balle> listeBalle;

	/**
	 * Sert à démarrer l'application 
	 */
	public void start(Stage primaryStage) {
		try {
			SplitPane page = (SplitPane) FXMLLoader.load(Controleur.class
					.getResource("VueFXML.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Collisions");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sert à enclencher l'application graphique.
	 */

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Sert à initialiser les paramètre de l'application.
	 * 
	 * @param URL
	 *            , RessourceBundle
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Thread th = new Thread(new Mouvement());
		th.setDaemon(true);
		th.start();
		glow = new SimpleDoubleProperty();
		glow.set(0.5);
		variationGlow = true;
		colorPicker.setValue(Color.BLACK);
		rayonSlider.setMin(3);
		rayonSlider.setMax(25);
		vitesse.setMin(0);
		vitesse.setMax(200);
		vitesse.setValue(100);
		angle.setMin(0);
		angle.setMax(Math.PI * 2);
		angle.setValue(0);
		nb.setText("0");

		listeBalle = new ArrayList<Balle>();
		quitter.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				Platform.exit();
			}
		}); 

		generer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				for(int i = 0; i < 10; i++){
					Balle ball = Action.randomBalle();
					Circle balle = ball.getBalle();

					if (effect.isSelected()) {
						Glow g = new Glow();
						g.levelProperty().bind(glow);
						Platform.runLater(() -> {
							balle.setEffect(g);
						});
					}
					
					listeBalle.add(ball);
					paneBalle.getChildren().add(balle);
				}
			}
		});

		aide.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Stage dialog = new Stage();
				dialog.initStyle(StageStyle.UTILITY);
				Text t = new Text(20, 20, "Allez voir Walid pour de l'aide.");
				t.setFont(Font.font("Verdana", 20));
				AnchorPane ap = new AnchorPane(t);
				ap.setMinHeight(60);
				ap.setMinWidth(370);
				Scene scene = new Scene(ap);
				dialog.setScene(scene);
				dialog.show();
			}
		});

		reinit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				paneBalle.getChildren().clear();
				paneBalle.getChildren().add(fond);
				listeBalle.clear();
				initialize(location, resources);
			}
		});

		effect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				Glow g = new Glow();
				g.levelProperty().bind(glow);
				if (effect.isSelected() == false) {
					for (Node ball : paneBalle.getChildren()) {
						if (!ball.getClass().equals(
								(new Rectangle().getClass()))) {
							Platform.runLater(() -> {
								ball.setEffect(null);
							});
						}
					}
				} else {
					for (Node ball : paneBalle.getChildren()) {
						if (!ball.getClass().equals(
								(new Rectangle().getClass()))) {
							Platform.runLater(() -> {
								ball.setEffect(g);
							});
						}
					}
				}
			}
		});

		fond.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				Balle ball = new Balle(colorPicker.getValue(),
						rayonSlider.getValue(), me.getSceneX(), me
								.getSceneY(), vitesse.getValue(), angle
								.getValue());
				Circle balle = ball.getBalle();

				if (effect.isSelected()) {
					Glow g = new Glow();
					g.levelProperty().bind(glow);
					Platform.runLater(() -> {
						balle.setEffect(g);
					});
				}
				
				listeBalle.add(ball);
				paneBalle.getChildren().add(balle);
			}
		});
	}
	
	/**
	 * Cette classe interne sert à gérer l'ordre des événements d'un mouvement.
	 *
	 * Jonathan Samson et Jérémie Bolduc V1.0
	 */
	public class Mouvement extends Task<Object> {

		@Override
		protected Object call() throws Exception {
			Object o = new Object();
			boolean True = true;

			while (True) {

				Action.calculerNextPos(listeBalle);
				Action.collisionMur(listeBalle);
				Action.collisionBalle(listeBalle);
				Action.setPos(listeBalle);
				if (effect.isSelected())
					calculerEffet();
				Platform.runLater(() -> {
					nb.setText(Integer.toString(listeBalle.size()));
				});
				Thread.sleep(16);
			}
			return o;
		}

		/**
		 * Crée un effet graphique sur les balles à l'aide du thread.
		 */
		private void calculerEffet() {
			if (effect.isSelected()) {
				if (variationGlow) {
					Platform.runLater(() -> {
						glow.set(glow.get() + varGlow);
					});
				} else {
					Platform.runLater(() -> {
						glow.set(glow.get() - varGlow);
					});
				}
				if (glow.get() >= 1.0) {
					Platform.runLater(() -> {
						glow.set(1.0 - varGlow);
					});
					variationGlow = false;
				} else if (glow.get() <= 0.0) {
					Platform.runLater(() -> {
						glow.set(varGlow);
					});
					variationGlow = true;
				}
			}
		}
	}
}
