package kata7;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Iterator;

public class SqliteFlightStore implements FlightStore, Closeable {
    private final File file;
    private final Connection connection;

    public SqliteFlightStore(File file) throws SQLException {
        this.file = file;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }
    
    @Override
    public Iterable<Flight> flights() {
        return new Iterable<Flight>() {

            @Override
            public Iterator<Flight> iterator() {
                try {
                    return createIterator();
                } catch (SQLException ex) {
                    return Collections.emptyIterator();
                }
            }

        };
    }

    private Iterator<Flight> createIterator() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM flights WHERE cancelled == 0");
        return new Iterator<Flight>() {
            Flight nextFlight = nextFlight();

            @Override
            public boolean hasNext() {
                return nextFlight != null;
            }

            @Override
            public Flight next() {
                Flight result = nextFlight;
                nextFlight = nextFlight();
                return result;
            }

            private Flight nextFlight() {
                return hasNextFlight() ? createFlight() : null;
            }

            private Flight createFlight() {
                return new Flight(
                    DayOfWeek.of(getInt("DAY_OF_WEEK")),
                    timeIn(getInt("DEP_TIME")),
                    timeIn(getInt("ARR_TIME")),
                    getInt("DEP_DELAY"),
                    getInt("ARR_DELAY"),
                    getInt("AIR_TIME"),
                    getInt("DISTANCE"),
                    getInt("CANCELLED") == 1,
                    getInt("DIVERTED") == 1
                );
            }
            
            private int getInt(String field) {
                try {
                    return rs.getInt(field);
                } catch (SQLException ex) {
                    return 0;
                }
            }

            private boolean hasNextFlight() {
                try {
                    boolean next = rs.next();
                    if (!next) rs.close();
                    return next;
                } catch (SQLException ex) {
                    return false;
                }
            }

            private LocalTime timeIn(int time) {
                return LocalTime.of(time / 100 % 24, time % 100);
            }

        };
    }
    
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }
    
}
