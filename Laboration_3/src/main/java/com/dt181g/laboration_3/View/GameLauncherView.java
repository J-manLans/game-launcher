package com.dt181g.laboration_3.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.dt181g.laboration_3.support.AppConfigLab3;

public class GameLauncherView extends JFrame{
    private final JPanel gameSelectorPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel(AppConfigLab3.PICK_A_GAME);
    private final JScrollPane scrollPane = new JScrollPane(gameSelectorPanel);
    private final JPanel gamePanel = new JPanel();

    public GameLauncherView() {
        // GameSelectorPanel
        gameSelectorPanel.setLayout(new BoxLayout(gameSelectorPanel, BoxLayout.Y_AXIS));
        gameSelectorPanel.setBackground(AppConfigLab3.DARK_GREY);

        pickAGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickAGameLabel.setFont(AppConfigLab3.MONOSPACE_BOLD);
        pickAGameLabel.setForeground(Color.WHITE);
        pickAGameLabel.setBorder(AppConfigLab3.PICK_A_GAME_BORDER);

        gameSelectorPanel.add(pickAGameLabel);

        // ScrollPane
        this.setupScrollPane();

        // GamePanel
        gamePanel.setPreferredSize(AppConfigLab3.GAME_PANEL_DIMENSIONS);
        gamePanel.setBackground(AppConfigLab3.DARKER_GREY);

        // JFrame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(scrollPane);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addGameButtons(List<String> pathToImage, Runnable gameToLoad) {
        for (int i = 0; i < pathToImage.size(); i++) {
            JButton button = new JButton();
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBorder(AppConfigLab3.REMOVE_BORDER);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            try {
                BufferedImage originalImage = ImageIO.read(new File(pathToImage.get(i)));
                Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                e.printStackTrace();
            }

            button.setPreferredSize(AppConfigLab3.GAME_ICON_SIZE);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addActionListener(e -> gameToLoad.run());

            gameSelectorPanel.add(button);
            gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        }
    }

    public void loadGame() {
        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();

        Timer timer = new Timer(300, e -> {
            JLabel gameLabel = new JLabel("Game Loaded!", SwingConstants.CENTER);
            gameLabel.setForeground(Color.WHITE);
            gamePanel.add(gameLabel);

            gamePanel.revalidate();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void setupScrollPane() {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(AppConfigLab3.REMOVE_BORDER);
        scrollPane.setPreferredSize(AppConfigLab3.GAME_SELECTOR_PANEL_DIMENSIONS);

        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getValue() + (notches * 20));
            }
        });
    }

    public JPanel getGameSelectorPanel() {
        return gameSelectorPanel;
    }
}
