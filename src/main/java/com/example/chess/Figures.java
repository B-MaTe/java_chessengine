package com.example.chess;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    Figures() throws IOException {
        this.doubles = Arrays.asList("wbi", "bbi", "wkn", "bkn", "wro", "bro");
        File folder = new File("./src/main/resources/com/example/chess/pictures");
        this.listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            throw new Error("No pictures found");
        }
        initFigures();

    }
    HashMap<String ,BufferedImage> loadFigures() throws IOException {
        HashMap<String ,BufferedImage> figuresLocal = new HashMap<>(listOfFiles.length);
        String strF;
        String[] path;
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



}
