import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class NumberConstructor implements Runnable{
    public PrintWriter writer;
    public StringBuilder builder;
    public int regionCode;

    char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};


    public NumberConstructor(int regionCode) throws FileNotFoundException {
        writer = new PrintWriter("res/numbers" + regionCode + ".txt");
        builder = new StringBuilder();
        this.regionCode = regionCode;

    }

    @Override
    public void run() {
        for (int number = 1; number < 1000; number++) {
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        builder.append(firstLetter);
                        builder.append(padNumber(number, 3));
                        builder.append(secondLetter);
                        builder.append(thirdLetter);
                        builder.append(padNumber(regionCode, 2));
                        builder.append("\n");
                    }
                }
            }
        }
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }
    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        StringBuilder builder = new StringBuilder(numberStr);
        for (int i = 0; i < padSize; i++) {
            builder.append('0');
            builder.append(numberStr);
        }
        return builder.toString();
    }
}
