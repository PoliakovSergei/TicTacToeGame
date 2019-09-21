package com.sergo.tictactoe;

import com.google.common.collect.Table;
import com.sergo.tictactoe.ActionListeners.NumberedMouseListener;
import com.sergo.tictactoe.ActionListeners.ResizeAppActionListener;
import com.sergo.tictactoe.Actions.ExitAction;
import com.sergo.tictactoe.enums.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class AppLauncher extends JFrame {

    private static AppLauncher instance;
    private static CellState winner = null;
    private final static int windowSize = 300;
    private static CellState[][] gameField = new CellState[3][3];
    private static boolean isAIon = false;
    private static boolean isCrossTurn = true;
    private static boolean isNoughtTurn = false;
    private static boolean isGameEnded = false;

    public static void main(String[] args) {
        AppLauncher app = AppLauncher.getInstance();
        ((JLabel) app.getContentPane().getComponent(11)).setText("Ходит: 'X'");
        app.setVisible(true);
        gameFunction();
    }

    private static void gameFunction() {
        while (true) {
            waitForCrossTurn();
            if (checkGameEndState()){
                endGameProcess();
            }
            else {
                waitForNoughtTurn();
                if (checkGameEndState()) {
                    endGameProcess();
                }
            }
        }
    }

    private static boolean waitForNoughtTurn() {
        isNoughtTurn = true;
        while (isNoughtTurn) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    private static boolean waitForCrossTurn() {
        isCrossTurn = true;
        while (isCrossTurn) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    private static boolean checkGameEndState() {
        if ((winner = checkEndByRows()) == null) {
            if ((winner = checkEndByColumns()) == null) {
                if ((winner = checkEndByDiagonals()) == null) {
                    if((winner = checkEndByDraw()) == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean endGameProcess() {
        isGameEnded = true;
        if(winner != CellState.EMPTY){
            System.out.println(winner.toString() + " is winner!");
            int choose = JOptionPane.showConfirmDialog(getInstance(),
                    winner.toString() + " won! Restart?",
                    "We found a winner!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            if(choose == JOptionPane.YES_OPTION){
                restartGame();
            }
        }
        else{
            System.out.println("Draw!");
            int choose = JOptionPane.showConfirmDialog(getInstance(),
                    "Draw! Restart?",
                    "Draw!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            if(choose == JOptionPane.YES_OPTION){
                restartGame();
            }
        }
        return true;
    }

    private static CellState checkEndByDraw(){
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (gameField[i][j] == CellState.EMPTY ) {
                    return null;
                }
            }
        }
        return CellState.EMPTY;
    }

    private static CellState checkEndByRows() {
        for (int i = 0; i < 3; i++) {
            if (gameField[i][0] != CellState.EMPTY && gameField[i][0] == gameField[i][1] && gameField[i][1] == gameField[i][2]) {
                return gameField[i][0];
            }
        }
        return null;
    }

    private static CellState checkEndByColumns() {
        for (int i = 0; i < 3; i++) {
            if (gameField[0][i] != CellState.EMPTY && gameField[0][i] == gameField[1][i] && gameField[1][i] == gameField[2][i]) {
                return gameField[0][i];
            }
        }
        return null;
    }

    private static CellState checkEndByDiagonals() {
        if (gameField[0][0] != CellState.EMPTY && gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2]) {
            return gameField[0][0];
        }
        if (gameField[0][2] != CellState.EMPTY && gameField[0][2] == gameField[1][1] && gameField[1][1] == gameField[2][0]) {
            return gameField[0][2];
        }
        return null;
    }

    private AppLauncher() {
        super("TicTacToe!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(this));
        setLayout(new GridLayout(4, 3, 5, 5));
        setSize(windowSize, windowSize);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = CellState.EMPTY;
            }
        }
    }

    private static void fillWithComponents() {
        for (int i = 0; i < 9; i++) {
            gameCell gamePanel = new gameCell(i);
            gamePanel.addMouseListener(new NumberedMouseListener(i) {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (((gameCell) getInstance().getContentPane().getComponent(getNum())).getCurrState() == CellState.EMPTY
                            && !getInstance().getGameEndState()) {
                        if (AppLauncher.isCrossTurn) {
                            AppLauncher.isCrossTurn = false;
                            ((JLabel) instance.getContentPane().getComponent(11)).setText("Ходит: 'O'");
                            (instance.getGameField())[getNum() / 3][getNum() % 3] = CellState.CROSS;
                            //instance.getContentPane().getComponent(getNum()).setBackground(Color.RED);
                            ((gameCell)instance.getContentPane().getComponent(getNum())).repaint(CellState.CROSS);
                        } else {
                            if (!isAIon) {
                                AppLauncher.isNoughtTurn = false;
                                ((JLabel) instance.getContentPane().getComponent(11)).setText("Ходит: 'X'");
                                (instance.getGameField())[getNum() / 3][getNum() % 3] = CellState.NOUGHT;
                                //instance.getContentPane().getComponent(getNum()).setBackground(Color.blue);
                                ((gameCell)instance.getContentPane().getComponent(getNum())).repaint(CellState.NOUGHT);
                            }
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    gamePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    gamePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            gamePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
            instance.add(gamePanel);
        }
        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel("");
            instance.add(label);
        }
    }

    private static JMenuBar createMenuBar(AppLauncher app) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createGameMenu());
        menuBar.add(createOpponentChooseMenu());
        menuBar.add(createViewMenu(app));
        return menuBar;
    }

    private static JMenu createGameMenu() {
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem exitButton = new JMenuItem(new ExitAction());
        JMenuItem restartButton = new JMenuItem("Рестарт");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppLauncher.restartGame();
            }
        });

        gameMenu.add(restartButton);
        gameMenu.add(exitButton);
        return gameMenu;
    }

    private static void restartGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = CellState.EMPTY;
                ((gameCell)getInstance().getContentPane().getComponent(i * 3 + j)).repaint(CellState.EMPTY);
            }
        }
        ((JLabel) getInstance().getContentPane().getComponent(11)).setText("Ходит: 'Х'");
        winner = null;
        isNoughtTurn = false;
        isCrossTurn = true;
        isGameEnded = false;
    }

    private static JMenu createViewMenu(AppLauncher app) {
        JMenu viewMenu = new JMenu("Размер окна");
        final int wSizesCount = 6;
        final int wSizeStep = 50;
        final int startSize = 250;
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = startSize; i <= startSize + wSizesCount * wSizeStep; i += wSizeStep) {
            JRadioButtonMenuItem menuButton = new JRadioButtonMenuItem(
                    i + "x" + i);
            menuButton.addActionListener(new ResizeAppActionListener(app, i));
            buttonGroup.add(menuButton);
            viewMenu.add(menuButton);
            if (i == AppLauncher.windowSize) {
                menuButton.setSelected(true);
            }
        }
        return viewMenu;
    }

    private static JMenu createOpponentChooseMenu() {
        JMenu oppMenu = new JMenu("Выбор противника");
        JRadioButtonMenuItem playerWithPlayer = new JRadioButtonMenuItem("Player VS Player");
        JRadioButtonMenuItem playerWithAI = new JRadioButtonMenuItem("Player VS AI (In progress)");

        playerWithPlayer.addActionListener(actionEvent -> isAIon = false);
        playerWithAI.addActionListener(actionEvent -> isAIon = true);

        ButtonGroup buttonGroup = new ButtonGroup();
        playerWithPlayer.setSelected(true);
        buttonGroup.add(playerWithPlayer);
        buttonGroup.add(playerWithAI);

        oppMenu.add(playerWithPlayer);
        oppMenu.add(playerWithAI);

        return oppMenu;
    }

    public static int getWindowSize() {
        return windowSize;
    }

    public boolean getGameEndState() {
        return isGameEnded;
    }

    public static AppLauncher getInstance() {
        if (instance == null) {
            instance = new AppLauncher();
            fillWithComponents();
        }
        return instance;
    }

    public CellState[][] getGameField() {
        return gameField;
    }
}
