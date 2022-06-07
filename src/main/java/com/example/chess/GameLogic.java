package com.example.chess;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameLogic {
    Figures figureClass;
    Settings settings;
    String typeOfFigure;
    String lastMovedFigure;
    boolean isValidMove;
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

    private String checkCollision(String figure, Byte[] pos) {
        if (figure != null && pos != null) {
            for (Map.Entry<String, Byte[]> entry : getFigures().entrySet()) {
                if (entry.getKey().equals(figure)) {
                    continue;
                }
                if (Arrays.equals(entry.getValue(), pos)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    // This method is called by Actions
    public boolean checkMove(String figure, Byte[] pos) throws Exception {
        if (figure != null && pos != null) {
            return validMove(figure) && handleCollision(checkCollision(figure, pos));
        }
        return false;
    }

    private boolean validMove(String figure) throws Exception {
        return checkFigureType(figure);
    }

    private boolean checkFigureType(String figure) throws Exception {
        if (figure == null) {
            return false;
        }
        typeOfFigure = figure.substring(1, 3);
        switch (typeOfFigure) {
            case "ki" ->
                // king
                    isValidMove = checkKingMove(figure);
            case "qu" ->
                // queen
                    isValidMove = checkQueenMove(figure);
            case "bi" ->
                // bishop
                    isValidMove = checkBishopMove(figure);
            case "kn" ->
                // knight
                    isValidMove = checkKnightMove(figure);
            case "ro" ->
                // rook
                    isValidMove = checkRookMove(figure);
            case "pa" ->
                // pawn
                    isValidMove = checkPawnMove(figure);
            default -> throw (new Exception("Piece not found"));
        }
        return isValidMove;
    }

    private boolean checkPawnMove(String figure) {
        return true;
    }

    private boolean checkRookMove(String figure) {
        return true;
    }

    private boolean checkKnightMove(String figure) {
        return true;
    }

    private boolean checkBishopMove(String figure) {
        return true;
    }

    private boolean checkQueenMove(String figure) {
        return true;
    }

    private boolean checkKingMove(String figure) {
        return true;
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
