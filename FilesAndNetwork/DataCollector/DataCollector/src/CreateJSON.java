import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.List;


public class CreateJSON {

    public static void main(String[] args) {
        List<Station> stations = ParseHTML.parseMetroStationsDoc();
        List<Line> lines = ParseHTML.parseMetroLinesDoc();
        System.out.println(lines);
        List<StationDate> dates = ParseCsv.getCsvDoc();
        List<StationDepth> depths = ParseJSON.parseStations();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (Station station : stations) {
            StationForJSON stationForJSON = new StationForJSON();
            stationForJSON.setName(station.getName());
            if(station.getHasConnection().contains("переход")){
                stationForJSON.setHasConnection(true);
            }else {
                stationForJSON.setHasConnection(false);
            }
            for (Line line : lines) {
                String lineNumber = line.getNumber();
                if(station.getLineNumber().equals(lineNumber)){
                    stationForJSON.setLine(line.getName());
                }
            }

            for (StationDate date : dates) {
                String name = date.getName();
                if (station.getName().equals(name)){
                    stationForJSON.setDate(date.getDate());
                }
            }

            for (StationDepth depth : depths) {
                String name = depth.getStationName();
                if (station.getName().equals(name)){
                    stationForJSON.setDepth(depth.getDepth());
                }
            }
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                String json1 = ow.writeValueAsString(stationForJSON);
                Gson gson = new Gson();
                JsonElement jelem = gson.fromJson(json1, JsonElement.class);
                JsonObject item = jelem.getAsJsonObject();
                array.add(item);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        json.put("stations", array);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json.toString());
        String prettyJsonString = gson.toJson(je);

        try {
            FileWriter file = new FileWriter("A:\\IDEA_projects\\java_basics\\FilesAndNetwork\\DataCollector\\DataCollector\\data\\stations.json");
            file.write(prettyJsonString);
            file.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        JSONObject jsonForStationsAndLines = new JSONObject();
        JSONObject jsonForStations = new JSONObject();
        JSONArray arrayForLines = new JSONArray();
        for (Line line : lines) {
            JSONArray arrayForStations = new JSONArray();
            JSONObject jsonForLines = new JSONObject();
            for (Station station : stations) {
                if (line.getNumber().equals(station.getLineNumber())){
                    arrayForStations.add(station.getName());
                }
            }
            jsonForLines.put("number", line.getNumber());
            jsonForLines.put("name", line.getName());
            System.out.println(jsonForLines);
            jsonForStations.put(line.getNumber(), arrayForStations);
            arrayForLines.add(jsonForLines);
        }
        jsonForStationsAndLines.put("stations", jsonForStations);
        jsonForStationsAndLines.put("lines", arrayForLines);

        JsonElement je1 = JsonParser.parseString(jsonForStationsAndLines.toString());
        String prettyJsonString1 = gson.toJson(je1);
        try {
            FileWriter file = new FileWriter("A:\\IDEA_projects\\java_basics\\FilesAndNetwork\\DataCollector\\DataCollector\\data\\lines.json");
            file.write(prettyJsonString1);
            file.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


