import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class ParseJSON{
    public static List<StationDepth> parseStations() {
        List<StationDepth> stationDepths = new ArrayList<>();
        FileSearch fileSearch = new FileSearch();
        fileSearch.processFilesFromFolder("data");
        List<String> paths = fileSearch.getJson();
        for (String path : paths) {
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonData = (JSONArray) parser.parse(ParseFile.parseFile(path));
                jsonData.forEach(stationDepth -> {
                    JSONObject lineJsonObject = (JSONObject) stationDepth;
                    StationDepth stationDepth1 = new StationDepth(
                            (String) lineJsonObject.get("station_name"), String.valueOf(lineJsonObject.get("depth"))
                    );
                    stationDepths.add(stationDepth1);
                });
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return stationDepths;
    }
}
