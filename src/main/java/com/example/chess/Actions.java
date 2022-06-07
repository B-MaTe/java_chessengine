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
            // set the mouse pos to (x, y) coordinates
            setNewPos(e);
            for (Map.Entry<String, Byte[]> entry : settings.getFigurePositions().entrySet()) {
                // check if the mouse coordinates (x, y) match any figure on the board
                if (Arrays.equals(entry.getValue(), new Byte[]{getCol(), getRow()})) {
                    updateOldPosToNew();
                    setCurrPiece(entry.getKey());
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        try {
            // check if user made a legal move
            if (getOldRow() != getRow() || getOldCol() != getCol() && getCurrPiece() != null) {
                handleGameLogicFigure();
                // check if move is valid
                if (gameLogic.checkMove(getCurrPiece(), new Byte[]{getCol(), getRow()})) {
                    // moves++
                    settings.setMove(settings.getMove() + 1);
                // take the move back
                } else {
                    takeBackMove();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // reassign the values to null
        setValuesToNull();
    }

    public void mouseDragged(MouseEvent e) {
        if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
            if (getCurrPiece() != null) {
                    setNewPos(e);
                // check if on the board
                if (getRow() < 0 || getRow() > 7 || getCol() < 0 || getCol() > 7) {
                    setOldPos();
                } else {
                    moveFigure(getCurrPiece());
                }
            }
        }
    }

    private void setValuesToNull() {
        setCurrPiece(null);
        board.setCurrFigure(null);
        gameLogic.setLastMovedFigurePos(null);
        gameLogic.setLastMovedFigure(null);
    }

    private void handleGameLogicFigure() {
        gameLogic.setLastMovedFigure(getCurrPiece());
        gameLogic.setLastMovedFigurePos(new Byte[]{getCol(), getRow()});
    }


    private void takeBackMove() {
        settings.moveFigure(getCurrPiece(), new Byte[]{getOldCol(), getOldRow()});
    }

    private void updateOldPosToNew() {
        setOldCol(getCol());
        setOldRow(getRow());
    }

    private void setOldPos() {
        setRow(getOldRow());
        setCol(getOldCol());
    }

    private void setNewPos(MouseEvent e) {
        setRow((byte) ((e.getX() - settings.getOffsetX()) / settings.getCellSize()));
        setCol((byte) ((e.getY() - settings.getOffsetY() - settings.getOffsetY() / 3) / settings.getCellSize()));
    }

    private void moveFigure(String figure) {
        board.setCurrFigure(getCurrPiece());
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

    public String getCurrPiece() {
        return currPiece;
    }

    public void setCurrPiece(String currPiece) {
        this.currPiece = currPiece;
    }
}
