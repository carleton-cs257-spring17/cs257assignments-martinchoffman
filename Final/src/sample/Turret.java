package sample;

import javafx.scene.image.Image;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Martin on 6/3/2017.
 */
public class Turret extends ImageView implements Tower_Interface {
	private final int COST = 10;
	private final int MAX_HP = 50;
	private final double ROF = .5;

	private final Image IMG = new Image("file:res/shot.png");

	private int hp;
	private Point2D pos;

	public Turret() {
		this.hp = MAX_HP;
	}

	public int getCost() {
		return COST;
	}

	public int getHealth() {
		return hp;
	}

	public double getROF() {
		return ROF;
	}

	public List<Enemy> findTargets(List<Enemy> allTargets) {
		return new ArrayList<Enemy>();
	}

	public Enemy getTarget(List<Enemy> validTargets) {
		return null;
	}

	public Shot fire() {
		Shot shot = new Shot(10, 10, null);
		shot.setFill(Color.YELLOW);
		shot.setCenterX(getPosition().getX());
		shot.setCenterY(getPosition().getY());
		shot.setRadius(5);
		return shot;
	}

	public Shot fire(Enemy target) {
		Shot shot = new Shot(10, 10, IMG);
		return shot;
	}

	public void setPosition(int row, int col, double grid_offset_y, double grid_offset_x, double total_tile_size) {
		Double x = grid_offset_x + col * total_tile_size + total_tile_size / 4;
		Double y = grid_offset_y + row * total_tile_size + total_tile_size / 4;

		Point2D point = new Point2D.Double(x, y);
		this.pos = point;
	}

	public Point2D getPosition() {
		return pos;
	}
}
