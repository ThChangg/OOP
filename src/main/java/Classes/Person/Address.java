package Classes.Person;

public class Address {
    private String houseNumber;
    private String streetName;
    private String ward;
    private String district;
    private String city;

    public Address() {
    }

    public Address(String houseNumber, String streetName, String ward, String district, String city) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.ward = ward;
        this.district = district;
        this.city = city;
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

    @Override
    public String toString() {
        return houseNumber + ", " + streetName + ", " + ward + ", " + district + ", " + city;
    }
}