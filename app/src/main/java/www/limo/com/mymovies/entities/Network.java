package www.limo.com.mymovies.entities;

import java.util.*;

public class Network {
    private long id;
    private String name;
    private Country country;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public Country getCountry() { return country; }
    public void setCountry(Country value) { this.country = value; }
}
