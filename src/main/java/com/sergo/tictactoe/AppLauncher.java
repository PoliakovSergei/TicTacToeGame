package com.sergo.tictactoe;

import com.sergo.tictactoe.ActionListeners.resizeAppActionListener;
import com.sergo.tictactoe.Actions.ExitAction;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

public class AppLauncher extends JFrame {

    private static AppLauncher instance;
    private final static int windowSize = 300;
    private static CellState gameField[][] = new CellState[3][3];
    private static boolean isAIon = false;
    private static boolean isCrossTurn = true;
    private static boolean isNoughtTurn = false;

    public static void main(String[] args) {
        AppLauncher app = AppLauncher.getInstance();
        ((JLabel) app.getContentPane().getComponent(11)).setText("Ходит: 'X'");
        app.setVisible(true);
        gameFunction();
    }

    private static boolean gameFunction() {
        while (true) {
            checkGameEndState();
            //TODO
            waitForCrossTurn();
            waitForNoughtTurn();
        }
    }

    private static boolean waitForNoughtTurn(){
        isNoughtTurn = true;
        while(isNoughtTurn){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }
            catch (InterruptedException e){}
        }
        return true;
    }

    private static boolean waitForCrossTurn(){
        isCrossTurn = true;
        while(isCrossTurn){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }
            catch (InterruptedException e){}
        }
        return true;
    }

    private static boolean checkGameEndState() {
        CellState winner = null;
        if((winner = checkEndByRows())==null){
            if((winner = checkEndByColumns())==null) {
                if ((winner = checkEndByDiagonals()) == null) {
                    return false;
                }
            }
        }
        endGameProcess(winner);
        return true;
    }

    private static boolean endGameProcess(CellState winner){
        //TODO
        // Вывод окна с победившей стороной
        return true;
    }

    private static CellState checkEndByRows(){
        for(int i = 0; i < 3; i++){
            if(gameField[i][0]!=CellState.EMPTY&&gameField[i][0]==gameField[i][1]&&gameField[i][1]==gameField[i][2]){
                return gameField[i][0];
            }
        }
        return null;
    }
    private static CellState checkEndByColumns(){
        for(int i = 0; i < 3; i++){
            if(gameField[0][i]!=CellState.EMPTY&&gameField[0][i]==gameField[1][i]&&gameField[1][i]==gameField[2][i]){
                return gameField[0][i];
            }
        }
        return null;
    }
    private static CellState checkEndByDiagonals(){
        if(gameField[0][0]!=CellState.EMPTY&&gameField[0][0]==gameField[1][1]&&gameField[1][1]==gameField[2][2]){
            return gameField[0][0];
        }
        if(gameField[0][2]!=CellState.EMPTY&&gameField[0][2]==gameField[1][1]&&gameField[1][1]==gameField[2][0]){
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
            JPanel gamePanel = new JPanel();
            gamePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(AppLauncher.isCrossTurn){
                        //TODO
                        AppLauncher.isCrossTurn = false;
                        ((JLabel) instance.getContentPane().getComponent(11)).setText("Ходит: 'O'");
                    }
                    else{
                        if(!isAIon){
                            //TODO second player mechanic
                            AppLauncher.isNoughtTurn = false;
                            ((JLabel) instance.getContentPane().getComponent(11)).setText("Ходит: 'X'");
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

    private static void restartGame(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = CellState.EMPTY;
            }
        }
        ((JLabel) getInstance().getContentPane().getComponent(11)).setText("Ходит: 'Х'");
        //TODO Закрытие информационных окон
    }

    private static JMenu createViewMenu(AppLauncher app) {
        JMenu viewMenu = new JMenu("Размер окна");
        final int wSizesCount = 6;
        final int wSizeStep = 50;
        final int startSize = 250;
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = startSize; i <= startSize + wSizesCount * wSizeStep; i += wSizeStep) {
            JRadioButtonMenuItem menuButton = new JRadioButtonMenuItem(
                    String.valueOf(i) + "x" + String.valueOf(i));
            menuButton.addActionListener(new resizeAppActionListener(app, i));
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
        JRadioButtonMenuItem playerWithAI = new JRadioButtonMenuItem("Player VS AI");

        playerWithPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAIon = false;
            }
        });
        playerWithAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAIon = true;
            }
        });

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

    public static AppLauncher getInstance() {
        if (instance == null) {
            instance = new AppLauncher();
            fillWithComponents();
        }
        return instance;
    }

    enum CellState {
        NOUGHT{
            @Override
            public String toString() {
                return "Noughts";
            }
        },
        CROSS{
            @Override
            public String toString(){
                return "Crosses";
            }
        },
        EMPTY
    }
}
