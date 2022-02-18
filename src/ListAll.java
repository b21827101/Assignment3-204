import java.util.ArrayList;
import java.util.List;

public class ListAll {
    public static ArrayList<PathList> listAllList = new ArrayList<>();

    public void printPaths(int s, int d,String startingcity,String parameter1) {//Save all proper paths from source to destination(s to d)
        boolean[] isVisited = new boolean[ReadingFile.vertices];
        ArrayList<Integer> pathList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> citiesList = new ArrayList<>();
        citiesList.add(startingcity);  //add start city in citiesList
        pathList.add(s);  // add source in pathList
        int xS = 0;
        findProperPaths(xS, s, d, isVisited, pathList, idList, citiesList,parameter1);
    }

    public void listAll(String depth,String arr){

        int depthIndex = 0;
        int arrIndex = 0;
        for(int i=0;i<ReadingFile.airportArray.size();i++){
            if(ReadingFile.airportArray.get(i).getCityName().equals(depth)){
                depthIndex = i;
            }
            else if(ReadingFile.airportArray.get(i).getCityName().equals(arr)){
                arrIndex = i;
            }
        }
        //find all possibilities airports for two cities
        for(int i=0;i<ReadingFile.airportArray.get(depthIndex).getAirport_alias().size();i++) {

            for (int j = 0; j < ReadingFile.airportArray.get(arrIndex).getAirport_alias().size(); j++) {
                Integer s=Graph.numberofAirport.get(ReadingFile.airportArray.get(depthIndex).getAirport_alias().get(i));
                Integer d= Graph.numberofAirport.get(ReadingFile.airportArray.get(arrIndex).getAirport_alias().get(j));

                printPaths(s,d,ReadingFile.airportArray.get(depthIndex).getCityName(),"ListAll");
            }
        }

    }

    public String citiesCompares(Integer in){ // for "Transferring between two airports at the same city is not allowed."
        String x = null;
        for(int y=0;y<ReadingFile.airportArray.size();y++) {
            if (ReadingFile.airportArray.get(y).getAirport_alias().contains(Graph.getKey(Graph.numberofAirport, in))) {
                x = ReadingFile.airportArray.get(y).getCityName();
            }
        }
        return x; //finding airport's city name
    }

    public void earliestDate(String eartliest) {
        Flight f = new Flight();
        for (int i = 0; i < ListAll.listAllList.size(); i++) {
            if(f.compareDate(ListAll.listAllList.get(i).getStartDate(),eartliest)<0){
                listAllList.remove(i);//if a path date is earlier than listAll [start_date],remove it
            }
        }
    }
    public void findProperPaths(Integer xS,Integer u, Integer d, boolean[] isVisited, List<Integer> pathList,
                                ArrayList<String> idList,ArrayList<String> citiesList,String parameter1) {
        if(xS==1){//the path is not proper for listAll
            return;
        }
        if (u.equals(d)) { /// if a path found
            ArrayList<String> pathList1 = new ArrayList<>();

            for (Integer integer : pathList) {
                for (String i : Graph.numberofAirport.keySet()) {
                    if (integer.equals(Graph.numberofAirport.get(i))) {
                        pathList1.add(i); } }
            }
            if(parameter1.equals("ListAll")){//for list all
                saveListAllInfo(idList,pathList,pathList1);
            }
            else if(parameter1.equals("Diameter")){//for diameter of graph
                CommandList c= new CommandList();
                c.saveList(idList,pathList,pathList1);
            }
            return;
        }
        isVisited[u] = true;//mark node

        for (Edges i : Graph.graphList[u]) {
            if (!isVisited[i.destination]) {

                String y=citiesCompares(i.destination);
                pathList.add(i.destination);// store current node in pathList
                idList.add(i.weight);// store id in idList

                if(citiesList.contains(y)){ xS=1; }  // different airport in same city is forbidden
                if(pathList.size()>=3 &&(!timeCompare(idList))&& parameter1.equals("ListAll")){ xS=1; }//comparing proper time

                citiesList.add(y);// store city name in citiesList
                findProperPaths(xS,i.destination, d, isVisited, pathList,idList,citiesList,parameter1);

                pathList.remove(new Integer(i.destination));//remove current node in pathList
                citiesList.remove(y);// remove city in citiesList
                idList.remove(i.weight);// remove id in idList
                xS=0;
            }
        }
        isVisited[u] = false;
    }

    private boolean timeCompare(List<String> idList) {// if Departure time of the second flight is not later than
        // arrival time of the first flight,return false
        int starttime = -1;
        int finishtime = -1;
        String finishDate = null;
        String startDate = null;
        Flight f = new Flight();

        for (int i = 0; i < idList.size()-1; i++) {
            for (int j = 0; j < ReadingFile.flightArray.size(); j++) {//arrival time of the first flight
                if (ReadingFile.flightArray.get(j).getID().equals(idList.get(i))) {
                    finishDate=f.date(j);//date
                    finishtime = f.calculateDept_dateTime(j) + f.calculateDurationTime(j);//hour and duration
                }
                if (ReadingFile.flightArray.get(j).getID().equals(idList.get(i + 1))) { //Departure time of the second flight
                    startDate=f.date(j);//date
                    starttime = f.calculateDept_dateTime(j);//hour
                }
            }
        }
        int c=(int)(f.compareDate(startDate,finishDate));
        return c + starttime > finishtime;

    }

    private void saveListAllInfo(ArrayList<String> idList,List<Integer> pathList,ArrayList<String> pathList1 ){
        int totalPrice =0;
        int starttime=0;
        int finishtime = 0;
        String finishDate = null;
        String finishDate1 = null;
        String startDate = null;
        Flight f = new Flight();
        //save idList ,totalTime,total Price,listofnode,start date and finishdate in listAll Arraylist
        for(int i=0;i<idList.size();i++){
            for(int j=0;j<ReadingFile.flightArray.size();j++) {

                if (ReadingFile.flightArray.get(j).getID().equals(idList.get(i))) {
                    totalPrice+=ReadingFile.flightArray.get(j).getPrice();

                    if(i==0){
                        starttime =f.calculateDept_dateTime(j);
                        startDate=f.date(j);
                    }
                    if(i==idList.size()-1){
                        finishDate1=ReadingFile.flightArray.get(j).getDeptDate();
                        finishtime =f.calculateDept_dateTime(j)+f.calculateDurationTime(j);
                        finishDate=f.date(j);
                    }
                }
            }
        }
        int c=(int)(f.compareDate(finishDate,startDate));
        int totalTime = finishtime-starttime+c;  //calculate total time
        ArrayList<String> person1 = new ArrayList<>(idList);
        PathList p1 = new PathList(totalTime,totalPrice,person1, pathList1,startDate,finishDate1);
        listAllList.add(p1);//add in listAll
    }
}
