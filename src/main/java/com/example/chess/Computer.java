package com.example.chess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Computer {
    Settings settings;
    GameLogic logic;
    FigureValues figureValues;
    Computer(Settings settings, GameLogic logic, FigureValues figureValues) {
        this.settings = settings;
        this.logic = logic;
        this.figureValues = figureValues;
    }

    public HashMap<String, List<int[]>> getAllMovesForOneColor(char color, HashMap<String, int[]> table) throws Exception {
        HashMap<String, List<int[]>> moves = new HashMap<>();
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (entry.getKey().charAt(0) == color) {
                moves.put(entry.getKey(), logic.getCheckedPossibleMoves(entry.getValue(), entry.getKey()));
            }
        }
        return moves;
    }


    public HashMap<String, int[]> minimaxRoot(int depth, HashMap<String, int[]> table, boolean isMaximisingPlayer) throws Exception {
        settings.restartPositionsChecked();
        HashMap<String, int[]>  bestMoveFound = new HashMap<>();
        int bestMove = Integer.MIN_VALUE;
        HashMap<String, int[]> originalTable = new HashMap<>(table);
        for (Map.Entry<String, List<int[]>> entry : getAllMovesForOneColor(settings.getComputerColor(), table).entrySet()) {
            for (int[] move : entry.getValue()) {
                logic.moveTemporary(entry.getKey(), table.get(entry.getKey()), move ,table);
                int value = minimax(depth - 1, table, Integer.MIN_VALUE, Integer.MAX_VALUE, !isMaximisingPlayer);
                table = new HashMap<>(originalTable);
                if (value >= bestMove) {
                    bestMove = value;
                    bestMoveFound.put(entry.getKey(), move);
                }
            }
        }
        return bestMoveFound;
    }

    private int minimax(int depth, HashMap<String,int[]> table, int alpha, int beta, boolean isMaximisingPlayer) throws Exception {
        settings.increasePositionsChecked();
        if (depth == 0) {
            return (int) -figureValues.calculateFigureValues(table, settings.getTopColorChar());
        }
        HashMap<String, int[]> originalTable = new HashMap<>(table);
        int bestMove;
        if (isMaximisingPlayer) {
            bestMove = Integer.MIN_VALUE;
            for (Map.Entry<String, List<int[]>> entry : getAllMovesForOneColor(settings.getComputerColor(), table).entrySet()) {
                for (int[] move : entry.getValue()) {
                    logic.moveTemporary(entry.getKey(), table.get(entry.getKey()), move, table);
                    bestMove = Math.max(bestMove, minimax(depth - 1, table, alpha, beta, !isMaximisingPlayer));
                    table = new HashMap<>(originalTable);
                    alpha = Math.max(alpha, bestMove);
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
        } else {
            bestMove = Integer.MAX_VALUE;
            for (Map.Entry<String, List<int[]>> entry : getAllMovesForOneColor(settings.turnSwapper(settings.getComputerColor()), table).entrySet()) {
                for (int[] move : entry.getValue()) {
                    logic.moveTemporary(entry.getKey(), table.get(entry.getKey()), move, table);
                    bestMove = minimax(depth -1, table, alpha, beta, !isMaximisingPlayer);
                    table = new HashMap<>(originalTable);
                    beta = Math.min(beta, bestMove);
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
        }
        return bestMove;
    }

    public HashMap<String, int[]> getComputerMove(int depth, HashMap<String, int[]> table) throws Exception {
        return minimaxRoot(depth, table, true);
    }

}
