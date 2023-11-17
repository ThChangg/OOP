package Classes.Parents;

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Person.Person;

public class Parent extends Person {
	private String pupilID;
    private String phoneNumber;
    private boolean status;

    public Parent() {
    }

    public Parent(String pupilID, String fullname, Date dob, Address address, String sex, String phoneNumber) {
        super(fullname, dob, address, sex);
        this.pupilID = pupilID;
        this.phoneNumber = phoneNumber;
        this.status = true;
    }

    public String getPupilID() {
        return this.pupilID;
    }

    public void setPupilID(String pupilID) {
        this.pupilID = pupilID;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return pupilID + "\t" + super.toString() + "\t" + phoneNumber; 
    }
}
