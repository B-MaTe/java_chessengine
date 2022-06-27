package com.example.chess;


import java.awt.image.BufferedImage;
import java.util.*;

public class GameLogic {
    Figures figureClass;
    Settings settings;
    GameLogic(Settings settings, Figures figureClass) {
        this.settings = settings;
        this.figureClass = figureClass;
    }
    public HashMap<String, int[]> getFigures() {
        return settings.getFigurePositions();
    }

    public boolean removeFigure(String figure) {
        // return true if successfully removed figure
        return settings.removeFigure(figure) && figureClass.removeFigure(figure);
    }
    // This method is called by Actions
    public boolean checkMove(String figure, int[] newPos, int[] oldPos) {
        if (figure != null && newPos != null && oldPos != null) {
            // getCheckedPossibleMoves(oldPos, figure)
            return settings.getPossibleMoves().stream().anyMatch(a -> Arrays.equals(a, newPos)) && handleCollision(checkCollision(figure, newPos), figure);
        }
        return false;
    }

    private List<int[]> getFigureMoves(String figure, int[] oldPos, HashMap<String, int[]> table) throws Exception {
        if (figure == null) {
            return null;
        }
        String typeOfFigure = figure.substring(1, 3);

        switch (typeOfFigure) {
            case "ki" -> {
                // king
                return getKingMoves(oldPos, figure, table);
            }
            case "qu" -> {
                // queen
                List<int[]> straightAndDiagonalMoves = getStraightMoves(oldPos, figure, table);
                straightAndDiagonalMoves.addAll(getDiagonalMoves(oldPos, figure, table));

                return straightAndDiagonalMoves;
            }
            case "bi" -> {
                // bishop
                return getDiagonalMoves(oldPos, figure, table);
            }
            case "kn" -> {
                // knight
                return getKnightMoves(oldPos, figure, table);
            }
            case "ro" -> {
                // rook
                return getStraightMoves(oldPos, figure, table);
            }
            case "pa" -> {
                // pawn
                return getPawnMoves(oldPos, figure, table);
            }
            default -> throw (new Exception("Piece not found"));
        }
    }

    private HashMap<String, int[]> getCopyOfFigures() {
        return new HashMap<>(getFigures());
    }

