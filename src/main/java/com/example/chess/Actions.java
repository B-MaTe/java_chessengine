package com.example.chess;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Actions extends MouseAdapter {
    Figures figures;
    Board board;
    Settings settings;
    GameLogic gameLogic;
    Computer computer;
    private int row, col, oldRow, oldCol;
    String currPiece;
    boolean onBoard;
    boolean leftClickReleased = true;
    Actions(Board board, Figures figures, Settings settings, GameLogic gameLogic, Computer computer) {
        super();
        this.computer = computer;
        this.board = board;
        this.figures = figures;
        this.settings = settings;
        this.gameLogic = gameLogic;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (settings.isJustPromoted()) {
            try {
                checkCheckmate(settings.getFigurePositions(), settings.turnSwapper(settings.getTurn()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            settings.setJustPromoted(false);
        }
        if (!settings.isPawnPromoted()) {
            if (!isLeftClickReleased()) {
                return;
            }
            int unmask = InputEvent.BUTTON1_DOWN_MASK;
            if ((e.getModifiersEx() & unmask) == unmask) {
                setLeftClickReleased(false);
                setNewPos(e);
                if (isMouseOnBoard()) {
                    setOnBoard(false);
                    return;
                }
                setOnBoard(true);
                setPossibleMovesAndHighlights();
                // set the mouse pos to (x, y) coordinates
                if (getCurrPiece() == null) {
                    setOnBoard(false);
                    setValuesToNull();
                }
            } else {
                setOnBoard(false);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!settings.isPawnPromoted()) {
            if (isOnBoard()) {
                if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
                    handleMouseDragging(e);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!settings.isPawnPromoted()) {
            if (e.getButton() == 1) {
                setLeftClickReleased(true);
            }
            if (isLeftClickReleased()) {
                if (isOnBoard() && settings.getTurn() != settings.getComputerColor()) {
                    try {
                        // check if user made a legal move
                        if (checkIfLegalMove()) {
                            // check if move is valid
                            if (checkIfValidMove()) {
                                handleValidMove();
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
    }

    private boolean isMouseOnBoard() {
        return getRow() < 0 || getRow() > 7 || getCol() < 0 || getCol() > 7;
    }


    private boolean checkIfLegalMove() {
        return getOldRow() != getRow() || getOldCol() != getCol() && getCurrPiece() != null;
    }

    private boolean checkIfValidMove() {
        return gameLogic.checkMove(getCurrPiece(), new int[]{getRow(), getCol()}, new int[]{getOldRow(), getOldCol()});
    }

    private void setPossibleMovesAndHighlights() {
        for (Map.Entry<String, int[]> entry : settings.getFigurePositions().entrySet()) {
            // check if the mouse coordinates (x, y) match any figure on the board
            if (Arrays.equals(entry.getValue(), new int[]{getRow(), getCol()})) {
                if (entry.getKey().charAt(0) == settings.getTurn()) {
                    updateOldPosToNew();
                    setCurrPiece(entry.getKey());
                    try {
                        // set the possible moves for highlighting, and validation
                        settings.setPossibleMoves(gameLogic.getCheckedPossibleMoves(new int[]{getRow(), getCol()}, entry.getKey()));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    private void handleMouseDragging(MouseEvent e) {
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


    private void handleValidMove() throws Exception {
        // moves++
        settings.setMove(settings.getMove() + 1);
        // check if move was en passant
        checkEnPassant();
        // check if move was castle
        checkCastle();
        // set last moved figure
        settings.setLastMovedFigure(getCurrPiece());
        settings.setLastMovedFigurePos(new int[]{getRow(), getCol()});
        settings.setLastMovedFigurePrevPos(new int[]{getOldRow(), getOldCol()});
        // check if move was pawn promotion
        checkPawnPromotion();
        // figure moved
        settings.setFigureMoved(getCurrPiece());
        // check if move caused checkmate
        checkCheckmate(settings.getFigurePositions(), getCurrPiece().charAt(0));
        // swap turn
        settings.setTurn(settings.turnSwapper(settings.getTurn()));
        // update game points
        settings.setPoints(computer.figureValues.calculateFigureValues(settings.getFigurePositions(), settings.getTopColorChar()));
        // computer move
        if (settings.getTurn() == settings.getComputerColor() && !settings.isCheckmate()) {
            setValuesToNull();
            HashMap<String, int[]> moveMade = computer.getComputerMove(4, new HashMap<>(settings.getFigurePositions()));
            System.out.println(settings.getPositionsChecked());
            String figure = null;
            int[] move = new int[0];
            for (Map.Entry<String, int[]> entry : moveMade.entrySet()) {
                figure = entry.getKey();
                move = entry.getValue();
            }
            setCurrPiece(figure);
            setOldRow(settings.getFigurePositions().get(figure)[0]);
            setOldCol(settings.getFigurePositions().get(figure)[1]);
            setRow(move[0]);
            setCol(move[1]);
            gameLogic.moveTemporary(figure, settings.getFigurePositions().get(figure), move, settings.getFigurePositions());
            handleValidMove();
        }
    }

    private void checkCheckmate(HashMap<String, int[]> table, char turn) throws Exception {
        settings.setCheckmate(gameLogic.checkIfCheckmate(turn, table));
    }

    private void checkPawnPromotion() {
        // check for promotion
        if (getCurrPiece().startsWith("pa", 1) && (getRow() == 7 || getRow() == 0)) {
            settings.setPawnPromoted(true);
        }
    }

    private void checkEnPassant() {
        if (settings.getMove() > 1 && getCurrPiece().startsWith("pa", 1) && settings.getLastMovedFigure().startsWith("pa", 1)) {
            // check if move was capture
            if (settings.getLastMovedFigure() != null) {
                if (getOldCol() != getCol() && getCol() == settings.getLastMovedFigurePos()[1] && getRow()-settings.getTopColor(getCurrPiece().charAt(0)) == settings.getLastMovedFigurePos()[0]) {
                    // check if there is a figure at that position, if not, it was en passant move
                    int[] move = new int[]{getRow(), getCol()};
                    if (!settings.getFigurePositions().containsValue(move)) {
                        // remove figure
                        gameLogic.removeFigure(settings.getLastMovedFigure());
                    }
                }
            }
        }
    }

    private void checkCastle() {
        if (getCurrPiece().startsWith("ki", 1)) {
            // if abs(oldCol - newCol) > 1 -> castle
            if (Math.abs(getOldCol() - getCol()) > 1) {
                // left sided castle
                if (getOldCol() > getCol()) {
                    gameLogic.handleCastle(getCurrPiece().charAt(0), 'L', getRow() ,settings.getFigurePositions());
                } else {
                    // right sided castle
                    gameLogic.handleCastle(getCurrPiece().charAt(0), 'R', getRow() ,settings.getFigurePositions());
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
        settings.moveFigure(getCurrPiece(), new int[]{getRow(), getCol()});
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
        setCol((e.getX() - settings.getOffsetX()) / settings.getCellSize());
        setRow((e.getY() - settings.getOffsetY()) / settings.getCellSize());
    }

    private void moveFigure(String figure) {
        board.setCurrFigure(getCurrPiece());
        settings.moveFigure(figure, new int[]{getRow(), getCol()});
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
