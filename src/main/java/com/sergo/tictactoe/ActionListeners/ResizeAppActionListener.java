package com.sergo.tictactoe.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sergo.tictactoe.AppLauncher;

public class ResizeAppActionListener implements ActionListener {
    private AppLauncher app;
    private int resizeParam;
    public ResizeAppActionListener(AppLauncher app, int resizeParam){
        this.app = app;
        this.resizeParam = resizeParam;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        app.setSize(resizeParam,resizeParam);
    }
}
