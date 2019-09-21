package com.sergo.tictactoe.Actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction
{
    private static final long serialVersionUID=1L;
    public ExitAction(){
    putValue(NAME,"Выход");
    }
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }
}