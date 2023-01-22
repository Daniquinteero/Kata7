package kata7;

import spark.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WebService {
    private final HistogramBuilder histogramBuilder;

    public WebService(HistogramBuilder histogramBuilder) {
        this.histogramBuilder = histogramBuilder;
    }
    
    
    public void start() {
        Spark.port(80);
        Spark.get("/histogram/:type", this::histogram);
        
        //http://localhost/histogram/duration
        //http://localhost/histogram/destination
    }
    
    
    private String histogram(Request request, Response response) {
        Histogram histogram = histogramBuilder.build(request.params(":type"));
        return serialize(histogram);
    }
    
    private String serialize(Histogram histogram) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String jsonString = gson.toJson(histogram);
        return jsonString;
    }
    
}
