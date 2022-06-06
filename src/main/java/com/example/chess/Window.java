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

    Window() throws IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);

        this.figures = new Figures();
        this.settings = new Settings(figures);
        this.board = new Board(figures, settings);
        this.frame = new JFrame(settings.getTitle());
        this.actions = new Actions(board, figures, settings);
        frame.add(board);
        frame.setContentPane(board);
        frame.addMouseListener(actions);
        frame.addMouseMotionListener(actions);
        frame.setSize(settings.getWIDTH(), settings.getHEIGHT());
        frame.setVisible(settings.getVisible());
        frame.setResizable(settings.getResizable());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
