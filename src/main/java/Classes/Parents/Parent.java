package Classes.Parents;

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Person.Person;
import Classes.Pupils.Pupil;

public class Parent extends Person {
    private String parentID;
    private String phoneNumber;
    private boolean status;
    private Pupil pupil;

    public Parent() {
    }

    public Parent(String parentID, String fullname, Date dob, Address address, String gender) {
        super(fullname, dob, address, gender);
        this.parentID = parentID;
        this.status = true;
    }

    public Parent(String parentID, String fullname, Date dob, Address address, String gender, String phoneNumber, Pupil pupil) {
        super(fullname, dob, address, gender);
        this.pupil = pupil;
        this.parentID = parentID;
        this.phoneNumber = phoneNumber;
        this.status = true;
    }

    public String getParentID() {
        return this.parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public Pupil getPupil() {
        return this.pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
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

    private static final String PHONE_NUMBER_PATTERN = "^(0[0-9]{9})$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }

    @Override
    public String toString() {
        return parentID + "\t" + super.toString() + "\t" + phoneNumber;
    }
}
