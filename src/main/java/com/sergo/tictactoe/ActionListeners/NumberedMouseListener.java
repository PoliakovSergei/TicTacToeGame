package com.sergo.tictactoe.ActionListeners;

import com.sergo.tictactoe.AppLauncher;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NumberedMouseListener implements MouseListener {
    private int num;

    public int getNum(){
        return num;
    }

    public NumberedMouseListener(int num){
        super();
        this.num = num;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
