import core.Line;
import core.Station;
import junit.framework.TestCase;


import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List<Station> route;

    List<Station> routeOnTheLine;
    List<Station> routeWithOneConnections;
    List<Station> routeWithTwoConnections;

    List<Station> connections1;
    List<Station> connections2;
    List<Station> connections3;
    Station fromOnTheLine;
    Station toOnTheLine;

    Station fromWithOneConnections;
    Station toWithOneConnections;

    Station fromWithTwoConnections;
    Station toWithTwoConnections;
    StationIndex stationIndex;

    @Override
    protected void setUp() {
        route = new ArrayList<>();

        routeOnTheLine = new ArrayList<>();
        routeWithOneConnections = new ArrayList<>();
        routeWithTwoConnections = new ArrayList<>();

        connections1 = new ArrayList<>();
        connections2 = new ArrayList<>();
        connections3 = new ArrayList<>();

        Line line2 = new Line(2, "Московско-Петроградская");
        Line line3 = new Line(3, "Невско-Василеостровская");
        Line line4 = new Line(4, "Правобережная");

        line2.addStation(new Station("Невский проспект", line2));
        line2.addStation(new Station("Сенная площадь", line2));

        line3.addStation(new Station("Василеостровская", line3));
        line3.addStation(new Station("Гостиный двор", line3));
        line3.addStation(new Station("Маяковская", line3));
        line3.addStation(new Station("Площадь Александра Невского", line3));

        line4.addStation(new Station("Площадь Александра Невского", line4));
        line4.addStation(new Station("Лиговский проспект", line4));
        line4.addStation(new Station("Достоевская", line4));
        line4.addStation(new Station("Спасская", line4));

        stationIndex = new StationIndex();

        connections1.add(new Station("Гостиный двор", line3));
        connections1.add(new Station("Невский проспект", line2));
        connections2.add(new Station("Площадь Александра Невского", line3));
        connections2.add(new Station("Площадь Александра Невского", line4));
        connections3.add(new Station("Сенная площадь", line2));
        connections3.add(new Station("Спасская", line4));

        route.add(new Station("Василеостровская", line3));
        route.add(new Station("Гостиный двор", line3));
        route.add(new Station("Маяковская", line3));
        route.add(new Station("Площадь Александра Невского", line3));
        route.add(new Station("Площадь Александра Невского", line4));
        route.add(new Station("Лиговский проспект", line4));
        route.add(new Station("Достоевская", line4));
        route.add(new Station("Спасская", line4));

        routeOnTheLine.add(new Station("Василеостровская", line3));
        routeOnTheLine.add(new Station("Гостиный двор", line3));
        routeOnTheLine.add(new Station("Маяковская", line3));

        routeWithOneConnections.add(new Station("Василеостровская", line3));
        routeWithOneConnections.add(new Station("Гостиный двор", line3));
        routeWithOneConnections.add(new Station("Невский проспект", line2));

        routeWithTwoConnections.add(new Station("Василеостровская", line3));
        routeWithTwoConnections.add(new Station("Гостиный двор", line3));
        routeWithTwoConnections.add(new Station("Невский проспект", line2));
        routeWithTwoConnections.add(new Station("Сенная площадь", line2));
        routeWithTwoConnections.add(new Station("Спасская", line4));

        stationIndex.addStation(new Station("Невский проспект", line2));
        stationIndex.addStation(new Station("Василеостровская", line3));
        stationIndex.addStation(new Station("Гостиный двор", line3));
        stationIndex.addStation(new Station("Маяковская", line3));
        stationIndex.addStation(new Station("Площадь Александра Невского", line3));
        stationIndex.addStation(new Station("Площадь Александра Невского", line4));
        stationIndex.addStation(new Station("Лиговский проспект", line4));
        stationIndex.addStation(new Station("Достоевская", line4));
        stationIndex.addStation(new Station("Сенная площадь", line2));
        stationIndex.addStation(new Station("Спасская", line4));

        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stationIndex.addLine(line4);

        stationIndex.addConnection(connections1);
        stationIndex.addConnection(connections2);
        stationIndex.addConnection(connections3);

        fromOnTheLine = stationIndex.getStation("Василеостровская");
        toOnTheLine = stationIndex.getStation("Маяковская");

        fromWithOneConnections = stationIndex.getStation("Василеостровская");
        toWithOneConnections = stationIndex.getStation("Невский проспект");

        fromWithTwoConnections = stationIndex.getStation("Василеостровская");
        toWithTwoConnections = stationIndex.getStation("Спасская");
    }

    public void testCalculateDuration(){
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 18.5;
        assertEquals(expected, actual);
    }

    public void testGetShortestRoute(){
        RouteCalculator calculator = new RouteCalculator(stationIndex);
        List<Station> actualOnTheLine = calculator.getShortestRoute(fromOnTheLine, toOnTheLine);
        List<Station> actualWithOneConnections = calculator.getShortestRoute(fromWithOneConnections, toWithOneConnections);
        List<Station> actualWithTwoConnections = calculator.getShortestRoute(fromWithTwoConnections, toWithTwoConnections);


        assertEquals(routeOnTheLine, actualOnTheLine);
        assertEquals(routeWithOneConnections, actualWithOneConnections);
        assertEquals(routeWithTwoConnections, actualWithTwoConnections);
    }
}
