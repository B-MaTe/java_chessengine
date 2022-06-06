package com.example.chess;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.Map;


public class Actions extends MouseAdapter {
    Figures figures;
    Board board;
    Settings settings;
    GameLogic gameLogic;
    private byte row, col, oldRow, oldCol;
    String currPiece;
    Actions(Board board, Figures figures, Settings settings, GameLogic gameLogic) {
        super();
        this.board = board;
        this.figures = figures;
        this.settings = settings;
        this.gameLogic = gameLogic;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {

            setRow((byte) ((e.getX() - settings.getOffsetX()) / settings.getCellSize()));
            setCol((byte) ((e.getY() - settings.getOffsetY()) / settings.getCellSize()));
            for (Map.Entry<String, Byte[]> entry : settings.getFigurePositions().entrySet()) {
                if (Arrays.equals(entry.getValue(), new Byte[]{getCol(), getRow()})) {
                    setOldCol(getCol());
                    setOldRow(getRow());
                    currPiece = entry.getKey();
                }
            }
                } else if (e.getModifiersEx() == InputEvent.BUTTON2_DOWN_MASK) {
            //System.out.println("m");
        } else if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
            //System.out.println("r");
        }

    }
    public void mouseReleased(MouseEvent e) {
        gameLogic.setLastMovedFigure(currPiece);
        gameLogic.setLastMovedFigurePos(new Byte[]{getCol(), getRow()});
        if (gameLogic.checkMove()) {
            settings.setMove(settings.getMove() + 1);
        } else {
            takeBackMove();
        }
        currPiece = null;
        board.setCurrFigure(null);

    }

    private void takeBackMove() {
        settings.moveFigure(currPiece, new Byte[]{getOldCol(), getOldRow()});
    }

    public void mouseDragged(MouseEvent e) {
            if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
                if (currPiece != null) {
                    setRow((byte) ((e.getX() - settings.getOffsetX()) / settings.getCellSize()));
                    setCol((byte) ((e.getY() - settings.getOffsetY()) / settings.getCellSize()));
                    if (getRow() < 0 || getRow() > 7 || getCol() < 0 || getCol() > 7) {
                        setRow(getOldRow());
                        setCol(getOldCol());
                    } else {
                        moveFigure(currPiece);
                    }

                }

            } else if (e.getModifiersEx() == InputEvent.BUTTON2_DOWN_MASK) {

        } else if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {

        }
    }

    public void moveFigure(String figure) {
        board.setCurrFigure(currPiece);
        settings.moveFigure(figure, new Byte[]{getCol(), getRow()});
    }

    public byte getOldRow() {
        return oldRow;
    }

    public void setOldRow(byte oldRow) {
        this.oldRow = oldRow;
    }

    public byte getOldCol() {
        return oldCol;
    }

    public void setOldCol(byte oldCol) {
        this.oldCol = oldCol;
    }

    public Byte getRow() {
        return row;
    }

    public void setRow(byte row) {
        this.row = row;
    }

    public Byte getCol() {
        return col;
    }

    public void setCol(byte col) {
        this.col = col;
    }

    public Figures getFigures() {
        return figures;
    }

    public Board getBoard() {
        return board;
    }

    public Settings getSettings() {
        return settings;
    }
}
