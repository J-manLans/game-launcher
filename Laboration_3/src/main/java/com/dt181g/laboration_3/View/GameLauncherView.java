package com.dt181g.laboration_3.view;


import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

public class GameLauncherView extends JFrame{
    DebugLogger logger = DebugLogger.INSTANCE;

    private final JPanel gameSelectorPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel(AppConfigLab3.PICK_A_GAME);
    private final JScrollPane scrollPane = new JScrollPane(gameSelectorPanel);
    private final JPanel gamePanel = new JPanel();

    SnakePanelView snakePanelView = new SnakePanelView();

    public GameLauncherView() {
        // GameSelectorPanel
        gameSelectorPanel.setLayout(new BoxLayout(gameSelectorPanel, BoxLayout.Y_AXIS));
        gameSelectorPanel.setBackground(AppConfigLab3.DARK_GREY);

        AppConfigLab3.LABEL_STYLING(pickAGameLabel);

        gameSelectorPanel.add(pickAGameLabel);

        // ScrollPane
        this.setupScrollPane();

        // GamePanel
        gamePanel.setLayout(new BorderLayout());
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

    public void addGameButtons(List<String> pathToIcon, List<String> titles) {
        for (int i = 0; i < pathToIcon.size(); i++) {
            JButton button = new JButton();
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBorder(AppConfigLab3.REMOVE_BORDER);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            button.setIcon(loadIcon(pathToIcon.get(i)));

            button.setPreferredSize(AppConfigLab3.GAME_ICON_SIZE);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            final String title = titles.get(i);
            button.addActionListener(e -> this.loadGame(title));

            gameSelectorPanel.add(button);
            gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        }
    }

    private ImageIcon loadIcon(String path) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadGame(String title) {
        SwingUtilities.invokeLater(() -> {
            gamePanel.removeAll();

            switch (title) {
                case AppConfigLab3.SNAKE_TITLE -> {
                    JPanel snakePanel = snakePanelView.getPanel();
                    gamePanel.add(snakePanel, BorderLayout.CENTER);

                    snakePanelView.resetGame();
                }
                default -> {
                    JLabel gameLabel = new JLabel("Sorry! The game isn't available at the moment.");
                    gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    gameLabel.setForeground(AppConfigLab3.WHITE);
                    gameLabel.setFont(AppConfigLab3.MONOSPACE_BOLD);

                    gamePanel.add(gameLabel, BorderLayout.CENTER);
                }
            }

            gamePanel.revalidate();
            gamePanel.repaint();
        });
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

    public JPanel getGamePanel() {
        return gamePanel;
    }
}
