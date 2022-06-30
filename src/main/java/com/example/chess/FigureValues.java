package com.example.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FigureValues {
    private final HashMap<String, Double> figureValues = new HashMap<>(6);
    private final HashMap<String, List<double[]>> positionalValues = new HashMap<>(6);

    FigureValues() {
        // figure values
        figureValues.put("pa", 10d);
        figureValues.put("kn", 30.5d);
        figureValues.put("bi", 33.3d);
        figureValues.put("ro", 56.3d);
        figureValues.put("qu", 95d);
        figureValues.put("ki", 9999d);

        // positional values
        // king
        positionalValues.put("ki",
                new ArrayList<>(8)
                );
        positionalValues.get("ki").add(new double[]{-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0});
        positionalValues.get("ki").add(new double[]{-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0});
        positionalValues.get("ki").add(new double[]{-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0});
        positionalValues.get("ki").add(new double[]{-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0});
        positionalValues.get("ki").add(new double[]{-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0});
        positionalValues.get("ki").add(new double[]{-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0});
        positionalValues.get("ki").add(new double[]{2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0});
        positionalValues.get("ki").add(new double[]{2.0, 3.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0});
        // queen
        positionalValues.put("qu",
                new ArrayList<>(8)
        );
        positionalValues.get("qu").add(new double[]{-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0});
        positionalValues.get("qu").add(new double[]{-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0});
        positionalValues.get("qu").add(new double[]{-1.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0});
        positionalValues.get("qu").add(new double[]{-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("qu").add(new double[]{0.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("qu").add(new double[]{-1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0});
        positionalValues.get("qu").add(new double[]{-1.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, -1.0});
        positionalValues.get("qu").add(new double[]{-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0});
        // rook
        positionalValues.put("ro",
                new ArrayList<>(8)
        );
        positionalValues.get("ro").add(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        positionalValues.get("ro").add(new double[]{0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5});
        positionalValues.get("ro").add(new double[]{-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("ro").add(new double[]{-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("ro").add(new double[]{-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("ro").add(new double[]{-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("ro").add(new double[]{-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5});
        positionalValues.get("ro").add(new double[]{0.0, 0.0, 0.0, 0.5, 0.5, 0.0, 0.0, 0.0});
        // bishop
        positionalValues.put("bi",
                new ArrayList<>(8)
        );
        positionalValues.get("bi").add(new double[]{-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0});
        positionalValues.get("bi").add(new double[]{-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0});
        positionalValues.get("bi").add(new double[]{-1.0, 0.0, 0.5, 1.0, 1.0, 0.5, 0.0, -1.0});
        positionalValues.get("bi").add(new double[]{-1.0, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, -1.0});
        positionalValues.get("bi").add(new double[]{-1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, -1.0});
        positionalValues.get("bi").add(new double[]{-1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, -1.0});
        positionalValues.get("bi").add(new double[]{-1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0});
        positionalValues.get("bi").add(new double[]{-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0});
        // knight
        positionalValues.put("kn",
                new ArrayList<>(8)
        );
        positionalValues.get("kn").add(new double[]{-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0});
        positionalValues.get("kn").add(new double[]{-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0});
        positionalValues.get("kn").add(new double[]{-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0});
        positionalValues.get("kn").add(new double[]{-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 1.0, -3.0});
        positionalValues.get("kn").add(new double[]{-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0});
        positionalValues.get("kn").add(new double[]{-3.0, 0.5, 1.0, 1.5, 1.5, 1.0, 0.5, -3.0});
        positionalValues.get("kn").add(new double[]{-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0});
        positionalValues.get("kn").add(new double[]{-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0});
        // pawn
        positionalValues.put("pa",
                new ArrayList<>(8)
        );
        positionalValues.get("pa").add(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        positionalValues.get("pa").add(new double[]{5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0});
        positionalValues.get("pa").add(new double[]{1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 1.0});
        positionalValues.get("pa").add(new double[]{0.5, 0.5, 1.0, 2.5, 2.5, 1.0, 0.5, 0.5});
        positionalValues.get("pa").add(new double[]{0.0, 0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 0.0});
        positionalValues.get("pa").add(new double[]{0.5, -0.5, -1.0, 0.0, 0.0, -1.0, -0.5, 0.5});
        positionalValues.get("pa").add(new double[]{0.5, 1.0, 1.5, -2.0, -2.0, 1.0, 1.0, 0.5});
        positionalValues.get("pa").add(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});


    }

    public HashMap<String, List<double[]>> getPositionalValues() {
        return positionalValues;
    }

    public HashMap<String, Double> getFigureValues() {
        return figureValues;
    }

    public float calculateFigureValues(HashMap<String, int[]> table, char upperSide) {
        float val = 0;
        double positionalVal;
        for (Map.Entry<String, int[]> entry : table.entrySet()) {
            if (entry.getKey().charAt(0) == 'w') {
                if (upperSide == 'w') {
                    positionalVal = positionalValues.get(entry.getKey().substring(1, 3)).get(7 - entry.getValue()[0])[7 - entry.getValue()[1]];
                } else {
                    positionalVal = positionalValues.get(entry.getKey().substring(1, 3)).get(entry.getValue()[0])[entry.getValue()[1]];
                }
                val += getFigureValues().get(entry.getKey().substring(1, 3)) + positionalVal;
            } else {
                if (upperSide == 'w') {
                    positionalVal = positionalValues.get(entry.getKey().substring(1, 3)).get(entry.getValue()[0])[entry.getValue()[1]];
                } else {
                    positionalVal = positionalValues.get(entry.getKey().substring(1, 3)).get(7 - entry.getValue()[0])[7 - entry.getValue()[1]];
                }
                val -= getFigureValues().get(entry.getKey().substring(1, 3)) + positionalVal;
            }

        }
        return val;
    }
}
