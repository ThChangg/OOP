package Classes.Person;

public class Date {
    private String date;
    private String month;
    private String year;

    public Date() {
    }

    public Date(String date, String month, String year) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public Date(String date) {
        String dobParts[] = date.split("/");
        this.date = dobParts[0];
        this.month = dobParts[1];
        this.year = dobParts[2];
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return date + "/" + month + "/" + year;
    }
}