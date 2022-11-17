import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParseFile {
    public static String parseFile(String path){
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> sb.append(line + "\n"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
