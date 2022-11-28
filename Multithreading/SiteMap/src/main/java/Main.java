import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class Main {
    private static String url = "https://skillbox.ru";//Адрес сайта
    private static String fileName = "skillbox_ru.txt";//Имя файла
    private static final String DST_FOLDER = "src/data/";//место хранения файлов записи
    private static final int numberOfCores = Runtime.getRuntime().availableProcessors();//кол-во ядер процессора

    public static void main(String[] args) throws IOException {

        SiteMapBuilder linkExecutor = new SiteMapBuilder(url);
            String siteMap = new ForkJoinPool(numberOfCores).invoke(linkExecutor);
            writeToFile(siteMap);
        }
        protected static void writeToFile(String string)  {
            if (!Files.exists(Paths.get(DST_FOLDER))){
                new File(DST_FOLDER).mkdir();
            }
            String filePath = DST_FOLDER.concat(fileName);
            File file = new File(filePath);
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.write(string);
            writer.flush();
        }
    }
