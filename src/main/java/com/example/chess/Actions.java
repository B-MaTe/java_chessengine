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
    private int row, col, oldRow, oldCol;
    String currPiece;
    boolean onBoard;
    boolean leftClickReleased = true;
    Actions(Board board, Figures figures, Settings settings, GameLogic gameLogic) {
        super();
        this.board = board;
        this.figures = figures;
        this.settings = settings;
        this.gameLogic = gameLogic;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isLeftClickReleased()) {
            return;
        }
        int unmask = InputEvent.BUTTON1_DOWN_MASK;
        if ((e.getModifiersEx() & unmask) == unmask) {
            setLeftClickReleased(false);
            setNewPos(e);
            if (getRow() < 0 || getRow() > 7 || getCol() < 0 || getCol() > 7) {
                setOnBoard(false);
                return;
            }
            setOnBoard(true);
            // set the mouse pos to (x, y) coordinates
            for (Map.Entry<String, int[]> entry : settings.getFigurePositions().entrySet()) {
                // check if the mouse coordinates (x, y) match any figure on the board
                if (Arrays.equals(entry.getValue(), new int[]{getCol(), getRow()})) {
                    if (entry.getKey().charAt(0) == settings.getTurn()) {
                        updateOldPosToNew();
                        setCurrPiece(entry.getKey());
                        try {
                            // set the possible moves for highlighting, and validation
                            settings.setPossibleMoves(gameLogic.getCheckedPossibleMoves(new int[]{getCol(), getRow()}, entry.getKey()));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            if (getCurrPiece() == null) {
                setOnBoard(false);
                setValuesToNull();
            }
        } else {
            setOnBoard(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            setLeftClickReleased(true);
        }
        if (isLeftClickReleased()) {
            if (isOnBoard()) {
                try {
                    // check if user made a legal move
                    if (getOldRow() != getRow() || getOldCol() != getCol() && getCurrPiece() != null) {
                        // check if move is valid
                        if (gameLogic.checkMove(getCurrPiece(), new int[]{getCol(), getRow()}, new int[]{getOldCol(), getOldRow()})) {
                            // moves++
                            settings.setMove(settings.getMove() + 1);
                            // figure moved
                            settings.setFigureMoved(getCurrPiece());
                            // check if move caused checkmate
                            settings.setCheckmate(gameLogic.checkIfCheckmate(getCurrPiece().charAt(0), settings.getFigurePositions()));
                            // swap turn
                            if (settings.getTurn() == 'w') {
                                settings.setTurn('b');
                            } else {
                                settings.setTurn('w');
                            }
                        // take the move back
                        } else {
                            takeBackMove();
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            // reassign the values to null
            setValuesToNull();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isOnBoard()) {
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
    }

    private void setValuesToNull() {
        setCurrPiece(null);
        board.setCurrFigure(null);
        settings.setPossibleMoves(null);
    }

    private void takeBackMove() {
        setOldPos();
        settings.moveFigure(getCurrPiece(), new int[]{getCol(), getRow()});
        setOnBoard(false);
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
        setRow((e.getX() - settings.getOffsetX()) / settings.getCellSize());
        setCol((e.getY() - settings.getOffsetY()) / settings.getCellSize());
    }

    private void moveFigure(String figure) {
        board.setCurrFigure(getCurrPiece());
        settings.moveFigure(figure, new int[]{getCol(), getRow()});
    }


    public boolean isLeftClickReleased() {
        return leftClickReleased;
    }

    public void setLeftClickReleased(boolean leftClickReleased) {
        this.leftClickReleased = leftClickReleased;
    }

    public int getOldRow() {
        return oldRow;
    }

    public void setOldRow(int oldRow) {
        this.oldRow = oldRow;
    }

    public int getOldCol() {
        return oldCol;
    }

    public void setOldCol(int oldCol) {
        this.oldCol = oldCol;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getCurrPiece() {
        return currPiece;
    }

    public void setCurrPiece(String currPiece) {
        this.currPiece = currPiece;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }
}
