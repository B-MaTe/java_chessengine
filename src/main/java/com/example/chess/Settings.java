package com.example.chess;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    private int WIDTH = 1980;
    private int HEIGHT = 1080;
    private boolean visible = true;
    private boolean resizable = false;
    private String title = "Chess";
    private int offsetX = 150; // px
    private int offsetY = 55; // px
    private int cellSize = 100; // px
    private Color darkColor = Color.gray;
    private Color lightColor = Color.WHITE;
    private Color possibleMoveColor = Color.cyan;
    private HashMap<String, int[]> figurePositions;
    private int move = 0;
    private final HashMap<String, Boolean> figureMoved;
    private List<int[]> possibleMoves = null;
    private char turn = 'w';
    // 1 -> black is top, -1 -> white is top
    private char topColorChar = 'b';
    private final char computerColor = 'b';
    private final HashMap<Character, Integer> topColor;
    private boolean checkmate = false;
    private final HashMap<Character, String> checkmateMessage;
    private final HashMap<String, int[]> kingStartingPos;
    private String lastMovedFigure = null;
    private int[] lastMovedFigurePos = null;
    private int[] lastMovedFigurePrevPos = null;
    private boolean pawnPromoted = false;
    private String promotedPawn = null;
    JButton queen, knight, bishop, rook, restart;
    private boolean justPromoted = false;
    private final HashMap<String, int[]> startingPos;
    private String temporaryPromotedFigure = null;
    private double points = 0;
    private int positionsChecked = 0;
    Figures figures;
    Settings(Figures figures) {
        this.figures = figures;
        this.kingStartingPos = new HashMap<>();
        this.topColor = new HashMap<>();
        topColor.put('w', -1);
        topColor.put('b', 1);
        this.figureMoved = new HashMap<>();
        this.figurePositions = new HashMap<>();
        figurePositions.put("bro", new int[]{0,0});
        figurePositions.put("bkn", new int[]{0,1});
        figurePositions.put("bbi", new int[]{0,2});
        figurePositions.put("bqu", new int[]{0,3});
        figurePositions.put("bki", new int[]{0,4});
        figurePositions.put("bbi1", new int[]{0,5});
        figurePositions.put("bkn1", new int[]{0,6});
        figurePositions.put("bro1", new int[]{0,7});
        figurePositions.put("bpa", new int[]{1,0});
        figurePositions.put("bpa1", new int[]{1,1});
        figurePositions.put("bpa2", new int[]{1,2});
        figurePositions.put("bpa3", new int[]{1,3});
        figurePositions.put("bpa4", new int[]{1,4});
        figurePositions.put("bpa5", new int[]{1,5});
        figurePositions.put("bpa6", new int[]{1,6});
        figurePositions.put("bpa7", new int[]{1,7});
        figurePositions.put("wro", new int[]{7,0});
        figurePositions.put("wkn", new int[]{7,1});
        figurePositions.put("wbi", new int[]{7,2});
        figurePositions.put("wqu", new int[]{7,3});
        figurePositions.put("wki", new int[]{7,4});
        figurePositions.put("wbi1", new int[]{7,5});
        figurePositions.put("wkn1", new int[]{7,6});
        figurePositions.put("wro1", new int[]{7,7});
        figurePositions.put("wpa", new int[]{6,0});
        figurePositions.put("wpa1", new int[]{6,1});
        figurePositions.put("wpa2", new int[]{6,2});
        figurePositions.put("wpa3", new int[]{6,3});
        figurePositions.put("wpa4", new int[]{6,4});
        figurePositions.put("wpa5", new int[]{6,5});
        figurePositions.put("wpa6", new int[]{6,6});
        figurePositions.put("wpa7", new int[]{6,7});
        for (String key : figurePositions.keySet()) {
            figureMoved.put(key, false);
        }
        startingPos = new HashMap<>(figurePositions);
        this.checkmateMessage = new HashMap<>(2);
        checkmateMessage.put('w', "Checkmate, Black won the game!");
        checkmateMessage.put('b', "Checkmate, White won the game!");
        kingStartingPos.put("wki", figurePositions.get("wki"));
        kingStartingPos.put("bki", figurePositions.get("bki"));

        int fontSize = getCellSize() / 8;
        queen = new JButton("Queen");
        queen.setBounds(getOffsetX() + getCellSize() * 9, fontSize, fontSize * 3, fontSize);
        queen.addActionListener(e -> setPromotedPawn(turnSwapper(getTurn()) + "qu"));
        queen.setVisible(false);

        knight = new JButton("Knight");
        knight.setBounds(getOffsetX() + getCellSize() * 9, fontSize * 2, fontSize * 3, fontSize);
        knight.addActionListener(e -> setPromotedPawn(turnSwapper(getTurn()) + "kn"));
        knight.setVisible(false);

        bishop = new JButton("Bishop");
        bishop.setBounds(getOffsetX() + getCellSize() * 9, fontSize * 3, fontSize * 3, fontSize);
        bishop.addActionListener(e -> setPromotedPawn(turnSwapper(getTurn()) + "bi"));
        bishop.setVisible(false);

        rook = new JButton("Rook");
        rook.setBounds(getOffsetX() + getCellSize() * 9, fontSize * 4, fontSize * 3, fontSize);
        rook.addActionListener(e -> setPromotedPawn(turnSwapper(getTurn()) + "ro"));
        rook.setVisible(false);


        // Restart button
        restart = new JButton("Restart Game");
        restart.setBounds(getOffsetX() + getCellSize() * 9, fontSize, fontSize * 5, fontSize);
        restart.addActionListener(e -> {
            try {
                restartGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        restart.setVisible(false);

    }

    public int getPositionsChecked() {
        return positionsChecked;
    }

    public void increasePositionsChecked() {
        positionsChecked += 1;
    }

    public void restartPositionsChecked() {
        positionsChecked = 0;
    }

    public char getComputerColor() {
        return computerColor;
    }

    public char getTopColorChar() {
        return topColorChar;
    }

    public void setTopColorChar(char topColorChar) {
        this.topColorChar = topColorChar;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getTemporaryPromotedFigure() {
        return temporaryPromotedFigure;
    }

    public void setTemporaryPromotedFigure(String temporaryPromotedFigure) {
        this.temporaryPromotedFigure = temporaryPromotedFigure;
    }

    public boolean isJustPromoted() {
        return justPromoted;
    }

    public void setJustPromoted(boolean justPromoted) {
        this.justPromoted = justPromoted;
    }

    public char turnSwapper(char color) {
        if (color == 'w') {
            return 'b';
        }
        return 'w';
    }

    public String getPromotedPawn() {
        return promotedPawn;
    }

    public void setPromotedPawn(String promotedPawn) {
        this.promotedPawn = promotedPawn;
    }

    public JButton getRestart() {
        return restart;
    }

    public JButton getQueen() {
        return queen;
    }

    public JButton getKnight() {
        return knight;
    }

    public JButton getBishop() {
        return bishop;
    }

    public JButton getRook() {
        return rook;
    }

    public boolean isPawnPromoted() {
        return pawnPromoted;
    }

    public void setPawnPromoted(boolean pawnPromoted) {
        this.pawnPromoted = pawnPromoted;
    }

    public int[] getLastMovedFigurePrevPos() {
        return lastMovedFigurePrevPos;
    }

    public void setLastMovedFigurePrevPos(int[] lastMovedFigurePrevPos) {
        this.lastMovedFigurePrevPos = lastMovedFigurePrevPos;
    }

    public int[] getLastMovedFigurePos() {
        return lastMovedFigurePos;
    }

    public void setLastMovedFigurePos(int[] lastMovedFigurePos) {
        this.lastMovedFigurePos = lastMovedFigurePos;
    }

    public String getLastMovedFigure() {
        return lastMovedFigure;
    }

    public void setLastMovedFigure(String lastMovedFigure) {
        this.lastMovedFigure = lastMovedFigure;
    }

    public HashMap<Character, String> getCheckmateMessage() {
        return checkmateMessage;
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public void setCheckmate(boolean checkmate) {
        this.checkmate = checkmate;
    }

    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    public int getTopColor(char color) {
        return topColor.get(color);
    }

    public HashMap<String, Boolean> getFigureMoved() {
        return figureMoved;
    }

    public void setFigureMoved(String key) {
        getFigureMoved().put(key, true);
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Color getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(Color darkColor) {
        this.darkColor = darkColor;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public Color getPossibleMoveColor() {
        return possibleMoveColor;
    }

    public void setPossibleMoveColor(Color possibleMoveColor) {
        this.possibleMoveColor = possibleMoveColor;
    }

    public void setFigurePositions(HashMap<String, int[]> figurePositions) {
        this.figurePositions = figurePositions;
    }

    public HashMap<String, int[]> getFigurePositions() {
        return figurePositions;
    }

    public boolean removeFigure(@NotNull String figure) {
        return getFigurePositions().remove(figure) != null;
    }

    public void moveFigure(@NotNull String key, int @NotNull [] value) {
        getFigurePositions().put(key, value);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public boolean getVisible() {
        return visible;
    }

    public boolean getResizable() {
        return resizable;
    }

    public String getTitle() {
        return title;
    }

    public List<int[]> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<int[]> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public int[] kingStartingPos(String king) {
        return kingStartingPos.get(king);
    }

    private void setStartingPos() {
        setFigurePositions(new HashMap<>(getStartingPos()));
    }

    private void setStartingFigures() throws IOException {
        figures.initFigures();
    }

    public HashMap<String, int[]> getStartingPos() {
        return startingPos;
    }


    public void restartGame() throws IOException {
        // set every figure to not moved
        for (String val : getFigureMoved().keySet()) {
            getFigureMoved().put(val, false);
        }
        // set starting position
        setStartingPos();
        // set starting figures
        setStartingFigures();
        setTurn('w');
        setCheckmate(false);
        setMove(1);
        getRestart().setVisible(false);
        setPoints(0.0d);
        setPossibleMoves(null);
    }

}
