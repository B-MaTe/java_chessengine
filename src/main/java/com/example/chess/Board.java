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
    GameLogic gameLogic;
    Color darkColor, lightColor, possibleMoveColor;
    Font turnFont, pointFont, checkmateFont;
    Board(Figures figures, Settings settings, GameLogic gameLogic) {
        this.figures = figures;
        this.settings = settings;
        this.darkColor = settings.getDarkColor();
        this.lightColor = settings.getLightColor();
        this.gameLogic = gameLogic;
        this.possibleMoveColor = settings.getPossibleMoveColor();
        turnFont = new Font("Serif", Font.BOLD, settings.getCellSize() / 4);
        pointFont = new Font("Serif", Font.BOLD, settings.getCellSize() / 4);
        checkmateFont = new Font("Serif", Font.BOLD, settings.getCellSize() / 4);
    }

    public void paint(Graphics g) {
        super.paint(g);

        //draw turn and points
        if (!settings.isCheckmate()) {
            g.setFont(getTurnFont());
            g.drawChars(new char[]{Character.toUpperCase(settings.getTurn())}, 0, 1, settings.getOffsetX() / 2, settings.getWIDTH() / 4);
            g.setFont(getPointFont());
            g.drawString("%.2f".formatted(settings.getPoints()), settings.getOffsetX() + (settings.getCellSize() * 11), settings.getWIDTH() / 4);
        }



        // draw if checkmate
        if (settings.isCheckmate()) {
            settings.getRestart().setVisible(true);
            g.setFont(getCheckmateFont());
            g.setColor(Color.red);
            g.drawString(settings.getCheckmateMessage().get(settings.getTurn()), settings.getOffsetX() + settings.getCellSize() * 9, settings.getWIDTH() / 4);
        }

        // draw board
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
            if (key != null && figurePositions.get(key) != null) {
                if (getCurrFigure() != null) {
                    if (key.equals(getCurrFigure())) {
                        continue;
                    }
                }
                g.drawImage(figures.getFigures().get(key), figurePositions.get(key)[1]*cellSize+settings.getOffsetX() + (settings.getCellSize() / 10), figurePositions.get(key)[0]*cellSize+settings.getOffsetY() + (settings.getCellSize() / 10), settings.getCellSize() - (settings.getCellSize() / 5), settings.getCellSize() - (settings.getCellSize() / 5), null);

            }
        }
        if (getCurrFigure() != null) {
            g.drawImage(figures.getFigures().get(getCurrFigure()), figurePositions.get(getCurrFigure())[1]*cellSize+settings.getOffsetX() + (settings.getCellSize() / 10), figurePositions.get(getCurrFigure())[0]*cellSize+settings.getOffsetY() + (settings.getCellSize() / 10), settings.getCellSize() - (settings.getCellSize() / 5), settings.getCellSize() - (settings.getCellSize() / 5), null);
        }
        if (settings.isPawnPromoted()) {
            if (!settings.getQueen().isVisible()) {
                settings.getQueen().setVisible(true);
                settings.getBishop().setVisible(true);
                settings.getKnight().setVisible(true);
                settings.getRook().setVisible(true);
            }

            if (settings.getPromotedPawn() != null) {
                try {
                    gameLogic.handlePawnPromotion(settings.getPromotedPawn(), settings.getLastMovedFigure(), settings.getLastMovedFigurePos());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            if (settings.getQueen().isVisible()) {
                settings.getQueen().setVisible(false);
                settings.getBishop().setVisible(false);
                settings.getKnight().setVisible(false);
                settings.getRook().setVisible(false);
            }
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

    public Font getTurnFont() {
        return turnFont;
    }

    public Font getPointFont() {
        return pointFont;
    }

    public Font getCheckmateFont() {
        return checkmateFont;
    }
}
