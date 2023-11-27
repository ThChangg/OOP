package Classes.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
    private String houseNumber;
    private String streetName;
    private String ward;
    private String district;
    private String city;
    private static String addressRegex = "(\\S.*),\\s(.*),\\s(Phuong\\s.*),\\s(Quan\\s.*),\\s(Thanh pho\\s.*$)";

    public Address() {
    }

    public Address(String houseNumber, String streetName, String ward, String district, String city) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.ward = ward;
        this.district = district;
        this.city = city;
    }

    public Address(String address) {
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(address);
        if (matcher.matches()) {
            this.houseNumber = matcher.group(1);
            this.streetName = "Duong " + matcher.group(2);
            this.ward = matcher.group(3);
            this.district = matcher.group(4);
            this.city = matcher.group(5);
        }
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getWard() {
        return this.ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static String getAddressRegex() {
        return addressRegex;
    }

    @Override
    public String toString() {
        return houseNumber + ", " + streetName + ", " + ward + ", " + district + ", " + city;
    }

    public static boolean isValidAddress(String address) {
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(address);

        boolean flag = true;
        if (!matcher.matches()) {
            flag = false;
        }
        return flag;
    }
}