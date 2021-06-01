package io.github.email.client.util;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ImageLoader {

    public Image getImage(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(name);

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
