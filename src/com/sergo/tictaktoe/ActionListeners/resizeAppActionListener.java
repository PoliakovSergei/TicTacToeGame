package com.sergo.tictaktoe.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sergo.tictaktoe.AppLauncher;

public class resizeAppActionListener implements ActionListener {
    private AppLauncher app;
    private int resizeParam;
    public resizeAppActionListener(AppLauncher app, int resizeParam){
        this.app = app;
        this.resizeParam = resizeParam;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        app.setSize(resizeParam,resizeParam);
    }
}