    private List<int[]> getDiagonalMoves(int[] oldPos, String figure, HashMap<String, int[]> table) {
        char color = figure.charAt(0);
        List<int[]> moves = new ArrayList<>();
        boolean foundPiece;


        // Left-Up
        foundPiece = false;
        if (oldPos[0] != 0 && oldPos[1] != 0) {
            int min = Math.min(oldPos[0], oldPos[1]);
            for (int i = 1; i <= min; i++) {
                int[] val = new int[]{oldPos[0]-i, oldPos[1]-i};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }

        // Right-Up
        foundPiece = false;
        if (oldPos[0] != 0 && oldPos[1] != 7) {
            int min = Math.min(oldPos[0], 7 - oldPos[1]);
            for (int i = 1; i <= min; i++) {
                int[] val = new int[]{oldPos[0]-i, oldPos[1]+i};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }

        // Left-Down
        foundPiece = false;
        if (oldPos[0] != 7 && oldPos[1] != 0) {
            int min = Math.min(7 - oldPos[0], oldPos[1]);
            for (int i = 1; i <= min; i++) {
                int[] val = new int[]{oldPos[0]+i, oldPos[1]-i};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }

        // Right-Down
        foundPiece = false;
        if (oldPos[0] != 7 && oldPos[1] != 7) {
            int min = Math.min(7 - oldPos[0], 7 - oldPos[1]);
            for (int i = 1; i <= min; i++) {
                int[] val = new int[]{oldPos[0]+i, oldPos[1]+i};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }
        return moves;
    }

    private List<int[]> getPawnMoves(int[] oldPos, String figure, HashMap<String, int[]> table) {
        List<int[]> moves = new ArrayList<>();
        int side = settings.getTopColor(figure.charAt(0));

        // collision left
        if (between(oldPos[1]-1,0, 7 )) {
            int[] valL = new int[]{side + oldPos[0], oldPos[1]-1};
            for (Map.Entry<String, int[]> entry : table.entrySet()) {
                if (Arrays.equals(entry.getValue(), valL) && !entry.getKey().equals(figure)) {
                    if (entry.getKey().charAt(0) != figure.charAt(0)) {
                        moves.add(valL);
                    }
                }
            }
        }

        // collision right
        if (between(oldPos[1]+1,0, 7 )) {
            int[] valR = new int[]{side + oldPos[0], oldPos[1]+1};
            for (Map.Entry<String, int[]> entry : table.entrySet()) {
                if (Arrays.equals(entry.getValue(), valR) && !entry.getKey().equals(figure)) {
                    if (entry.getKey().charAt(0) != figure.charAt(0)) {
                        moves.add(valR);
                    }
                }
            }
        }

        // move 1
        boolean foundFigure = false;
        int[] val = new int[]{side + oldPos[0], oldPos[1]};
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                foundFigure = true;
                break;
            }
        }
        if (!foundFigure) {
            moves.add(val);
        }


        // move 2
        if (!settings.getFigureMoved().get(figure)) {
            int[] doubleVal = new int[]{2*side + oldPos[0], oldPos[1]};
            for (Map.Entry<String, int[]> entry : table.entrySet()) {
                if (Arrays.equals(entry.getValue(), doubleVal) && !entry.getKey().equals(figure)) {
                    return moves;
                }
            }
            moves.add(doubleVal);
        } else {
            if (settings.getLastMovedFigure() != null) {
                // check for En Passant
                if (oldPos[0] == settings.getLastMovedFigurePos()[0] && Math.abs(oldPos[1] - settings.getLastMovedFigurePos()[1]) == 1) {
                    if (settings.getLastMovedFigure().startsWith("pa", 1) && Math.abs(settings.getLastMovedFigurePos()[0] - settings.getLastMovedFigurePrevPos()[0]) > 1) {
                        moves.add(new int[]{settings.getLastMovedFigurePos()[0]+side, settings.getLastMovedFigurePos()[1]});
                    }
                }
            }

        }
        return moves;
    }

    private List<int[]> getStraightMoves(int[] oldPos, String figure, HashMap<String, int[]> table) {
        char color = figure.charAt(0);
        List<int[]> moves = new ArrayList<>();
        boolean foundPiece;
        // Iterate through the 4 directions and add them to the HashMap

        // Up
        foundPiece = false;
        if (oldPos[0] != 7) {
            for (int i = oldPos[0]+1; i < 8; i++) {
                int[] val = new int[]{i ,oldPos[1]};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }


        // Down
        foundPiece = false;
        if (oldPos[0] != 0) {
            for (int i = oldPos[0]-1; i > -1; i--) {
                int[] val = new int[]{i ,oldPos[1]};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }


        // Left
        foundPiece = false;
        if (oldPos[1] != 0) {
            for (int i = oldPos[1]-1; i > -1; i--) {
                int[] val = new int[]{oldPos[0] , i};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }
        }


        // Up
        foundPiece = false;
        if (oldPos[1] != 7) {
            for (int i = oldPos[1]+1; i < 8; i++) {
                int[] val = new int[]{oldPos[0] ,i};
                for (Map.Entry<String, int[]> entry : table.entrySet()) {
                    if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                        foundPiece = true;
                        if (entry.getKey().charAt(0) != color) {
                            moves.add(val);
                        }
                        break;
                    }
                }
                if (foundPiece) {
                    break;
                }
                moves.add(val);
            }

        }
        return moves;
    }

    private List<int[]> getKnightMoves(int[] oldPos, String figure, HashMap<String, int[]> table) {
        char color = figure.charAt(0);
        List<int[]> moves = new ArrayList<>();
        if (between(oldPos[0]+1, 0, 7) && between(oldPos[1]+2, 0, 7)) {
            moves.add(new int[]{oldPos[0]+1, oldPos[1]+2});
        }
        if (between(oldPos[0]-1, 0, 7) && between(oldPos[1]-2, 0, 7)) {
            moves.add(new int[]{oldPos[0]-1, oldPos[1]-2});
        }
        if (between(oldPos[0]-1, 0, 7) && between(oldPos[1]+2, 0, 7)) {
            moves.add(new int[]{oldPos[0]-1, oldPos[1]+2});
        }
        if (between(oldPos[0]+1, 0, 7) && between(oldPos[1]-2, 0, 7)) {
            moves.add(new int[]{oldPos[0]+1, oldPos[1]-2});
        }
        if (between(oldPos[0]+2, 0, 7) && between(oldPos[1]+1, 0, 7)) {
            moves.add(new int[]{oldPos[0]+2, oldPos[1]+1});
        }
        if (between(oldPos[0]-2, 0, 7) && between(oldPos[1]+1, 0, 7)) {
            moves.add(new int[]{oldPos[0]-2, oldPos[1]+1});
        }
        if (between(oldPos[0]+2, 0, 7) && between(oldPos[1]-1, 0, 7)) {
            moves.add(new int[]{oldPos[0]+2, oldPos[1]-1});
        }
        if (between(oldPos[0]-2, 0, 7) && between(oldPos[1]-1, 0, 7)) {
            moves.add(new int[]{oldPos[0]-2, oldPos[1]-1});
        }

        List<int[]> validMoves = new ArrayList<>();
        boolean sameColor;
        for (int[] move : moves) {
            sameColor = false;
            for (Map.Entry<String, int[]> entry : table.entrySet()) {
                if (Arrays.equals(entry.getValue(), move)) {
                    if (entry.getKey().charAt(0) == color && !entry.getKey().equals(figure)) {
                        sameColor = true;
                        break;
                    }
                }
            }
            if (!sameColor) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    private List<int[]> getKingMoves(int[] oldPos, String figure, HashMap<String, int[]> table) {
        char color = figure.charAt(0);
        List<int[]> moves = new ArrayList<>();
        boolean foundPiece;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                foundPiece = false;
                if (i != 0 || j != 0) {
                    if (between(oldPos[0]+i, 0, 7) && between(oldPos[1]+j,0,7 )) {
                        int[] val = new int[]{oldPos[0]+i ,oldPos[1]+j};
                        for (Map.Entry<String, int[]> entry : table.entrySet()) {
                            if (Arrays.equals(entry.getValue(), val) && !entry.getKey().equals(figure)) {
                                foundPiece = true;
                                if (entry.getKey().charAt(0) != color) {
                                    moves.add(val);
                                }
                                break;
                            }
                        }
                        if (!foundPiece) {
                            moves.add(val);
                        }

                    }
                }
            }
        }
        // check if king can castle
        String king = color + "ki";
        if (!settings.getFigureMoved().get(king) && Arrays.equals(table.get(king), settings.kingStartingPos(king))) {
            if (!settings.getFigureMoved().get(color + "ro1")) {
                int[] castleR = castleRight(king, table);
                if (castleR != null) {
                    moves.add(castleR);
                }
            }
            if (!settings.getFigureMoved().get(color + "ro")) {
                int[] castleL = castleLeft(king, table);
                if (castleL != null) {
                    moves.add(castleL);
                }
            }


        }

        return moves;
    }

    private int[] castleLeft(String king, HashMap<String, int[]> table) {
        List<Integer> cols = new ArrayList<>();
        cols.add(1);
        cols.add(2);
        cols.add(3);
        int row = settings.kingStartingPos(king)[0];
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (entry.getValue()[0] == row) {
                if (!entry.getKey().equals(king)) {
                    if (cols.contains(entry.getValue()[1])) {
                        return null;
                    }
                }
            }
        }
        return new int[]{row, 2};
    }

    private int[] castleRight(String king, HashMap<String, int[]> table) {
        List<Integer> cols = new ArrayList<>();
        cols.add(5);
        cols.add(6);
        int row = settings.kingStartingPos(king)[0];
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (entry.getValue()[0] == row) {
                if (!entry.getKey().equals(king)) {
                    if (cols.contains(entry.getValue()[1])) {
                        return null;
                    }
                }
            }
        }

        return new int[]{row, 6};
    }

    private String checkCollision(String figure, int[] pos) {
        if (figure != null && pos != null) {
            for (Map.Entry<String, int[]> entry : getFigures().entrySet()) {
                if (entry.getKey().equals(figure)) {
                    continue;
                }
                if (Arrays.equals(entry.getValue(), pos)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public void handleCastle(char color, char direction, int row, HashMap<String, int[]> table) {
        // right side castle
        if (direction == 'R') {
            table.put(color + "ro1", new int[]{row, 5});
        } else {
            // left side castle
            table.put(color + "ro", new int[]{row, 3});
        }

    }

    public void handleEnPassant(String collidedFigure, HashMap<String, int[]> table) {
        table.remove(collidedFigure);
    }

    public void handlePawnPromotion(String figure, String pawn, int[] pos) {
        addNewFigureToTable(figure, pos);
        removeFigure(pawn);
        settings.setPawnPromoted(false);
        settings.setPromotedPawn(null);
    }

    private boolean handleCollision(String collidedPiece, String figure) {
        if (collidedPiece == null || figure == null) {
            // no collision -> no checking
            return true;
        }
        if (collidedPiece.substring(0, 1).equals(figure.substring(0,1))) {
            // same color
            return false;
        }

        // remove the collidedPiece
        return removeFigure(collidedPiece);
    }

    private boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
        return variable >= minValueInclusive && variable <= maxValueInclusive;
    }

    public List<int[]> getCheckedPossibleMoves(int[] oldPos, String figure) throws Exception {
        HashMap<String, int[]> tableCopy = getCopyOfFigures();
        List<int[]> moves = getFigureMoves(figure, oldPos, tableCopy);
        List<int[]> checkedMoves = new ArrayList<>();
        for (int[] move : moves) {
            moveTemporary(figure, move, tableCopy);
            if (!isChess(figure.charAt(0), tableCopy)) {
                checkedMoves.add(move);
            }
            tableCopy = getCopyOfFigures();
        }
        return checkedMoves;
    }

    private void moveTemporary(String figure, int[] move, HashMap<String, int[]> table) {
        // check castle
        if (figure.substring(1, 3).equals("ki")) {
            if (Math.abs(move[1] - table.get(figure)[1]) > 1) {
                // handle castling
                int row = move[0];
                char direction;
                if (move[1] > table.get(figure)[1]) {
                    direction = 'R';
                } else {
                    direction = 'L';
                }
                handleCastle(figure.charAt(0), direction, row, table);
            }
        }

        // make move
        table.put(figure, move);
        // check collision
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (Arrays.equals(entry.getValue(), move) && !entry.getKey().equals(figure)) {
                table.remove(entry.getKey());
                return;
            }
        }

        // check if pawn promotion


    }

    private boolean isChess(char color, HashMap<String, int[]> table) throws Exception {
        // get the position of the king
        // figure.charAt(0) -> color + "ki" -> wki/bki
        int[] kingPos = table.get(color + "ki");
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            // different color
            if (entry.getKey().charAt(0) != color) {
                if (getFigureMoves(entry.getKey(), entry.getValue(), table).stream().anyMatch(a -> Arrays.equals(a, kingPos))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfCheckmate(char color, HashMap<String, int[]> table) throws Exception {
        // get a color that moved the last move.
        // check the other sides moves
        // if the other side has any moves with any figure -> return false
        // if not return and the loop is done -> return true
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (entry.getKey().charAt(0) != color) {
                if (getCheckedPossibleMoves(entry.getValue(), entry.getKey()).size() > 0) {
                    return false;
                }

            }
        }
        return true;
    }

    public HashMap<String, List<int[]>> getAllMovesForOneColor(char color, HashMap<String, int[]> table) throws Exception {
        long start = System.nanoTime();

        HashMap<String, List<int[]>> moves = new HashMap<>();
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (entry.getKey().charAt(0) == color) {
                moves.put(entry.getKey(), getCheckedPossibleMoves(entry.getValue(), entry.getKey()));
            }
        }
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        return moves;
    }

    public void addNewFigureToTable(String figure, int[] pos) {
        // r -> retrospectively created figure
        String identifier = "r";
        int num = 0;
        for (String key : settings.getFigurePositions().keySet()) {
            if (key.startsWith(figure.substring(1, 3), 1)) {
                num++;
            }
        }
        if (num == 0) {
            num = Integer.parseInt("");
        }
        settings.getFigurePositions().put(figure.substring(0, 3) + identifier + num, pos);
        figureClass.addFigure(figure.substring(0, 3) + identifier + num, figureClass.createFigure(figure));
    }

}


