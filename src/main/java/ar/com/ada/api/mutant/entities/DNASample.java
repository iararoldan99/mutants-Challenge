package ar.com.ada.api.mutant.entities;

import ar.com.ada.api.mutant.security.Crypto;

public class DNASample {

    private final static int MIN_SECUENCE = 4;
    private String[] dna;

    public DNASample(String[] dna) {
        this.dna = dna;
    }

    public boolean isValid() {
        //dimensions
        //&&
        //only letter A, T, C, G

        return this.dimensionIsOk() && this.lettersOk();
    }

    public char[][] toMatrix() {

        char[][] matrix = new char[this.dna.length][this.dna.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = this.dna[i].charAt(j);
            }
        }

        return matrix;
    }

    public char[][] toMatrixInverted() {

        char[][] matrix = new char[this.dna.length][this.dna.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][matrix.length - j - 1] = this.dna[i].charAt(j);
            }
        }

        return matrix;
    }

    private boolean dimensionIsOk() {
        //Tiene qeu ser NxN 
        //String[] dnaMutant = {"ATGCGAA","CAGTGCA","TTATGTA","AGAAGGA","CCCCTAA","TCACTGA","TCGAACT"};		
        //String[] dnaMutant = {}					
        int arrayLenghtSecuenceWord = this.dna.length;

        if (arrayLenghtSecuenceWord < MIN_SECUENCE)
            return false;

        for (String secuence : dna) {
            if (secuence == null || secuence.length() != arrayLenghtSecuenceWord)
                return false;
        }

        return true;
    }

    private boolean lettersOk() {

        for (String secuence : dna) {

            for (char letter : secuence.toCharArray()) {
                if (letter != 'A' && letter != 'T' && letter != 'C' && letter != 'G')
                    return false;
            }
        }

        return true;
    }

    public String uniqueHash() {
        StringBuilder sb = new StringBuilder();
        for (String secuence : dna) {

            sb.append(secuence + "|");

        }
        String dnaToHash = sb.toString();

        String dnaHashed = Crypto.hash(dnaToHash, "Magneto Rulz");

        return dnaHashed;
    }

    public String[] encrypt() {

        String[] dnaEncrypted = new String[dna.length];
        for (int i = 0; i < dnaEncrypted.length; i++) {
            dnaEncrypted[i] = Crypto.encrypt(dna[i], "*");
        }
        return dnaEncrypted;
    }

    public String[] decrypt() {

        String[] dnaClear = new String[dna.length];
        for (int i = 0; i < dnaClear.length; i++) {
            dnaClear[i] = Crypto.decrypt(dna[i], "*");
        }
        return dnaClear;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String secuence : dna) {
            sb.append("| ");
            sb.append(secuence);
            sb.append(" |\n");
        }
        return sb.toString();
    }
}
