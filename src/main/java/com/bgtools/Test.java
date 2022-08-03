package com.bgtools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class Test extends JFrame {

    private TrayIcon trayIcon;
    private SystemTray systemTray;

    public Test() {
super("系統圖示");
        systemTray = SystemTray.getSystemTray();
        setSize(150, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        try {
            trayIcon = new TrayIcon(ImageIO.read(new File("E:\\SL-Project\\JavaProjects\\BgTools\\src\\main\\resources\\logo-bg.ico")));
            systemTray.add(trayIcon);

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (AWTException e2) {
            e2.printStackTrace();
        }
        this.addWindowListener(new WindowAdapter() {
                                   @Override
                                   public void windowIconified(WindowEvent e) {
                                       dispose();
                                   }
                               }
        );
        trayIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2)
                            setExtendedState(Frame.NORMAL);
                        setVisible(true);
                    }
                }
        );


    }

public static void main(String args[]){
        new Test();
}
}
