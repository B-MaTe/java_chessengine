package com.example.chess;

import javax.swing.*;
import java.awt.*;

public class Board  extends JPanel {

    Settings settings;
    Board() {
        this.settings = new Settings();
    }

    public void paint(Graphics g) {
        boolean isWhiteRowStart = true;
        char nextColor = 'w';
        int cellSize = settings.getCellSize();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (col == 0) {
                    if (isWhiteRowStart) {
                        g.setColor(Color.WHITE);
                        nextColor = 'b';
                        isWhiteRowStart = false;
                    } else {
                        g.setColor(Color.BLACK);
                        nextColor = 'w';
                        isWhiteRowStart = true;
                    }
                } else {
                    if (nextColor == 'w') {
                        g.setColor(Color.WHITE);
                        nextColor = 'b';
                    } else {
                        g.setColor(Color.BLACK);
                        nextColor = 'w';
                    }
                }
                g.fillRect(cellSize*col+settings.getOffsetX(), cellSize*row+settings.getOffsetY(), cellSize, cellSize);
            }
        }

    }
}
