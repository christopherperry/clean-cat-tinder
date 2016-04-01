package com.github.cleancattinder.catswiping;

import java.awt.*;

import javax.swing.*;

class ImagePanel extends JPanel {

    private final Image image;

    ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Insets insets = getInsets();
        g.drawImage(image, insets.left, insets.top, this);
    }
}
