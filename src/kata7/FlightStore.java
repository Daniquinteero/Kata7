package kata7;

import java.io.Closeable;

public interface FlightStore extends Closeable {
    Iterable<Flight> flights();
        
}

