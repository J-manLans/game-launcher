package com.dt181g.project.support;

import javax.swing.JPanel;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Graphics;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * A custom JPanel that supports dynamic background images.
 * <p>
 * This class loads multiple background images from a specified path and allows for
 * switching between them randomly. It is designed to be used by the views within an MVC architecture.
 * </p>
 *
 * @author Joel Lansgren
 */
public class BackgroundPanel extends JPanel{
    private final List<Image> backgroundImages = new ArrayList<>();
    private final Random randomizer = new Random();
    private Image image;
    private boolean shouldSwitchBackground;
    private Image newImage;

    /**
     * Constructs a BackgroundPanel with specified image path and background images.
     *
     * @param imagePath the path to the background images
     * @param backgroundsOrBackground an array of background image file names,
     * can also be a single background, but it needs to reside in an array
     */
    public BackgroundPanel(String imagePath, String[] backgroundsOrBackground) {
        loadImages(imagePath, backgroundsOrBackground);
    }

    /**
     * Loads images from the specified path into the backgroundImages list.
     * <p>
     * This method attempts to read each image file specified in the array and adds them
     * to the backgroundImages list. The first image loaded is set as the initial image.
     * </p>
     *
     * @param imagePath the path to the background images
     * @param backgroundsOrBackground an array of background image file names
     */
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
     * Fetches a random background from the list, ensuring it is different from the current image.
     *
     * @return a randomly selected background image
     */
    private Image getRandomBackground() {
        newImage = backgroundImages.get(randomizer.nextInt(backgroundImages.size()));

        while (newImage.equals(image)) {
            newImage = backgroundImages.get(randomizer.nextInt(backgroundImages.size()));
        }

        return newImage;
    }

    /**
     * Enables or disables the background repaint.
     *
     * <p>
     * This method should be called by views when they want to switch backgrounds dynamically.
     * If enabled, a new random background image will be selected and displayed.
     * </p>
     *
     * @param shouldRepaintBackground true to enable background repaint, false to disable
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
