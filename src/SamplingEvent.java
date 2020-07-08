public class SamplingEvent {
    private int year, month, date, hrs, min;
    private String location;
    private int samplingid;

    public SamplingEvent(int year, int month, int date, int hrs, int min, String location) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hrs = hrs;
        this.min = min;
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHrs() {
        return hrs;
    }

    public void setHrs(int hrs) {
        this.hrs = hrs;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
