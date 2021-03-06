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
    Computer computer;
    FigureValues figureValues;

    Window() throws IOException {
        this.figures = new Figures();
        this.settings = new Settings(figures);
        this.frame = new JFrame(settings.getTitle());
        this.gameLogic = new GameLogic(settings, figures);
        this.figureValues = new FigureValues();
        this.computer = new Computer(settings, gameLogic, figureValues);
        this.board = new Board(figures, settings, gameLogic);
        this.actions = new Actions(board, figures, settings, gameLogic, computer);
        frame.add(board);
        frame.setContentPane(board);
        frame.addMouseListener(actions);
        frame.addMouseMotionListener(actions);
        JFrame.setDefaultLookAndFeelDecorated(true);
        //frame.setSize(settings.getWIDTH(), settings.getHEIGHT());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(settings.getVisible());
        frame.setResizable(settings.getResizable());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //rook.setVisible(false);
        frame.add(settings.getQueen());
        frame.add(settings.getBishop());
        frame.add(settings.getRook());
        frame.add(settings.getKnight());
        frame.add(settings.getRestart());
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
