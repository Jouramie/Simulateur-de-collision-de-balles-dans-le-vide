package application;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import balles.Balle;

public class Action {

	//La position maximale en X et en Y
	public static final double maxY = 367, maxX = 773;

	/**
	 * Cette méthode génère les prochaines position des balles
	 * 
	 * @param listeBalle
	 *            toutes les balles
	 */
	public static void calculerNextPos(ArrayList<Balle> listeBalle) {
		Balle.maintenant = System.currentTimeMillis();
		for (Balle ball : listeBalle) {
			ball.calculerNextPos();
		}
		Balle.tantot = System.currentTimeMillis();
	}

	/**
	 * Modifie la prochaine position des balles s'il y a des collisions avec les
	 * murs
	 * 
	 * @param listeBalle
	 *            toutes les balles
	 */
	public static void collisionMur(ArrayList<Balle> listeBalle) {
		for (Balle ball : listeBalle) {
			while (ball.getNextPosX() - ball.getRayon() < 0
					|| ball.getNextPosX() + ball.getRayon() > maxX
					|| ball.getNextPosY() - ball.getRayon() < 0
					|| ball.getNextPosY() + ball.getRayon() > maxY) {

				if (ball.getNextPosX() - ball.getRayon() < 0) {
					double[] nNextPos = {
							ball.getRayon()
									- (ball.getNextPosX() - ball.getRayon()),
							ball.getNextPosY() };
					ball.setVitesseX(-ball.getVitesseX());
					ball.setNextPos(nNextPos);

				} else if (ball.getNextPosX() + ball.getRayon() > maxX) {
					double[] nNextPos = {
							(maxX - ball.getRayon())
									- (ball.getNextPosX() + ball.getRayon())
									+ maxX, ball.getNextPosY() };
					ball.setNextPos(nNextPos);
					ball.setVitesseX(-ball.getVitesseX());
				}

				if (ball.getNextPosY() - ball.getRayon() < 0) {
					double[] nNextPos = {
							ball.getNextPosX(),
							ball.getRayon()
									- (ball.getNextPosY() - ball.getRayon()) };
					ball.setNextPos(nNextPos);
					ball.setVitesseY(-ball.getVitesseY());

				} else if (ball.getNextPosY() + ball.getRayon() > maxY) {
					double[] nNextPos = {
							ball.getNextPosX(),
							(maxY - ball.getRayon())
									- (ball.getNextPosY() + ball.getRayon())
									+ maxY };
					ball.setNextPos(nNextPos);
					ball.setVitesseY(-ball.getVitesseY());
				}
			}
		}
	}

	/**
	 * Modifie la prochaine position et la vitesse des balles s'il y a des
	 * collisions avec d'autres balles
	 * 
	 * @param listeBalle
	 *            toutes les balles
	 */
	public static void collisionBalle(ArrayList<Balle> listeBalle) {
		for (Balle ball1 : listeBalle) {
			for (Balle ball2 : listeBalle) {
				if (ball1.hashCode() != ball2.hashCode()) {
					if (Math.pow(ball1.getRayon() + ball2.getRayon(), 2) > Math
							.pow(ball1.getNextPosX() - ball2.getNextPosX(), 2)
							+ Math.pow(
									ball1.getNextPosY() - ball2.getNextPosY(),
									2)) {

						double x1 = ball1.getNextPosX(), y1 = ball1
								.getNextPosY();
						double x2 = ball2.getNextPosX(), y2 = ball2
								.getNextPosY();
						double r1 = ball1.getRayon(), r2 = ball2.getRayon();

						double dx = x2 - x1;
						double dy = y2 - y1;
						double d = Math.sqrt(dx * dx + dy * dy);

						if (d <= r1 + r2) {
							double vx1 = ball1.getVitesseX(), vy1 = ball1
									.getVitesseY();
							double vx2 = ball2.getVitesseX(), vy2 = ball2
									.getVitesseY();
							double mass1 = ball1.getRayon(), mass2 = ball2
									.getRayon();

							// direction de la force
							double vp1 = (vx1 * dx + vy1 * dy) / d;
							double vp2 = (vx2 * dx + vy2 * dy) / d;

							double dt = (r1 + r2 - d) / (vp1 - vp2);

							// bouger les cercles
							x1 -= vx1 * dt;
							y1 -= vy1 * dt;
							x2 -= vx2 * dt;
							y2 -= vy2 * dt;

							// calculs de la nouvelle collision
							dx = x2 - x1;
							dy = y2 - y1;
							d = Math.sqrt(dx * dx + dy * dy);

							// vecteur unitaire en direction de la collision
							double ax = dx / d;
							double ay = dy / d;

							// vitesse selon les axes
							double va1 = vx1 * ax + vy1 * ay;
							double vb1 = -vx1 * ay + vy1 * ax;
							double va2 = vx2 * ax + vy2 * ay;
							double vb2 = -vx2 * ay + vy2 * ax;

							// calculer la nouvelle vitesse apres la colision
							double ed = 1;
							double vaP1 = va1 + (1 + ed) * (va2 - va1)
									/ (1 + mass1 / mass2);
							double vaP2 = va2 + (1 + ed) * (va1 - va2)
									/ (1 + mass2 / mass1);
							vx1 = vaP1 * ax - vb1 * ay;
							vy1 = vaP1 * ay + vb1 * ax;
							vx2 = vaP2 * ax - vb2 * ay;
							vy2 = vaP2 * ay + vb2 * ax;

							// temps
							x1 += vx1 * dt;
							y1 += vy1 * dt;
							x2 += vx2 * dt;
							y2 += vy2 * dt;

							// mettre à jour les positions et les vitesses
							if (!Double.isNaN(x1))
								ball1.setNextPosX(x1);
							if (!Double.isNaN(y1))
								ball1.setNextPosY(y1);
							if (!Double.isNaN(x2))
								ball2.setNextPosX(x2);
							if (!Double.isNaN(y2))
								ball2.setNextPosY(y2);
							if (!Double.isNaN(vx1))
								ball1.setVitesseX(vx1);
							if (!Double.isNaN(vy1))
								ball1.setVitesseY(vy1);
							if (!Double.isNaN(vx2))
								ball2.setVitesseX(vx2);
							if (!Double.isNaN(vy2))
								ball2.setVitesseY(vy2);

						}
					}
				}
			}

		}
	}

	/**
	 * Change la position actuelle des balles
	 */
	public static void setPos(ArrayList<Balle> listeBalle) {
		for (Balle ball : listeBalle) {
			Platform.runLater(() -> {
				ball.appliquerNextPos();
			});
		}
	}

	/**
	 * Crée une balle totalement aléatoire
	 * 
	 * @return la balle
	 */
	public static Balle randomBalle() {
		return new Balle(randomColor(), Math.random() * 22 + 3, Math.random()
				* maxX, Math.random() * maxY, Math.random() * 400,
				Math.random() * Math.PI * 2);
	}

	/**
	 * Crée une couleur totalement aléatoire
	 * 
	 * @return la couleur
	 */
	private static Color randomColor() {
		int r = (int) (Math.random() * 255);
		int v = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		String rs = Integer.toHexString(r);
		String vs = Integer.toHexString(v);
		String bs = Integer.toHexString(b);
		if (r < 16)
			rs = "0" + rs;
		if (v < 16)
			vs = "0" + vs;
		if (b < 16)
			bs = "0" + bs;
		return Color.web(rs + vs + bs);
	}

}
