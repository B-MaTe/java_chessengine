package com.example.chess;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class Board extends JPanel {
    HashMap<String, int[]> figurePositions;
    String currFigure;
    Settings settings;
    Figures figures;
    Color darkColor, lightColor, possibleMoveColor;
    Board(Figures figures, Settings settings) {

        this.figures = figures;
        this.settings = settings;
        this.darkColor = settings.getDarkColor();
        this.lightColor = settings.getLightColor();
        this.possibleMoveColor = settings.getPossibleMoveColor();
    }

    public void paint(Graphics g) {
        super.paint(g);
        boolean isWhiteRowStart = true;
        char nextColor = 'w';
        int cellSize = settings.getCellSize();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (col == 0) {
                    if (isWhiteRowStart) {
                        g.setColor(lightColor);
                        nextColor = 'b';
                        isWhiteRowStart = false;
                    } else {
                        g.setColor(darkColor);
                        nextColor = 'w';
                        isWhiteRowStart = true;
                    }
                } else {
                    if (nextColor == 'w') {
                        g.setColor(lightColor);
                        nextColor = 'b';
                    } else {
                        g.setColor(darkColor);
                        nextColor = 'w';
                    }
                }
                g.fillRect(cellSize*col+settings.getOffsetX(), cellSize*row+settings.getOffsetY(), cellSize, cellSize);
            }
        }

        if (settings.getPossibleMoves() != null) {
            g.setColor(settings.getPossibleMoveColor());
            for (int[] move : settings.getPossibleMoves()) {
                if (settings.getFigurePositions().values().stream().anyMatch(a -> Arrays.equals(a, move))) {
                    g.fillRect(cellSize*move[1]+settings.getOffsetX(), cellSize*move[0]+settings.getOffsetY(), cellSize, cellSize);
                }
                g.fillOval(cellSize*move[1]+settings.getOffsetX() + (cellSize / 10), cellSize*move[0]+settings.getOffsetY() + (cellSize / 10), cellSize - (cellSize / 5), cellSize - (cellSize / 5));
            }
        }


        figurePositions = settings.getFigurePositions();
        for (String key : figures.getFigures().keySet()) {
            if (getCurrFigure() != null) {
                if (key.equals(getCurrFigure())) {
                    continue;
                }
            }
            g.drawImage(figures.getFigures().get(key), figurePositions.get(key)[1]*cellSize+settings.getOffsetX() + (settings.getCellSize() / 10), figurePositions.get(key)[0]*cellSize+settings.getOffsetY() + (settings.getCellSize() / 10), settings.getCellSize() - (settings.getCellSize() / 5), settings.getCellSize() - (settings.getCellSize() / 5), null);
        }
        if (getCurrFigure() != null) {
            g.drawImage(figures.getFigures().get(getCurrFigure()), figurePositions.get(getCurrFigure())[1]*cellSize+settings.getOffsetX() + (settings.getCellSize() / 10), figurePositions.get(getCurrFigure())[0]*cellSize+settings.getOffsetY() + (settings.getCellSize() / 10), settings.getCellSize() - (settings.getCellSize() / 5), settings.getCellSize() - (settings.getCellSize() / 5), null);
        }
        revalidate();
        repaint();

    }

    public String getCurrFigure() {
        return currFigure;
    }

    public void setCurrFigure(String currFigure) {
        this.currFigure = currFigure;
    }
}
