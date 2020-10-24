package ar.com.ada.api.mutant.utils;

import ar.com.ada.api.mutant.entities.DNASample;

public class MatrixDNAIterator {

    public static boolean debugMode = false;

    public int matchesByRows(DNASample sample) {
        char[][] m = sample.toMatrix();
        int matches = 0;

        //iteramos por fila
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (j + 4 >= m.length) {
                    break;
                }
                // SI A = B, y B = C, C = D, A = D?
                // A = B, A = C, A = D y B = D?
                if (m[i][j] == m[i][j + 1] && m[i][j] == m[i][j + 2] && m[i][j] == m[i][j + 3]) {
                    if (debugMode)
                        System.out.println("Secuencia Match Row: " + m[i][j] + m[i][j + 1] + m[i][j + 2] + m[i][j + 3]);
                    matches++;
                }
            }
        }

        return matches;

    }

    public int matchesByColumns(DNASample sample) {
        char[][] m = sample.toMatrix();
        int matches = 0;

        //iteramos por columna
        for (int j = 0; j < m.length; j++) {
            for (int i = 0; i < m.length; i++) {

                if (i + 4 >= m.length) {
                    break;
                }
                // SI A = B, y B = C, C = D, A = D?
                // A = B, A = C, A = D y B = D?
                if (m[i][j] == m[i + 1][j] && m[i][j] == m[i + 2][j] && m[i][j] == m[i + 3][j]) {
                    if (debugMode)
                        System.out.println(
                                "Secuencia Match Column: " + m[i][j] + m[i + 1][j] + m[i + 2][j] + m[i + 3][j]);

                    matches++;
                }
            }
        }

        return matches;

    }

    public int matchesByDiagonals(DNASample sample) {

        int matches = 0;
        if (debugMode)
            System.out.println("Secuencia Diagonal Izq a Derecha");

        matches = matchesByDiagonals(sample.toMatrix(), false);

        if (debugMode)
            System.out.println("Secuencia Diagonal Derecha a izq");

        /*if (matches > 1) //Descomentar para salir de una si no importa la cantidad de matchs
            return matches;*/

        matches += matchesByDiagonals(sample.toMatrixInverted(), true);

        return matches;

    }

    private int matchesByDiagonals(char[][] m, boolean inverted) {

        int matches = 0;
        int matrixSize = m.length;

        if (debugMode) {
            System.out.println("Inverted Matrix: " + inverted);
            System.out.println(matrixToString(m));
        }

        //diagonales de izq a derecha
        for (int k = 0; k < m.length * 2; k++) {
            for (int j = 0; j <= k; j++) {
                int i = k - j;
                if (i < matrixSize && j < matrixSize) {
                    //Aca estoy en diagonal de izq a derecha.
                    if (debugMode)
                        System.out.println("i : " + i + " , j: " + j);
                    if (i + 1 - 4 < 0 || j + 4 - 1 >= matrixSize) {

                        if (debugMode) {
                            System.out.println("Sale");
                            System.out.println("i + 1 - 4 = " + (i + 1 - 4));
                            System.out.println("j + 4 - 1 = " + (j + 4));
                        }
                        continue;
                    }
                    // SI A = B, y B = C, C = D, A = D?
                    // A = B, A = C, A = D y B = D?
                    if (debugMode)
                        System.out.println(
                                "Secuencia Test: " + m[i][j] + m[i - 1][j + 1] + m[i - 2][j + 2] + m[i - 3][j + 3]);

                    if (m[i][j] == m[i - 1][j + 1] && m[i][j] == m[i - 2][j + 2] && m[i][j] == m[i - 3][j + 3]) {
                        if (debugMode)
                            System.out.println("Secuencia Match: " + m[i][j] + m[i - 1][j + 1] + m[i - 2][j + 2]
                                    + m[i - 3][j + 3]);

                        matches++;
                    }
                }
            }

        }

        return matches;
    }

    public static String matrixToString(char[][] matrix) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            sb.append("| ");
            for (int j = 0; j < matrix.length; j++) {
                sb.append(matrix[i][j]);
            }
            sb.append(" |\n");
        }

        return sb.toString();

    }

}
