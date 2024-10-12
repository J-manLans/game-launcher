package com.dt181g.laboration_3.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.support.AppConfigLab3;

public class FakeTicTacToeView extends JPanel implements GameView{
    JLabel title = new JLabel();

    public FakeTicTacToeView(final String title) {
        this.title.setText(title);
        this.initializeStartMenu();
    }

    private void initializeStartMenu() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel gameLabel = new JLabel("Sorry! The game isn't available at the moment.");
        AppConfigLab3.LABEL_STYLING(gameLabel);

        this.setBackground(AppConfigLab3.DARKER_GREY);
        this.add(Box.createVerticalGlue());
        this.add(gameLabel);
        this.add(Box.createVerticalGlue());
    }

    @Override
    public void resetGame() {
        this.removeAll();
        initializeStartMenu();
    }

    @Override
    public JPanel getPanel() {
       return this;
    }

    @Override
    public String getTitle() {
       return AppConfigLab3.TIC_TAC_TOE_TITLE;
    }

    @Override
    public void startGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }
}
