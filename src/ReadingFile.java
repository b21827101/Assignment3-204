import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ReadingFile {
    public static ArrayList<Airport> airportArray = new ArrayList<>();//save data of airport.txt
    public static ArrayList<Flight> flightArray = new ArrayList<>();//save data of flight.txt
    public static int vertices;

    static void main(String arg0,String arg1,String arg2) {//String arg0,String arg1,String arg2
        readFile(arg0);//"airportList.txt"
        readFlight(arg1);//"flightList.txt"
        for (int i = 0; i < ReadingFile.airportArray.size(); i++) {
            for (int j = 0; j < ReadingFile.airportArray.get(i).getAirport_alias().size(); j++) {
                vertices++;//find number of vertices in the graph
            }
        }
        Graph g = new Graph(vertices);
        g.main();
        readCommand(arg2);//"commandList.txt"
    }

    private static void readFile(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] recArr = data.split("\t");//spliting from tab
                ArrayList<String> a1 = new ArrayList<>();

                Airport a = new Airport();
                for (int i = 0; i < recArr.length; i++) {
                    if (i == 0) {
                        a.setCityName(recArr[0]);
                    } else {
                        a1.add(recArr[i]);//airport_alias arraylist for each city
                    }
                }
                a.setAirport_alias(a1);
                airportArray.add(a);//add all of data from airportList.txt
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void readFlight(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] recArr = data.split("\t");
                Integer price1 = Integer.parseInt(recArr[4]);//price
                String[] recArr1 = recArr[1].split("->");//contain arr,dept,duration,DeptDate

                Flight a1 = new Flight();

                a1.setID(recArr[0]);
                a1.setDepth(recArr1[0]);
                a1.setArr(recArr1[1]);
                a1.setDeptDate(recArr[2]);
                a1.setDuration(recArr[3]);
                a1.setPrice(price1);
                flightArray.add(a1);//add all of data from flightList.txt
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void readCommand(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            FileWriter myWriter = new FileWriter("output.txt");


            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] recArr = data.split("\t");

                ListAll c = new ListAll();
                CommandList command = new CommandList();

                if (data.contains("listAll")) {
                    String[] recArr1 = recArr[1].split("->");
                    c.listAll(recArr1[0], recArr1[1]);
                    c.earliestDate(recArr[2]);//date
                    writeOutput(ListAll.listAllList, data);
                }
                else if (data.contains("listProper")) {
                    command.listProper(recArr[2]);
                    writeOutput(CommandList.listProper, data);
                }
                else if (data.contains("listCheapest")) {
                    command.listCheapest(recArr[2], data);
                }
                else if (data.contains("listQuickest")) {
                    command.listQuickest(recArr[2],data);
                }
                else if (data.contains("listCheaper")) {
                    int cheaper = Integer.parseInt(recArr[3]);
                    command.listCheaper(cheaper, recArr[2],data);
                }
                else if (data.contains("listQuicker")) {
                    command.listQuicker(recArr[2], recArr[3],data);
                }
                else if (data.contains("listExcluding")) {
                    command.listExcluding(recArr[3], recArr[2],data);
                }
                else if (data.contains("listOnlyFrom")) {
                    command.listOnlyFrom(recArr[3], recArr[2],data);
                }
                else if (data.contains("diameterOfGraph")) {
                    int x = command.diameterOfGraph();
                    writeDiameter(x,data);
                }
                else if (data.contains("pageRankOfNodes")) {
                    command.pageRankOfNodes(data);

                }
            }
            myReader.close();
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDiameter(Integer x,String data) {//print diameterOfGraph
        try {
            FileWriter myWriter = new FileWriter("output.txt",true);//appending
            myWriter.write("command : " + data + "\n");
            myWriter.write("The diameter of graph : "+x+"\n\n\n");
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writePageRank(HashMap<String, Float> pageRank, String data) {//print pageRank hashmap
        try {
            FileWriter myWriter = new FileWriter("output.txt",true);
            myWriter.write("command : " + data + "\n");
            for (String i : pageRank.keySet()) {
                String[] splitter = pageRank.get(i).toString().split("\\.");
                if(splitter[1].length()<3){
                    myWriter.write(i + " : " + pageRank.get(i) + "0\n");
                }
                else {
                    myWriter.write(i + " : " + pageRank.get(i) + "\n");
                }
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void writeOutput(ArrayList<PathList> listArray, String data) {
        try {
            FileWriter myWriter = new FileWriter("output.txt",true);//appending
            myWriter.write("command : " + data + "\n");

            if (listArray.isEmpty()) {//if it is empty,print this.
                myWriter.write("No suitable flight plan is found\n\n\n");
                myWriter.close();
                return;
            }

            Flight f = new Flight();

            for (int i = 0; i < listArray.size(); i++) {
                for (int j = 0; j < listArray.get(i).getpathArray().size() - 1; j++) {
                    myWriter.write(listArray.get(i).getIdArray().get(j) + "\t" + listArray.get(i).getpathArray().get(j) + "->"
                            + listArray.get(i).getpathArray().get(j + 1));

                    if (j + 1 != listArray.get(i).getpathArray().size() - 1) {//end of the ararylist,don't write this.
                        myWriter.write("||");
                    }
                }
                String time = f.writeInTheFormofClock(listArray.get(i).getDuration());//write in the form of clock each time
                myWriter.write("\t" + time + "/" + listArray.get(i).getPrice() + "\n");
            }
            myWriter.write("\n\n");
            myWriter.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
