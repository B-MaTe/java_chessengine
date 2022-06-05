package com.example.chess;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pictures extends JFrame {


    File[] listOfFiles;
    File folder;
    Pictures() {
        this.folder = new File("./src/main/resources/com/example/chess/pictures");
        this.listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            throw new Error("No pictures found");
        }
    }

    HashMap<String ,Icon> getIcons() {
        HashMap<String ,Icon> icons = new HashMap<>(listOfFiles.length);
        String strF;
        String[] path;
        for (File f : listOfFiles) {
            strF = f.toString();
            strF = strF.replace("\\", "\\\\");
            path = strF.split("\\\\");
            icons.put(path[path.length - 1].substring(0, 3), new ImageIcon(strF));
        }
        return icons;
    }

}
