/**
 *
 *	Cette classe construit les objets balles.
 *
 *	@version 1.0
 *
 *	Automne 2014
 *
 *	@author Jonathan Samson et Jérémie Bolduc
 *
 */

package balles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Balle {

	public static double tantot = 0;
	public static double maintenant = 0;
	public static final int TAILLEDEFAUT = 10;
	private Color couleur;
	private double rayon;

	private DoubleProperty posX;
	private DoubleProperty posY;

	private double nextPosX;
	private double nextPosY;
	private double vitesseX;
	private double vitesseY;

	/**
	 * Constructeur publique de balle avec tous ses paramètres.
	 *
	 * @param pCouleur
	 *            , pRayon, pPosX, pPosY, pVitesse, pAngle. return objet balle
	 */
	public Balle(Color pCouleur, double pRayon, double pPosX, double pPosY,
			double pVitesse, double pAngle) {
		couleur = pCouleur;
		rayon = pRayon;
		posX = new SimpleDoubleProperty();
		posX.set(pPosX);
		posY = new SimpleDoubleProperty();
		posY.set(pPosY);
		nextPosX = pPosX;
		nextPosY = pPosY;
		vitesseX = getVitesseXY(pVitesse, pAngle)[0];
		vitesseY = getVitesseXY(pVitesse, pAngle)[1];

	}

	/**
	 * Getter des vitesses en deux dimensions de l'obejt balle.
	 *
	 * @param pVitesse
	 *            , pAngle return la matrice 1x2 des vitesses en x et en y.
	 */
	private double[] getVitesseXY(double pVitesse, double pAngle) {
		double[] r = { 0, 0 };
		r[0] = Math.cos(pAngle) * pVitesse;
		r[1] = Math.sin(pAngle) * pVitesse;
		return r;

	}

	/**
	 * Getter de l'objet balle.
	 *
	 * @return un circle en JavaFX
	 */
	public Circle getBalle() {
		Circle cercle = new Circle(posX.get(), posY.get(), rayon, couleur);
		cercle.centerXProperty().bind(posX);
		cercle.centerYProperty().bind(posY);
		return cercle;
	}

	/**
	 * Getter VitesseX.
	 * 
	 * @return vitesseX
	 */
	public double getVitesseX() {
		return vitesseX;
	}

	/**
	 * Setter VitesseX.
	 */
	public void setVitesseX(double pVitesseX) {
		vitesseX = pVitesseX;
	}

	/**
	 * Getter VitesseY.
	 * 
	 * @return vitesseY
	 */
	public double getVitesseY() {
		return vitesseY;
	}

	/**
	 * Setter VitesseY.
	 */
	public void setVitesseY(double pVitesseY) {
		vitesseY = pVitesseY;
	}

	/**
	 * getter RAYON.
	 * 
	 * @return rayon
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * Getter de la prochaine position en x.
	 * 
	 * @return nextPosX
	 */
	public double getNextPosX() {
		return nextPosX;
	}

	/**
	 * Setter de la prochaine position en x.
	 */
	public void setNextPosX(double pNextPosX) {
		nextPosX = pNextPosX;
	}

	/**
	 * Getter de la prochaine position en y.
	 * 
	 * @return nextPosY
	 */
	public double getNextPosY() {
		return nextPosY;
	}

	/**
	 * Setter de la prochaine position en Y.
	 */
	public void setNextPosY(double pNextPosY) {
		nextPosY = pNextPosY;
	}

	/**
	 * Setter de la prochaine position.
	 * 
	 * @return matrice des positions
	 */
	public void setNextPos(double[] pNextPos) {
		nextPosX = pNextPos[0];
		nextPosY = pNextPos[1];
	}

	/**
	 * ToString de l'objet balle.
	 * 
	 * @return String
	 */
	public String toString() {
		return couleur.toString() + "\r" + this.rayon;
	}

	/**
	 * Set le prochaine position à l'objet.
	 */
	public void appliquerNextPos() {
		posX.set(nextPosX);
		posY.set(nextPosY);
	}

	/**
	 * Calcule la prochaine position.
	 */
	public void calculerNextPos() {
		nextPosX = posX.get() + vitesseX * (maintenant - tantot) / 1000;
		nextPosY = posY.get() + vitesseY * (maintenant - tantot) / 1000;

	}

	/**
	 * Retourne un DoubleProperty pour la PosX
	 */
	public DoubleProperty posXProperty() {
		return posX;
	}

	/**
	 * Retourne un DoubleProperty pour la PosY
	 */
	public DoubleProperty posYProperty() {
		return posY;
	}
}
