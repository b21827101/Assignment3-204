import java.util.ArrayList;

public class PathList {

    //2 int and string and arraylist for Listall
    int duration;
    int price;
    String startDate;//recorded startDate and finishDate to see if it matches the time.
    String finishDate;
    ArrayList<String> idArray; //flight id of each ListAll path
    ArrayList<String> pathArray; //all of listall pathArray

    public PathList(int duration, int price, ArrayList<String> idArray,ArrayList<String> pathArray ,String startDate,String finishDate) {
        this.idArray = idArray;
        this.pathArray = pathArray;
        this.duration = duration;
        this.price = price;
        this.startDate=startDate;
        this.finishDate=finishDate;

    }

    public ArrayList<String> getIdArray() { return idArray; }

    public ArrayList<String> getpathArray() { return pathArray; }

    public String getStartDate() { return startDate; }

    public String getFinishDate() { return finishDate; }

    public int getDuration() { return duration; }

    public int getPrice() {
        return price;
    }

}
