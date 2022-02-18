import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandList {

    public static ArrayList<PathList> listProper = new ArrayList<>();//listProper ArrayList
    static ArrayList<PathList> diameterOfGraph = new ArrayList<>();
    static ArrayList<String> Graph1 = new ArrayList<>();


    private int findFirstProperIndex(String eartliest){//return first proper index
        // for listproper(listCheapest,listQuickest) according to date info
        Flight f = new Flight();
        int index = 0;
        for (int i = 0; i < ListAll.listAllList.size(); i++) {
            if (f.compareDate(ListAll.listAllList.get(i).getStartDate(), eartliest) >= 0) {
                index = i ;
                break;
            }
        }
        return index;
    }

    public void listProper(String eartliest) {

        Flight f = new Flight();
        int index=findFirstProperIndex(eartliest);
        int minTime = ListAll.listAllList.get(index).getDuration();
        int minPrice = ListAll.listAllList.get(index).getPrice();
        listProper.add(ListAll.listAllList.get(index));

        for (int i = index+1; i < ListAll.listAllList.size(); i++) {
            long comparing = f.compareDate(ListAll.listAllList.get(i).getStartDate(), eartliest);
            //both quicker and cheaper than this flight (A)
            if (ListAll.listAllList.get(i).getDuration() < minTime && ListAll.listAllList.get(i).getPrice() < minPrice && comparing >= 0) {

                minTime = ListAll.listAllList.get(i).getDuration();
                minPrice = ListAll.listAllList.get(i).getPrice();
                listProper.add(ListAll.listAllList.get(i)); //add new
                listProper.remove(ListAll.listAllList.get(i - 1));//remove flight A

            }else if (ListAll.listAllList.get(i).getDuration() < minTime && comparing >= 0) {
                //only quicker  than this flight (A)
                minTime = ListAll.listAllList.get(i).getDuration();
                listProper.add(ListAll.listAllList.get(i));//add new

            }else if (ListAll.listAllList.get(i).getPrice() < minPrice && comparing >= 0) {
                //only cheaper than this flight (A)
                minPrice = ListAll.listAllList.get(i).getPrice();
                listProper.add(ListAll.listAllList.get(i));//add new

            }
        }
    }

    public void listCheapest(String earliest, String data) {
        ArrayList<PathList> listCheapest = new ArrayList<>();

        Flight f = new Flight();
        int index = findFirstProperIndex(earliest);
        int minPrice = ListAll.listAllList.get(index).getPrice();;//get minPrice of first proper index

        for (int i = index+1; i < ListAll.listAllList.size(); i++) {
            long comparing = f.compareDate(ListAll.listAllList.get(i).getStartDate(), earliest);
            if (ListAll.listAllList.get(i).getPrice() < minPrice && comparing >= 0) {//cheaper,append but clear bigger
                minPrice = ListAll.listAllList.get(i).getPrice();
                listCheapest.clear();
                listCheapest.add(ListAll.listAllList.get(i));
            }
            else if (ListAll.listAllList.get(i).getPrice() == minPrice && comparing >= 0) {//equal ,append
                listCheapest.add(ListAll.listAllList.get(i));
            }
        }
        if(listCheapest.isEmpty()){
            listCheapest.add(ListAll.listAllList.get(index));

        }

        ReadingFile.writeOutput(listCheapest,data);//print data in output.txt

    }

    public void listQuickest(String earliest,String data) {
        ArrayList<PathList> listQuickest = new ArrayList<>();
        Flight f = new Flight();
        int index = findFirstProperIndex(earliest);
        int minTime = ListAll.listAllList.get(index).getDuration();;//get minDuration of first proper index

        for (int i = index+1; i < ListAll.listAllList.size(); i++) {
            long comparing = f.compareDate(ListAll.listAllList.get(i).getStartDate(), earliest);
            if (ListAll.listAllList.get(i).getDuration() < minTime && comparing >= 0) {//smaller append but clear bigger Time
                minTime = ListAll.listAllList.get(i).getDuration();
                listQuickest.clear();
                listQuickest.add(ListAll.listAllList.get(i));//save in listQuickest
            }
            else if (ListAll.listAllList.get(i).getDuration() == minTime && comparing >= 0) {//equal ,append
                listQuickest.add(ListAll.listAllList.get(i));

            }
        }
        if(listQuickest.isEmpty()){
            listQuickest.add(ListAll.listAllList.get(index));

        }

        ReadingFile.writeOutput(listQuickest, data);//print data in output.txt

    }

    public void listCheaper(int cheaper, String earliest,String data) {
        ArrayList<PathList> listCheaper = new ArrayList<>();
        Flight f = new Flight();

        for (int i = 0; i < listProper.size(); i++) {//according data listProper,take cheaper than this.
            long comparing = f.compareDate(ListAll.listAllList.get(i).getStartDate(), earliest);
            if (listProper.get(i).getPrice() < cheaper && comparing >= 0) {
                listCheaper.add(listProper.get(i));//add cheaper in listCheaper
            }
        }
        ReadingFile.writeOutput(listCheaper, data);//print listCheaper data in output.txt

    }

    public void listExcluding(String id, String earliest,String data) {
        ArrayList<PathList> listExcluding = new ArrayList<>(listProper);
        Flight f = new Flight();
        for (PathList pathList : listProper) {//according to listProper
            for (int j = 0; j < pathList.getIdArray().size(); j++) {
                long comparing = f.compareDate(ListAll.listAllList.get(j).getStartDate(), earliest);
                if (pathList.getIdArray().get(j).contains(id) || comparing < 0) {
                    listExcluding.remove(pathList);//if it contains id,remove it
                    break;
                }
            }
        }
        ReadingFile.writeOutput(listExcluding, data);//print listExcluding data in output.txt

    }

    public void listQuicker(String startDate, String finishDate,String data) {
        ArrayList<PathList> listQuicker = new ArrayList<>();

        String arr = finishDate.split(" ")[1];
        int hour = Integer.parseInt(arr.split(":")[0]) * 60 + Integer.parseInt(arr.split(":")[1]);//convert minutes

        Flight f = new Flight();
        for (PathList pathList : listProper) {

            String arr1 = pathList.getFinishDate().split(" ")[1];
            int hour1 = Integer.parseInt(arr1.split(":")[0]) * 60 + Integer.parseInt(arr1.split(":")[1]);//convert minutes

            //if it is proper according to startDate and finish with using compareDate,append it in listQuicker

            if (f.compareDate(startDate, pathList.getStartDate()) <= 0 && f.compareDate(finishDate, pathList.getFinishDate()) + hour - hour1 > 0) {
                listQuicker.add(pathList);
            }
        }
        ReadingFile.writeOutput(listQuicker, data);//print listQuicker data in output.txt

    }

    public void listOnlyFrom(String id, String earliest,String data) {
        Flight f = new Flight();
        ArrayList<PathList> listOnlyFrom = new ArrayList<>(listProper);//copy the listProper
        for (PathList pathList : listProper) {
            for (int j = 0; j < pathList.getIdArray().size(); j++) {
                long comparing = f.compareDate(ListAll.listAllList.get(j).getStartDate(), earliest);
                if (!pathList.getIdArray().get(j).contains(id) || comparing < 0) {
                    listOnlyFrom.remove(pathList);//if it is not contain company,remove it
                    break;
                }
            }
        }
        ReadingFile.writeOutput(listOnlyFrom, data);//print listOnlyFrom data in output.txt

    }

    public int diameterOfGraph() {
        ListAll l2 = new ListAll();

        for (int i = 0; i < ReadingFile.vertices; i++) {
            for (int j = 0; j < Graph.graphList[i].size(); j++) {
                if (!Graph1.contains(Graph.getKey(Graph.numberofAirport, i))) {
                    Graph1.add(Graph.getKey(Graph.numberofAirport, i));
                }
                if (!Graph1.contains(Graph.getKey(Graph.numberofAirport, Graph.graphList[i].get(j).destination))) {//if it contains,don't append again
                    Graph1.add(Graph.getKey(Graph.numberofAirport, Graph.graphList[i].get(j).destination));
                }//All of using vertices is added in Graph1
            }
        }
        for (int i = 0; i < Graph1.size(); i++) {
            for (int j = 0; j < Graph1.size(); j++) {//All possible source and destination combinations
                if (i != j) {
                    int s = Graph.numberofAirport.get(Graph1.get(i));//source
                    int d = Graph.numberofAirport.get(Graph1.get(j));//destination
                    l2.printPaths(s, d, ReadingFile.airportArray.get(i).getCityName(), "Diameter");
                    l2.printPaths(d, s, ReadingFile.airportArray.get(j).getCityName(), "Diameter");
                }
            }
        }

        int maxprice = diameterOfGraph.get(0).getPrice();//maximum distance between the pair of vertices
        for (int i = 1; i < diameterOfGraph.size(); i++) {
            if (maxprice < diameterOfGraph.get(i).getPrice()) {
                maxprice = diameterOfGraph.get(i).getPrice();
            }
        }

        return maxprice;
    }

    public void saveList(ArrayList<String> idList, List<Integer> pathList,ArrayList<String> pathList1 ) {
        int totalPrice = 0;
        if (pathList.size() == 1) {
            return;
        }
        for (String s : idList) {
            for (int j = 0; j < ReadingFile.flightArray.size(); j++) {
                if (ReadingFile.flightArray.get(j).getID().equals(s)) {
                    totalPrice += ReadingFile.flightArray.get(j).getPrice();//calculating price for each path
                }
            }
        }
        for (int i = 0; i < diameterOfGraph.size(); i++) {

            String x = diameterOfGraph.get(i).getpathArray().get(0);
            int int1 = diameterOfGraph.get(i).getPrice();
            String y = diameterOfGraph.get(i).getpathArray().get(diameterOfGraph.get(i).getpathArray().size() - 1);

            if (pathList1.get(0).equals(x) && pathList1.get(pathList1.size() - 1).equals(y) && totalPrice > int1) {
                return;
            }//if source node and destination is equal and price is not more than other,remove it
            else if (pathList1.get(0).equals(x) && pathList1.get(pathList1.size() - 1).equals(y) && totalPrice <= int1) {
                diameterOfGraph.remove(i);
            }
        }
        ArrayList<String> path1 = new ArrayList<>(idList);
        PathList p1 = new PathList(0, totalPrice, path1, pathList1, "0", "0");
        diameterOfGraph.add(p1);//otherwise append it

    }


    public void pageRankOfNodes(String data) {
        HashMap<String, Float> pageRank = new HashMap<>();//contain nodes and page rank of each of them
        HashMap<String, Integer> numberofOutDegree = new HashMap<>();//contain nodes and their outdegrees
        HashMap<String, ArrayList<String>> name = new HashMap<>();//nodes and its attached nodes's arraylist

        for (int i = 0; i < Graph1.size(); i++) {

            int outDegree = 0;
            ArrayList<String> nodesNames = new ArrayList<>();
            String node = Graph1.get(i);

            for (int j = 0; j < ReadingFile.flightArray.size(); j++) {

                String dept=ReadingFile.flightArray.get(j).getDept();

                if (ReadingFile.flightArray.get(j).getArr().equals(node)) {
                    if (!nodesNames.contains(ReadingFile.flightArray.get(j).getDept())) {
                        nodesNames.add(dept);//for each nodes,append its connecting node,if it does not already contain
                    }
                }
                else if (dept.equals(node)) {//if they are same nodes,increase outDegree
                    outDegree++;
                }
            }
            numberofOutDegree.put(node, outDegree);//number of out bounds links
            pageRank.put(node, ((float)1/Graph1.size()));
            name.put(node, nodesNames);

        }
        pageRank(numberofOutDegree,name,pageRank,data);

    }

    private float calculateSumPageRank(HashMap<String, Float> pageRank){//calculate sum of previous pagerank
        float pastSum =0;
        for (String i : pageRank.keySet()) {
            pastSum+=pageRank.get(i);
        }
        return pastSum;
    }

    private void pageRank(HashMap<String, Integer> number, HashMap<String, ArrayList<String>> name, HashMap<String, Float> pageRank ,String data){
        float pastSum =calculateSumPageRank(pageRank);//previous sum

        for (String i : name.keySet()) {
            float pr = 0;
            for (int x = 0; x < name.get(i).size(); x++) {
                for (String j : number.keySet()) {
                    if (name.get(i).get(x).equals(j)) {
                        pr += pageRank.get(j)/ number.get(j);//so for each node,i calculated connecting node's pagerank/outDegree of connecting nodes
                        //and append it in float value
                    }
                }
            }
            String formattedString = String.format("%.3f",0.150 + (0.850*pr));
            String asd = formattedString.replace(",", ".");
            pageRank.remove(i);//removed old value and append new value of node
            pageRank.put(i, Float.parseFloat(asd));
        }

        float sumOfNewValuesofNodes =calculateSumPageRank(pageRank);//new sum


        if(pastSum!=sumOfNewValuesofNodes){//compare previous and now,if it is not equal ,work the method again
            pageRank(number,name,pageRank,data);
        }
        else{
            ReadingFile.writePageRank(pageRank,data);//if they are equals ,finish this and print pageRank in output.txt
        }
    }
}

