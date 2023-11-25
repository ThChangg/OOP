package Classes.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private String fullname;
    private Date birthDate;
    private Address address;
    private String gender;

    public Person() {
    }

    public Person(String fullname, Date birthDate, Address address) {
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Person(String fullname, Date birthDate, Address address, String gender) {
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.address = address;
        this.gender = gender;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("%-20s\t%-6s\t%-10s\t%-80s", fullname, gender, birthDate, address);
    }

    public static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female");
    }

    public static boolean isValidName(String fullname) {
        boolean flag = true;
        String regex = "(^[A-Z][a-z]+)(\\s[A-Z][a-z]+){1,2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullname);
        if (!matcher.matches()) {
            flag = false;
        }

        return flag;
    }
}
