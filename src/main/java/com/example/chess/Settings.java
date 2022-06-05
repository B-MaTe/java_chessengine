package com.example.chess;

public class Settings {
    private int WIDTH = 1600;
    private int HEIGHT = 900;
    private boolean visible = true;
    private boolean resizable = false;
    private String title = "Chess";
    private int offsetX = 150; // px
    private int offsetY = 75; // px
    private int cellSize = 85; // px
    Settings() {
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
