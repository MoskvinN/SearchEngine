import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;
import java.util.*;

public class Main {
    public static void main(String[] args) {


    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        List<Flight> flights = new ArrayList<>();

        airport
                .getTerminals()
                .stream()
                .map(Terminal::getFlights)
                .flatMap(Collection::stream)
                .filter(flight -> flight.getDate().getHours() - new Date().getHours() <= 2)
                .filter(flight -> flight.getDate().getHours() - new Date().getHours() > 0)
                .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
                .forEach(flights::add);

        return flights;
    }

}