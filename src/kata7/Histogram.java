package kata7;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Histogram<T> {
    private Map<T, Integer> map;

    public Histogram() {
        this.map = new HashMap<T, Integer>();
    }
    
    public Integer get(T key) {
        return this.map.get(key);
    } 
    
    public Set<T> keySet(){
        return this.map.keySet();
    }
    
    public void increment(T key) {
        this.map.put(key, this.map.containsKey(key) ? this.map.get(key) +1 : 1);
    }  
}
