package org.example.UI.themes;

import java.awt.*;

public class ColorUtils {

    public static Color getBrighterColor(Color baseColor, float factor) {
        if (factor < 0 || factor > 1) {
            throw new IllegalArgumentException("Factor must be between 0 and 1");
        }
        int r;
        int g;
        int b;
        r = (int) Math.min(255, (baseColor.getRed() + factor * 255));
        g = (int) Math.min(255, (baseColor.getGreen() + factor * 255));
        b = (int) Math.min(255, (baseColor.getBlue() + factor * 255));
        return new Color(r, g, b);
    }

    public static Color getDarkerColor(Color baseColor, float factor) {
        if (factor < 0 || factor > 1) {
            throw new IllegalArgumentException("Factor must be between 0 and 1");
        }
        int r;
        int g;
        int b;
        r = (int) Math.max(0, (baseColor.getRed() - factor * 255));
        g = (int) Math.max(0, (baseColor.getGreen() - factor * 255));
        b = (int) Math.max(0, (baseColor.getBlue() - factor * 255));
        return new Color(r, g, b);
    }
}
