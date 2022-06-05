package com.example.chess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    JFrame frame;
    Settings settings;
    Pictures pictures;
    Board board;

    Window() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.board = new Board();
        this.pictures = new Pictures();
        this.settings = new Settings();
        this.frame = new JFrame(settings.getTitle());
        frame.add(board);
        frame.setSize(settings.getWIDTH(), settings.getHEIGHT());
        frame.setVisible(settings.getVisible());
        frame.setResizable(settings.getResizable());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawTable();
    }


    public void drawTable() {
        //System.out.println(pictures.getIcons());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
