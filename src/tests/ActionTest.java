package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

import balles.Balle;
import application.Action;

public class ActionTest {

	private ArrayList<Balle> listeBalle;
	private Balle balle, balle1, balle2;
	
	@Before
	public void genererBalle(){
		balle = new Balle(Color.RED, 10, 40, 10, 10, Math.PI/4);
		balle1 = new Balle(Color.RED, 5, 50, 20, 0, 0);
		balle2 = new Balle(Color.RED, 10, 10, 10, -100, Math.PI/4);
		listeBalle = new ArrayList<Balle>();
		listeBalle.add(balle);
		listeBalle.add(balle1);
	}
	
	@Test
	public void testCalculerNextPos() {
		Action.calculerNextPos(listeBalle);
		assertTrue(balle.getNextPosX() > balle.posXProperty().get());
		assertTrue(balle.getNextPosY() > balle.posYProperty().get());
	}

	@Test
	public void testCollisionMur() {
		Action.calculerNextPos(listeBalle);
		Action.collisionMur(listeBalle);
		assertTrue(balle.getNextPosX() > 0 && balle.getNextPosX() < 773);
		assertTrue(balle.getNextPosY() > 0 && balle.getNextPosY() < 367);
	}

	@Test
	public void testCollisionBalle() {
		Action.calculerNextPos(listeBalle);
		Action.collisionBalle(listeBalle);
	}

	@Test
	public void testRandomBalle() {
		assertFalse(Action.randomBalle().hashCode() == Action.randomBalle().hashCode());
	}

}
