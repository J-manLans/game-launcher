package com.dt181g.laboration_3.view.launcher;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.dt181g.laboration_3.support.AppConfigLab3;

import java.util.List;
import java.util.Random;
import java.awt.Image;
import java.util.ArrayList;
import java.net.URL;
import java.nio.file.Path;

public class BackgroundPanel extends JPanel{
    private List<Image> backgroundImages = new ArrayList<>();
    Random randomizer = new Random();

    private void loadImages() {
        try {
            String folderPath = AppConfigLab3.PATH_TO_IMAGES;
            URL folderURL = getClass().getClassLoader().getResource(folderPath);
            if (folderURL != null) {
                Path path = java.nio.file.Paths.get(folderURL.toURI());
                java.nio.file.DirectoryStream<java.nio.file.Path> directoryStream = java.nio.file.Files.newDirectoryStream(path);

                for (java.nio.file.Path filePath : directoryStream) {
                    if (filePath.toString().endsWith(".png")) {
                        try (InputStream is = java.nio.file.Files.newInputStream(filePath)) {
                            backgroundImages.add(ImageIO.read(is));  // Load image
                        } catch (IOException e) {
                            System.err.println("Couldn't read file: " + filePath);
                        }
                    }
                }
                directoryStream.close();
            } else {
                System.err.println("Folder wasn't found: " + folderPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BackgroundPanel(String imagePath) {
        loadImages();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image, scaling it to fill the entire panel
        g.drawImage(backgroundImages.get(randomizer.nextInt(backgroundImages.size())), 0, 0, getWidth(), getHeight(), this);
    }
}
