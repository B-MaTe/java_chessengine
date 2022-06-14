package com.example.chess;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;

public class Settings {
    private int WIDTH = 1980;
    private int HEIGHT = 1080;
    private boolean visible = true;
    private boolean resizable = false;
    private String title = "Chess";
    private int offsetX = 150; // px
    private int offsetY = 75; // px
    private int cellSize = 85; // px
    private Color darkColor = Color.GRAY;
    private Color lightColor = Color.WHITE;
    private final HashMap<String, int[]> figurePositions;
    private int move = 0;
    private final HashMap<String, Boolean> figureMoved;
    // 1 -> black is top, -1 -> white is top
    private final HashMap<Character, Integer> topColor;
    Settings() {
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
}
