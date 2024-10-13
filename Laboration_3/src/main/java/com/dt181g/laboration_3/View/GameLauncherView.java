package com.dt181g.laboration_3.view;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.support.DebugLogger;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class GameLauncherView extends JFrame{
    DebugLogger logger = DebugLogger.INSTANCE;

    private final JPanel gameSelectorPanel = new JPanel();
    private final JLabel pickAGameLabel = new JLabel(AppConfigLab3.PICK_A_GAME);
    private final JScrollPane scrollPane = new JScrollPane(gameSelectorPanel);
    private final JPanel gamePanel = new JPanel();
    private final List<JButton> gameIcons = new ArrayList<JButton>();

    public GameLauncherView() {
        // GameSelectorPanel
        gameSelectorPanel.setLayout(new BoxLayout(gameSelectorPanel, BoxLayout.Y_AXIS));
        gameSelectorPanel.setBackground(AppConfigLab3.DARK_GREY);
        AppConfigLab3.LABEL_STYLING(pickAGameLabel);
        gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));
        gameSelectorPanel.add(pickAGameLabel);

        // ScrollPane (handles size of gameSelectorPanel)
        this.setupScrollPane();

        // GamePanel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setPreferredSize(AppConfigLab3.GAME_PANEL_DIMENSIONS);
        gamePanel.setBackground(AppConfigLab3.DARKER_GREY);

        // JFrame
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        this.add(scrollPane);
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void addGameIcons(List<String> pathToIcon, List<String> titles) {
        for (int i = 0; i < pathToIcon.size(); i++) {
            // Set up the icon.
            JButton gameIcon = new JButton();
            gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameIcon.setBorder(AppConfigLab3.REMOVE_BORDER);
            gameIcon.setContentAreaFilled(false);
            gameIcon.setBorderPainted(false);
            gameIcon.setPreferredSize(AppConfigLab3.GAME_ICON_SIZE);
            gameIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Fetch the icon image.
            gameIcon.setIcon(loadIcon(pathToIcon.get(i)));
            // Set the action command od the icon to the game title,
            // to let the action listener know which game was clicked.
            gameIcon.setActionCommand(titles.get(i));

            // Add icon to the panel, plus a distance to create some air.
            gameSelectorPanel.add(gameIcon);
            gameSelectorPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20));

            // Add the icon to the icon list so action listeners can be attached at a later stage.
            gameIcons.add(gameIcon);
        }
        // Adds flexible space below the icons,
        // allowing the panel to respect the preferred size of the buttons.
        gameSelectorPanel.add(Box.createVerticalGlue());
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

    public void addGameIconListener(ActionListener listenForGameIconClicks) {
        for (JButton gameBtn : gameIcons) {
            gameBtn.addActionListener(listenForGameIconClicks);
        }
    }

    public void loadGame(GameView gameView) {
        // Clears the panel and adds the game view.
        gamePanel.removeAll();
        gamePanel.add(gameView.getPanel(), BorderLayout.CENTER);

        // Redraws the panel
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
