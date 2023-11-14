package Classes.Person;

public class Person {
    private String fullname;
    private Date birthDate;
    private Address address;

    public Person() {
    }

    public Person(String fullname, Date birthDate, Address address) {
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.address = address;
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

    @Override
    public String toString() {
        return String.format("%-20s\t%-10s\t%-80s", fullname, birthDate, address);
    }

    public String toStringWithoutAddress() {
        return String.format("%-20s\t%-10s", fullname, birthDate);
    }
}
