/**
 * Moose.java
 * Jeff Ondich, 10/29/14.
 *
 * A sample subclass of Sprite for CS257.
 */
package sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Moose extends Sprite {
    private AudioClip audioClip;
    private ImageView imageView;

    public Moose() {
        Image image = new Image(getClass().getResourceAsStream("/res/moose.png"));
        this.imageView = new ImageView();
        this.imageView.setImage(image);
        this.getChildren().add(this.imageView);

        this.audioClip = new AudioClip(getClass().getResource("/res/moose.wav").toString());
    }

    public void setSize(double width, double height) {
        super.setSize(width, height);
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
    }

    @Override
    public void makeSound() {
        this.audioClip.play();
    }
}
