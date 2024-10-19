package com.dt181g.laboration_3.support;

import javax.swing.JPanel;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Graphics;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;


public class BackgroundPanel extends JPanel{
    private List<Image> backgroundImages = new ArrayList<>();
    Random randomizer = new Random();
    Image image;
    private boolean shouldSwitchBackground = false;
    Image newImage;

    public BackgroundPanel(String imagePath, String[] backgroundsOrBackground) {
        loadImages(imagePath, backgroundsOrBackground);
    }

    private void loadImages(String imagePath, String[] backgroundsOrBackground) {
        for (String background : backgroundsOrBackground) {
            try (InputStream is = getClass().getResourceAsStream(imagePath + background)) {
                Image imageIcon = ImageIO.read(is);
                backgroundImages.add(imageIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        image = backgroundImages.get(0);
    }

    /**
     * Helper method to fetch a random background from the list if there are multiple of them.
     * @return the new background.
     */
    private Image getRandomBackground() {
        newImage = backgroundImages.get(randomizer.nextInt(backgroundImages.size()));

        while (newImage.equals(image)) {
            newImage = backgroundImages.get(randomizer.nextInt(backgroundImages.size()));
        }

        return newImage;
    }

    /**
     * <h3>Method to enable background repaint.</h3>
     *
     * <p>
     * Should be set by views in the MVC pattern when re-displaying
     * its view if they want to dynamically switch their backgrounds.
     * </p>
     * @param shouldRepaintBackground boolean to determine if background should be repainted.
     */
    public void toggleBackgroundRepaint(boolean shouldRepaintBackground) {
        shouldSwitchBackground = shouldRepaintBackground;

        if (shouldSwitchBackground) {
            image = getRandomBackground();
            shouldSwitchBackground = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image, scaling it to fill the entire panel
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
