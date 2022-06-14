package com.example.chess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window extends JFrame implements ActionListener {
    JFrame frame;
    Settings settings;
    Figures figures;
    Board board;
    Actions actions;
    GameLogic gameLogic;

    Window() throws IOException {
        this.figures = new Figures();
        this.settings = new Settings();
        this.board = new Board(figures, settings);
        this.frame = new JFrame(settings.getTitle());
        this.gameLogic = new GameLogic(settings, figures);
        this.actions = new Actions(board, figures, settings, gameLogic);
        frame.add(board);
        frame.setContentPane(board);
        frame.addMouseListener(actions);
        frame.addMouseMotionListener(actions);
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(settings.getWIDTH(), settings.getHEIGHT());
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setUndecorated(true);
        frame.setVisible(settings.getVisible());
        frame.setResizable(settings.getResizable());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
