package io.github.email.client.ui.components.background;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CustomBackground {
    private URL url;

    public CustomBackground(String image) {
        ClassLoader classLoader = getClass().getClassLoader();
        url = classLoader.getResource(image);
    }

    public Background getBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(
                createImage(),
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

    private Image createImage() {
        FileInputStream inputStream = null;
        try {
            URI uri = url.toURI();
            File file = new File(uri);
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new Image(inputStream);
    }
}
