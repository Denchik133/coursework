package org.example.UI;

import org.example.UI.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IconFactory {
    private static final int ICON_IMAGE_SIZE = 20;

    public static ImageIcon getClearIcon(Theme theme) {
        ImageIcon icon = new ImageIcon(IconFactory.class.getResource("/Images/clear.png"));
        return resize(new ImageIcon(getColoredIcon(icon.getImage(), getContrastColorTo(theme))));
    }

    public static ImageIcon getMessageIcon(Theme theme) {
        ImageIcon icon = new ImageIcon(IconFactory.class.getResource("/Images/message.png"));
        return resize(new ImageIcon(getColoredIcon(icon.getImage(), getContrastColorTo(theme))));
    }

    public static ImageIcon getEncryptedMessageIcon(Theme theme) {
        ImageIcon icon = new ImageIcon(IconFactory.class.getResource("/Images/encMessage.png"));
        return resize(new ImageIcon(getColoredIcon(icon.getImage(), getContrastColorTo(theme))));
    }

    public static ImageIcon getUserIcon(Theme theme) {
        ImageIcon icon = new ImageIcon(IconFactory.class.getResource("/Images/user.png"));
        return resize(new ImageIcon(getColoredIcon(icon.getImage(), getContrastColorTo(theme))));
    }

    public static Image getColoredIcon(Image image, Color color) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(color);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g2d.dispose();
        return bufferedImage;
    }

    private static ImageIcon resize(ImageIcon original) {
        Image imageScaled = original.getImage().getScaledInstance(ICON_IMAGE_SIZE, ICON_IMAGE_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(imageScaled);
    }

    public static Color getContrastColorTo(Color color) {
        return null;
    }

    public static Color getContrastColorTo(Theme theme) {
        return theme.getMainText();
    }
}
