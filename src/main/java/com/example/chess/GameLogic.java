package com.example.chess;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameLogic {
    Figures figureClass;
    Settings settings;
    String lastMovedFigure;
    Byte[] lastMovedFigurePos;
    HashMap<String, Byte[]> figures;
    GameLogic(Settings settings, Figures figureClass) {
        this.settings = settings;
        this.figureClass = figureClass;
    }


    public String getLastMovedFigure() {
        return lastMovedFigure;
    }

    public void setLastMovedFigure(String lastMovedFigure) {
        this.lastMovedFigure = lastMovedFigure;
    }

    public Byte[] getLastMovedFigurePos() {
        return lastMovedFigurePos;
    }

    public void setLastMovedFigurePos(Byte[] lastMovedFigurePos) {
        this.lastMovedFigurePos = lastMovedFigurePos;
    }

    public HashMap<String, Byte[]> getFigures() {
        return settings.getFigurePositions();
    }

    public boolean removeFigure(String figure) {
        return figureClass.removeFigure(figure) && settings.removeFigure(figure);
    }

    public String checkCollision(String lastMovedFigure, Byte[] lastMovedFigurePos) {
        for (Map.Entry<String, Byte[]> entry : getFigures().entrySet()) {
            if (entry.getKey().equals(lastMovedFigure)) {
                continue;
            }
            if (Arrays.equals(entry.getValue(), lastMovedFigurePos)) {
                return entry.getKey();
            }
        }
        return null;

    }

    public boolean checkMove() {
        return handleCollision(checkCollision(getLastMovedFigure(), getLastMovedFigurePos()));
    }

    private boolean handleCollision(String collidedPiece) {
        if (collidedPiece == null || getLastMovedFigure() == null) {
            // no collision -> no checking
            return true;
        }
        if (collidedPiece.substring(0, 1).equals(getLastMovedFigure().substring(0,1))) {
            // same color
            return false;
        }

        // remove the collidedPiece
        return removeFigure(collidedPiece);
    }
}
