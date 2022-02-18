import java.util.*;

public class Graph {

    private final int vertices;
    public static ArrayList<Edges>[] graphList;
    public static HashMap<String, Integer> numberofAirport = new HashMap<>();//airport with its index in numberOfAirport Hashmap

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;//initializing vertices
        graphList = (ArrayList<Edges>[]) new ArrayList[vertices]; //initializing graphList
        for (int v = 0; v < vertices; v++) {
            graphList[v] = new ArrayList<Edges>();
        }
    }


    private void addEdge(int source, int destination, String weight) {    // add edge from source to destination
        Edges edge = new Edges(source, destination, weight); //source node,destination node and it's weight
        graphList[source].add(edge); // Add edge to source.


    }

    private void addingEdge(){

        for(int i=0;i<ReadingFile.flightArray.size();i++)        {
            int x =numberofAirport.get(ReadingFile.flightArray.get(i).getDept()); //source
            int y =numberofAirport.get(ReadingFile.flightArray.get(i).getArr());  //destination
            addEdge(x, y, ReadingFile.flightArray.get(i).getID()); //add edge for connecting nodes and save with weight
        }
    }


    public void main() {

        int vertices1=0;
        for(int i=0;i<ReadingFile.airportArray.size();i++)        {
            for(int j=0;j<ReadingFile.airportArray.get(i).getAirport_alias().size();j++) { //give a int value for each node
                numberofAirport.put(ReadingFile.airportArray.get(i).getAirport_alias().get(j), vertices1);//put with airport name and int value
                vertices1++;
            }
        }
        addingEdge();
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {//to get Map's key from the value
        for (Map.Entry<K, V> entry: map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
