import java.util.ArrayList;

public class Airport {

    private String city_name;  //city name attributes for airport.txt
    private ArrayList<String> airport_alias = new ArrayList<>();  //airport alias ArrayList for each city

    public void setCityName(String city_name) { this.city_name=city_name; }

    public void setAirport_alias(ArrayList<String>  airport_alias) { this.airport_alias=airport_alias; }

    public String getCityName() {
        return city_name;
    }

    public ArrayList<String> getAirport_alias() { return airport_alias; }


}
