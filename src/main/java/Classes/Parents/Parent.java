package Classes.Parents;

import Classes.Parents.Parent;
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

    public Parent(String parentID, String fullname, Date dob, Address address, String sex) {
        super(fullname, dob, address, sex);
        this.parentID = parentID;
        this.status = true;
    }

    public Parent(String parentID, String fullname, Date dob, Address address, String sex, String phoneNumber) {
        super(fullname, dob, address, sex);
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

    public boolean isStatus() {
        return this.status;
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
        return parentID + "\t" + super.toString() + "\t" + phoneNumber;
    }

}
