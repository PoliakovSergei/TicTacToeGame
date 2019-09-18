package com.sergo.tictaktoe;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import com.sergo.tictaktoe.ActionListeners.*;

public class AppLauncher extends JFrame {

    private static AppLauncher instance;
    private final static int windowSize = 300;

    public static void main(String[] args) {
        JFrame app = AppLauncher.getInstance();
        app.setVisible(true);
    }

    private AppLauncher() {
        super("TicTacToe!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(this));
        setLayout(new GridLayout(3,3,5,5));
        fillWithComponents(this);
        setSize(windowSize,windowSize);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2- this.getSize().width/2,
                Toolkit.getDefaultToolkit().getScreenSize().height/2 - this.getSize().height/2);
    }

    private static AppLauncher fillWithComponents(AppLauncher app){
        for(int i = 0; i < 9; i++){
            JPanel gamePanel = new JPanel();
            gamePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //TODO
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //TODO
                    gamePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //TODO
                    gamePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //TODO
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //TODO
                }
            });
            gamePanel.setSize(150,150);
            gamePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
            app.add(gamePanel);
        }
        return app;
    }

    private static JMenuBar createMenuBar(AppLauncher app) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createOpponentChooseMenu());
        menuBar.add(createViewMenu(app));
        return menuBar;
    }

    private static JMenu createViewMenu(AppLauncher app) {
        JMenu viewMenu = new JMenu("Размер окна");
        final int wSizesCount = 6;
        final int wSizeStep = 50;
        final int startSize = 250;
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = startSize; i <= startSize + wSizesCount*wSizeStep; i+=wSizeStep){
            JRadioButtonMenuItem menuButton = new JRadioButtonMenuItem(
                    String.valueOf(i)+"x"+String.valueOf(i));
            menuButton.addActionListener(new resizeAppActionListener(app,i));
            buttonGroup.add(menuButton);
            viewMenu.add(menuButton);
            if(i == AppLauncher.windowSize){
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
                //TODO переключение на режим 1х1
            }
        });
        playerWithAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO переключение на режим 1хAI
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

    public static int getWindowSize(){
        return windowSize;
    }

    public static AppLauncher getInstance() {
        if (instance == null) {
            instance = new AppLauncher();
        }
        return instance;
    }
}
