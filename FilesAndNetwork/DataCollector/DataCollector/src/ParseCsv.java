import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {
    public static List<StationDate> getCsvDoc(){
        List<StationDate> stationDates = new ArrayList<>();
        FileSearch fileSearch = new FileSearch();
        fileSearch.processFilesFromFolder("data");
        List<String> paths = fileSearch.getCsv();
        for (String path : paths) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(path));
                for (String line : lines) {
                    String[] fragments = line.split(",");
                    stationDates.add(new StationDate(fragments[0], fragments[1]));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return stationDates;
    }
}
