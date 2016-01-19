package tests;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.junit.Test;

import balles.Balle;

public class BalleTest {
	Balle b = new Balle(Color.RED,10,21,31,41,51);

	@Test
	public void testGetBalle() {
		assertTrue(b.getBalle().getClass().equals(new Circle().getClass()));
	}

	@Test
	public void testGetVitesseX() {
		b.setVitesseX(20);
		assertTrue(b.getVitesseX() == 20 );
	}

	@Test
	public void testSetVitesseX() {
		b.setVitesseX(20);
		assertTrue(b.getVitesseX() == 20 );
	}

	@Test
	public void testGetVitesseY() {
		b.setVitesseY(20);
		assertTrue(b.getVitesseY() == 20 );
	}

	@Test
	public void testSetVitesseY() {
		b.setVitesseY(20);
		assertTrue(b.getVitesseY() == 20 );
	}

	@Test
	public void testGetRayon() {
		assertTrue(b.getRayon() == 10);
	}

	@Test
	public void testGetNextPosX() {
		b.setNextPosX(29);
		assertTrue(b.getNextPosX() == 29);
	}

	@Test
	public void testSetNextPosX() {
		b.setNextPosX(29);
		assertTrue(b.getNextPosX() == 29);
	}

	@Test
	public void testGetNextPosY() {
		b.setNextPosY(29);
		assertTrue(b.getNextPosY() == 29);
	}

	@Test
	public void testSetNextPosY() {
		b.setNextPosY(29);
		assertTrue(b.getNextPosY() == 29);
	}

	@Test
	public void testSetNextPos() {
		double[] pNextPos = {1,1};
     assertTrue(pNextPos[0] == 1);
     b.setNextPos(pNextPos);
     
	}

	@Test
	public void testToString() {
		String s = b.toString();
		assertTrue(s.equals(b.toString()));
	}

	@Test
	public void testAppliquerNextPos() {
		b.appliquerNextPos();	
	}

	@Test
	public void testCalculerNextPos() {
		b.calculerNextPos();
	}

	@Test
	public void testPosXProperty() {
		b.posXProperty();
	}

	@Test
	public void testPosYProperty() {
		b.posYProperty();
	}

}
