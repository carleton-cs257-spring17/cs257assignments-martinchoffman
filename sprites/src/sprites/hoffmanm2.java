/**
 * hoffmanm2.java
 * Martin Hoffman, 21 May 2017
 *
 * It's Geralt!
 */
package sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class hoffmanm2 extends Sprite {
	private AudioClip audioClip;
	private ImageView imageView;
	private Map<String, AudioClip> sounds;

	public hoffmanm2() {
		Image image = new Image(getClass().getResourceAsStream("/res/geralt.png"));
		this.imageView = new ImageView();
		this.imageView.setImage(image);
		this.getChildren().add(this.imageView);

		sounds = new HashMap<String, AudioClip>();
		sounds.put("fireball", new AudioClip(getClass().getResource(
				"/res/sword.wav").toString()));
		sounds.put("sword", new AudioClip(getClass().getResource(
				"/res/crossbow.wav").toString()));
		sounds.put("crossbow", new AudioClip(getClass().getResource(
				"/res/fireball.wav").toString()));

		this.audioClip = sounds.get("fireball");
	}

	public void setSize(double width, double height) {
		super.setSize(width, height);
		this.imageView.setFitWidth(width);
		this.imageView.setFitHeight(height);
	}

	public void changeSound() {
		if (this.audioClip == sounds.get("fireball")) {
			this.audioClip = sounds.get("sword");
		} else if (this.audioClip == sounds.get("sword")) {
			this.audioClip = sounds.get("crossbow");
		} else if (this.audioClip == sounds.get("crossbow")) {
			this.audioClip = sounds.get("fireball");
		} else {
			System.out.println("Error changing audioClip");
		}
	}

	@Override
	public void step() {
		final double vx = super.getVelocity().getX();

		// Change image based on x velocity
		this.getChildren().remove(this.imageView);
		Image image;
		if (vx >= 0) {
			image = new Image(getClass().getResourceAsStream("/res/geralt2.png"));
		} else {
			image = new Image(getClass().getResourceAsStream("/res/geralt.png"));
		}
		this.imageView.setImage(image);
		this.getChildren().add(this.imageView);

		// Calculate two roll locations based on the scene width
		final double width = super.getScene().getWidth();
		final double roll1 = width * .25;
		final double roll2 = width * .75;

		final double rollLen = abs(vx * 8) + 1;

		// Roll at roll locations
		double x = super.getPosition().getX();
		if (x > roll1 && x < roll1 + rollLen && vx > 0.0) {
			this.getTransforms().add(new Rotate(45, this.getSize().getX() / 2.0,
					this.getSize().getY() / 2.0));
		} else if (x < roll2 && x > roll2 - rollLen && vx < 0.0) {
			this.getTransforms().add(new Rotate(-45, this.getSize().getX() / 2.0,
					this.getSize().getY() / 2.0));
		} else if (vx == 0) {
			this.getTransforms().add(new Rotate(-45, this.getSize().getX() / 2.0,
					this.getSize().getY() / 2.0));
		}
		super.step();
	}

	@Override
	public void makeSound() {
		this.audioClip.play();
		changeSound();
	}
}
