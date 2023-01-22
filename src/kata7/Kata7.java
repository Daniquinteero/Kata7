package kata7;

import java.io.File;
import java.sql.SQLException;

public class Kata7 {
    
    public static void main(String[] args) throws SQLException {
        Iterable<Flight> flights = new SqliteFlightStore(new File("flights.db")).flights();
        
        new WebService(new HistogramBuilder(flights)).start();
        
        // Para probar a obtener un histograma serializado:
        //http://localhost/histogram/duration
        //http://localhost/histogram/destination
    }
    
}
