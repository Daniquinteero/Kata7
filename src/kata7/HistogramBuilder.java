package kata7;

public class HistogramBuilder {
    private final Iterable<Flight> flights;
    
    
    public HistogramBuilder(Iterable<Flight> flights) {
        this.flights = flights;
    }
    
    
    public Histogram build(String field) {
        Histogram histogram = new Histogram();
        for (Flight flight : this.flights) {
            histogram.increment(flight.get(field));
        }
        return histogram;
        
    }
    
    
}
