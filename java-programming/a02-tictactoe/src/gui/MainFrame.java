package gui;

import game.XOGame;
import game.control.Shape;
import gui.panels.GamePanel;
import gui.panels.MenuPanel;
import utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends Frame implements ActionListener {

    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private XOGame gameInstance = null;

    public MainFrame() {
        this(null, 800, 600);
    }

    public MainFrame(String title) {
        this(title, 800, 600);
    }

    public MainFrame(String title, int width, int height) {
        super(new BorderLayout(), Color.WHITE);
        this.setTitle(title);
        this.setSize(width, height);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void initComponents() {
        this.gamePanel = new GamePanel();
        this.menuPanel = new MenuPanel();

        this.add(this.menuPanel, BorderLayout.NORTH);
        this.add(this.gamePanel, BorderLayout.CENTER);
    }

    @Override
    protected void initListeners() {

        // Listeners for menuPanel
        this.menuPanel.getMode().addActionListener(this);
        this.menuPanel.getClear().addActionListener(this);
        this.menuPanel.getStat().addActionListener(this);

        // Listeners for gamePanel
        for (int i = 0; i < this.gamePanel.getButtons().length; i++) {
            this.gamePanel.getButtons()[i].addActionListener(this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {
//        if (event.getSource() == this.menuPanel.getStat()) {
//            this.statFrame.view();
//        }
        if (checkButtonPressed(event) != null) {
            JButton buttonPressed = checkButtonPressed(event);
            int index = checkButtonPressedIndex(event);
            if (this.gameInstance.getTurns() == null) {
                Logger.logMessage("Game has not started yet!", this, true);
            } else if (this.gameInstance.getTurnNumber() % 2 == 0) {
                this.gameInstance.getBoard().setShape(index, Shape.X);
                buttonPressed.setText("X");
            } else {
                this.gameInstance.getBoard().setShape(index, Shape.O);
                buttonPressed.setText("O");
            }

            // Print board in console
            this.gameInstance.getBoard().printBoard();
            this.gameInstance.nextTurn();
        }
    }

    private JButton checkButtonPressed(ActionEvent event) {
        for (int i = 0; i < this.gamePanel.getButtons().length; i++) {
            if (event.getSource() == this.gamePanel.getButtons()[i]) {
                return this.gamePanel.getButtons()[i];
            }
        }
        return null;
    }

    private int checkButtonPressedIndex(ActionEvent event) {
        for (int i = 0; i < this.gamePanel.getButtons().length; i++) {
            if (event.getSource() == this.gamePanel.getButtons()[i]) {
                return i;
            }
        }
        return -1;
    }

    // Getters and Setters
    public void setGameInstance(XOGame gameInstance) {
        this.gameInstance = gameInstance;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

}
