package com.example.chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Figures extends JPanel {
    private final File[] listOfFiles;
    private HashMap<String, BufferedImage> figures;
    private final List<String> doubles;
    String strF;
    String[] path;
    Figures() throws IOException {
        this.doubles = Arrays.asList("wbi", "bbi", "wkn", "bkn", "wro", "bro");
        File folder = new File("./src/main/resources/com/example/chess/pictures");
        this.listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            throw new Error("No pictures found");
        }
        initFigures();

    }
    public HashMap<String ,BufferedImage> loadFigures() throws IOException {
        HashMap<String ,BufferedImage> figuresLocal = new HashMap<>(listOfFiles.length);
        for (File f : listOfFiles) {
            strF = f.toString().replace("\\", "\\\\");
            path = strF.split("\\\\");
            strF = path[path.length - 1].substring(0, 3);
            figuresLocal.put(strF, ImageIO.read(f));
            if (strF.equals("wpa") || strF.equals("bpa")) {
                for (int i = 1; i < 8; i++) {
                    figuresLocal.put(strF + "%s".formatted(i), ImageIO.read(f));
                }
            } else if (doubles.contains(strF)) {
                figuresLocal.put(strF + "1", ImageIO.read(f));
            }

        }
        return figuresLocal;
    }

    public HashMap<String, BufferedImage> getFigures() {
        return figures;
    }

    public void initFigures() throws IOException {
        this.figures = loadFigures();
    }

    public void addFigure(String figure, BufferedImage img) {
        getFigures().put(figure, img);
    }

    public boolean removeFigure(String figure) {
        return getFigures().remove(figure) != null;
    }

    public BufferedImage createFigure(String figure) {
        // create a figure that was requested retrospectively
        // get the name of figure
        char color = figure.charAt(0);
        String figureFileName = switch (figure.substring(1, 3)) {
            case "ki" -> color + "king.png";
            case "qu" -> color + "queen.png";
            case "bi" -> color + "bishop.png";
            case "kn" -> color + "knight.png";
            case "ro" -> color + "rook.png";
            case "pa" -> color + "pawn.png";
            default -> throw new StringIndexOutOfBoundsException();
        };
        BufferedImage file = null;
        try {
            for (File f : listOfFiles) {
                strF = f.toString().replace("\\", "\\\\");
                path = strF.split("\\\\");
                strF = path[path.length - 1];
                if (strF.equals(figureFileName)) {
                    file = ImageIO.read(f);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
