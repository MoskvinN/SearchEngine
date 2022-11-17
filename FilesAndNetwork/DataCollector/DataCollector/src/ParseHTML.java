import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParseHTML{

    private static String htmlFile = ParseFile.parseFile("data/metro.html");
    private static Document metroDoc = Jsoup.parse(htmlFile);

    public static List<Line> parseMetroLinesDoc(){
        List<Line> lines = new ArrayList<>();
        Elements elements = metroDoc.select("span.js-metro-line");
        elements.forEach(element -> lines.add(new Line(element.text(), element.attr("data-line"))));
        return lines;
    }

    public static List<Station> parseMetroStationsDoc(){
        List<Station> stations = new ArrayList<>();
        Elements elements = metroDoc.select("p.single-station");
        elements.forEach(element -> stations.add(new Station(element.children().get(1).text(), element.parents().attr("data-line"), element.children().attr("title"))));
        return stations;
    }
}
