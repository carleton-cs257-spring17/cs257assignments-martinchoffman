package sample;

import javafx.scene.image.Image;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 6/3/2017.
 */
public class Turret extends Tower {
	private final int COST = 10;
	private final int MAX_HP = 50;
	private final double ROF = .5;

	private final Image IMG = new Image("file:res/shot.png");

	private int hp;
	private Point2D pos;

	public Turret() {
		this.hp = MAX_HP;
	}

	public void setPos(Point2D position) {
		this.pos = position;
	}

	public int getCost() {
		return COST;
	}

	public int getHealth() {
		return hp;
	}

	public double getROF() {
		return rof;
	}

	public List<Enemy> findTargets(List<Enemy> allTargets) {
		return new ArrayList<Enemy>();
	}

	public Enemy getTarget(List<Enemy> validTargets) {
		return null;
	}

	public Shot fire(Enemy target) {
		Shot shot = new Shot(10, 10, IMG);
		return shot;
	}

	public Point2D getPosition() {
		return pos;
	}
}
