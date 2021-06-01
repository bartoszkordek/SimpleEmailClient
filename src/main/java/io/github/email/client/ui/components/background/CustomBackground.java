package io.github.email.client.ui.components.background;

import io.github.email.client.util.ImageLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class CustomBackground {

    private CustomBackground() {
        throw new IllegalStateException("Utility class");
    }

    public static Background getBackground(String name) {
        ImageLoader imageLoader = new ImageLoader();
        Image image = imageLoader.getImage(name);

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        1.0,
                        1.0,
                        true,
                        true,
                        false,
                        false
                )
        );
        return new Background(backgroundImage);
    }
}
