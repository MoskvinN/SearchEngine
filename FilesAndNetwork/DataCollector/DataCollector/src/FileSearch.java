
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSearch {
    private List<Path> paths;
    private List<String> csv = new ArrayList<>();
    private List<String> json = new ArrayList<>();

    public List<String> getCsv() {
        return csv;
    }

    public List<String> getJson() {
        return json;
    }

    public void processFilesFromFolder(String path) {
        try {
           paths = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Path path1 : paths) {
            if(path1.toString().contains("csv")){
                csv.add(path1.toString());
            } else if (path1.toString().contains("json")) {
                json.add(path1.toString());
            }
        }
    }
}
