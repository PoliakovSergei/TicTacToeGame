package com.sergo.tictactoe;

import com.sergo.tictactoe.enums.CellState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class gameCell extends JPanel {
    private int panelNum;
    private CellState currState = CellState.EMPTY;
    public gameCell(int number){
        super();
        panelNum = number;
    }
    public int getPanelNum(){
        return panelNum;
    }

    public CellState getCurrState(){
        return currState;
    }

    public void setCurrState(CellState newState){
        currState = newState;
    }

    public void repaint(CellState state){
        currState = state;
        super.repaint();
    }

    public void paint(Graphics g){
        super.paint(g);
        Image im = null;
        switch (currState){
            case CROSS:
                try {
                    im = ImageIO.read(new File(System.getProperty("user.dir")+"/img/cross.png"));
                } catch (IOException e) {}
                break;
            case NOUGHT:
                try {
                    im = ImageIO.read(new File(System.getProperty("user.dir")+"/img/nought.png"));
                } catch (IOException e) {}
                break;
            case EMPTY:
                im=null;
                break;
        }
        if(im!=null){
            g.drawImage(im.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_FAST), 0, 0, null);
        }
    }
}
