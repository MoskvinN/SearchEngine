import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {

    public static PrintWriter writer;
    public static void main(String[] args) throws Exception
    {
        ExecutorService service = Executors.newFixedThreadPool(4);

        long start = System.currentTimeMillis();

        writer = new PrintWriter("res/numbers.txt");
        char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        for (int regionCode = 1; regionCode < 100; regionCode++) {
            service.submit(new NumberConstructor(regionCode));
        }
        service.shutdown();

        System.out.println("Время выполнения несколькими потоками - " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();

        for (int regionCode = 1; regionCode < 100; regionCode++) {
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
                            writer.write(builder.toString());
                        }
                    }
                }
            }
        }

        writer.flush();
        writer.close();

        System.out.println("Время выполнения одним потоком - " + (System.currentTimeMillis() - start) + " ms");
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
