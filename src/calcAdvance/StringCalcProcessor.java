package calcAdvance;

/**
 * Created by gedeagas on 6/9/17.
 */


public class StringCalcProcessor {
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            boolean kunyah(int charTunggalUntukDiKunyah) {
                while (ch == ' ') nextChar();
                if (ch == charTunggalUntukDiKunyah) {
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse() {
                nextChar();
                double x = fungsiParseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }



            double fungsiParseExpression() {
                double x = fungsiParseTerm();
                for (;;) {
                    if      (kunyah('+')) x += fungsiParseTerm(); // tambah
                    else if (kunyah('-')) x -= fungsiParseTerm(); // kurang
                    else return x;
                }
            }

            double fungsiParseTerm() {
                double x = fungsiParseFaktor();
                for (;;) {
                    if      (kunyah('*')) x *= fungsiParseFaktor(); // perkalian
                    else if (kunyah('/')) x /= fungsiParseFaktor(); // pembagian
                    else return x;
                }
            }

            double fungsiParseFaktor() {
                if (kunyah('+')) return fungsiParseFaktor(); // tanda tambah
                if (kunyah('-')) return -fungsiParseFaktor(); // tanda kurang

                double x;
                int startPos = this.pos;
                if (kunyah('(')) {
                    x = fungsiParseExpression();
                    kunyah(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = fungsiParseFaktor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Error: " + (char)ch);
                }

                if (kunyah('^')) x = Math.pow(x, fungsiParseFaktor()); // Exponen

                return x;
            }
        }.parse();
    }

}
