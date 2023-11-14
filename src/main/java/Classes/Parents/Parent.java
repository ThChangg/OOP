package Classes.Parents;

import Classes.Classroom.Classroom;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Person.Person;
import Classes.Points.Point;

public class Parent extends Person {
	private String parentID;
    private Classroom classroom;
    private Parent parents;
    private Point subjectPoints;
    private boolean status;
    private String phoneNumber;

    public Parent() {
    }

    public Parent(String parentID, String fullname, Date dob, Address address) {
        super(fullname, dob, address);
        this.parentID = parentID;
        this.status = true;
    }

    public Parent(String parentID, String fullname, Date dob, Address address, Classroom classroom) {
        super(fullname, dob, address);
        this.parentID = parentID;
        this.classroom = classroom;
        this.status = true;
    }

    public String getParentID() {
        return this.parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Parent getParents() {
        return this.parents;
    }

    public void setParents(Parent parents) {
        this.parents = parents;
    }

    public Point getSubjectPoints() {
        return this.subjectPoints;
    }

    public void setSubjectPoints(Point subjectPoints) {
        this.subjectPoints = subjectPoints;
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
        // Thêm số điện thoại vào chuỗi khi hiển thị thông tin
        return parentID + "\t" + super.toString() + "\t" + phoneNumber; 
    }


}
