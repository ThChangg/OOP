package Classes.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean isValidDateAndMonth(String date) {
        boolean flag = true;
        String regex = "\\d+\\/\\d+\\/\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        if (matcher.matches()) {
            String dateParts[] = date.split("/");
            int day = Integer.parseInt(dateParts[0]), month = Integer.parseInt(dateParts[1]);
            if (month < 1 || month > 12) {
                flag = false; // Invalid month
            }

            int[] daysInMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

            if (day < 1 || day > daysInMonth[month]) {
                flag = false; // Invalid day for the given month
            }

        } else {
            flag = false;
        }
        return flag;
    }
}