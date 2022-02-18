import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Flight {
    //six attributes for flightList.txt
    private String flight_id;
    private String dept;
    private String arr;
    private String dept_date;
    private String duration;
    private int price;

    public void setID(String flight_id) { this.flight_id=flight_id; }

    public void setDepth(String dept) { this.dept=dept; }

    public void setDuration(String duration) { this.duration = duration; }

    public void setArr(String arr) {
        this.arr = arr;
    }

    public void setDeptDate(String dept_date) { this.dept_date = dept_date; }

    public void setPrice(Integer price) { this.price = price; }

    public String getID() {
        return flight_id;
    }

    public String getDept() { return dept; }

    public String getDuration() {
        return duration;
    }

    public String getArr() {
        return arr;
    }

    public String getDeptDate() { return dept_date; }

    public int getPrice() {
        return price;
    }

    public String date(int i){ //take only date for comparing dates
        String[] recArr1 = ReadingFile.flightArray.get(i).getDeptDate().split(" ");
        return recArr1[0];

    }
    public String writeInTheFormofClock(int minutes){ //For writing in the form of clock
        String time=String.valueOf(minutes/60);
        String min=String.valueOf(minutes%60);
        if(minutes/60<9){ //if hour is smaller than 9,append 0 at the start of hour time
            time="0"+time;
        }
        if(minutes%60<9) {//if minutes is smaller than 9,append 0 at the start of minutes time
            min= "0"+min ;
        }
        return time+":"+min;

    }
    public long compareDate(String x,String y ) {  //compare two dates
        long diff = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // convert date present in the String to java.util.Date.
        try {
            Date date1= sdf.parse(x);
            Date date2 = sdf.parse(y);
            long elapse = date1.getTime() - date2.getTime();  // get the difference between two dates in minutes
            diff = TimeUnit.MINUTES.convert(elapse, TimeUnit.MILLISECONDS);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return diff; //return difference of two dates
    }

    public int calculateDept_dateTime(int i){
        String[] recArr1 = ReadingFile.flightArray.get(i).getDeptDate().split(" ");
        String[] date = recArr1[1].split(":");//calculate time of DeptDate in minutes
        return (Integer.parseInt(date[0])*60)+Integer.parseInt(date[1]);
    }

    public int calculateDurationTime(int i) {//calculate time of Duration in minutes
        String[] duration = ReadingFile.flightArray.get(i).getDuration().split(":");
        return (Integer.parseInt(duration[0])*60)+Integer.parseInt(duration[1]);
    }
}
