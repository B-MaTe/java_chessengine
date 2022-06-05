package com.example.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Board  extends JPanel {

    Settings settings;
    Figures figures;
    HashMap<String,Byte[]> figurePositions;
    Color darkColor, lightColor;
    Board(Figures figures, Settings settings) {
        this.figurePositions = new HashMap<>();
        this.figures = figures;
        this.settings = settings;
        this.darkColor = settings.getDarkColor();
        this.lightColor = settings.getLightColor();
    }

    public void paint(Graphics g) {

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
        //System.out.println(figures.getFigures().keySet());
        figurePositions = settings.getFigurePositions();
        for (String key : figures.getFigures().keySet()) {
            g.drawImage(figures.getFigures().get(key), figurePositions.get(key)[1]*cellSize+settings.getOffsetX() + (settings.getCellSize() / 10), figurePositions.get(key)[0]*cellSize+settings.getOffsetY() + (settings.getCellSize() / 10), settings.getCellSize() - (settings.getCellSize() / 5), settings.getCellSize() - (settings.getCellSize() / 5), null);
        }


    }
}
