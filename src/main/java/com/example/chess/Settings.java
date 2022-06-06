package com.example.chess;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Settings {
    private int WIDTH = 1600;
    private int HEIGHT = 900;
    private boolean visible = true;
    private boolean resizable = false;
    private String title = "Chess";
    private int offsetX = 150; // px
    private int offsetY = 75; // px
    private int cellSize = 85; // px
    private Color darkColor = Color.GRAY;
    private Color lightColor = Color.WHITE;
    private final HashMap<String, Byte[]> figurePositions;
    Settings(@NotNull Figures figures) {
        HashMap<String, BufferedImage> figureMap = figures.getFigures();
        this.figurePositions = new HashMap<>();
        figurePositions.put("bro", new Byte[]{0,0});
        figurePositions.put("bkn", new Byte[]{0,1});
        figurePositions.put("bbi", new Byte[]{0,2});
        figurePositions.put("bqu", new Byte[]{0,3});
        figurePositions.put("bki", new Byte[]{0,4});
        figurePositions.put("bbi1", new Byte[]{0,5});
        figurePositions.put("bkn1", new Byte[]{0,6});
        figurePositions.put("bro1", new Byte[]{0,7});
        figurePositions.put("bpa", new Byte[]{1,0});
        figurePositions.put("bpa1", new Byte[]{1,1});
        figurePositions.put("bpa2", new Byte[]{1,2});
        figurePositions.put("bpa3", new Byte[]{1,3});
        figurePositions.put("bpa4", new Byte[]{1,4});
        figurePositions.put("bpa5", new Byte[]{1,5});
        figurePositions.put("bpa6", new Byte[]{1,6});
        figurePositions.put("bpa7", new Byte[]{1,7});
        figurePositions.put("wro", new Byte[]{7,0});
        figurePositions.put("wkn", new Byte[]{7,1});
        figurePositions.put("wbi", new Byte[]{7,2});
        figurePositions.put("wqu", new Byte[]{7,3});
        figurePositions.put("wki", new Byte[]{7,4});
        figurePositions.put("wbi1", new Byte[]{7,5});
        figurePositions.put("wkn1", new Byte[]{7,6});
        figurePositions.put("wro1", new Byte[]{7,7});
        figurePositions.put("wpa", new Byte[]{6,0});
        figurePositions.put("wpa1", new Byte[]{6,1});
        figurePositions.put("wpa2", new Byte[]{6,2});
        figurePositions.put("wpa3", new Byte[]{6,3});
        figurePositions.put("wpa4", new Byte[]{6,4});
        figurePositions.put("wpa5", new Byte[]{6,5});
        figurePositions.put("wpa6", new Byte[]{6,6});
        figurePositions.put("wpa7", new Byte[]{6,7});
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

    public HashMap<String, Byte[]> getFigurePositions() {
        return figurePositions;
    }

    public void moveFigure(String key, Byte[] value) {
        figurePositions.put(key, value);
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
