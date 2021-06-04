package io.github.email.client.ui.components.icons;

import io.github.email.client.util.ImageLoader;
import javafx.scene.image.Image;

public class SimpleEmailIcon {

    private static final String icon = "images/icon/Envelope-icon.png";

    private SimpleEmailIcon() {
        throw new IllegalStateException("Utility class");
    }

    public static Image getIcon() {
        ImageLoader imageLoader = new ImageLoader();
        return imageLoader.getImage(icon);
    }
}
