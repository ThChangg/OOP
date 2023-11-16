package Classes.Pupils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Classes.Classroom.Classroom;
import Classes.Parents.Parent;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Person.Person;
import Classes.Points.Point;

public class Pupil extends Person {
    private String pupilID;
    private Classroom classroom;
    private Parent parents;
    private Point subjectPoints;
    private boolean status;
    private List<Parent> relatives;

    public Pupil() {
    }

    public Pupil(String pupilID, String fullname, Date dob, Address address) {
        super(fullname, dob, address);
        this.pupilID = pupilID;
        this.status = true;
        this.relatives = new ArrayList<>();
    }

    public Pupil(String pupilID, String fullname, Date dob, Address address, Classroom classroom) {
        super(fullname, dob, address);
        this.pupilID = pupilID;
        this.classroom = classroom;
        this.status = true;
        this.relatives = new ArrayList<>();
    }

    public String getPupilID() {
        return this.pupilID;
    }

    public void setPupilID(String pupilID) {
        this.pupilID = pupilID;
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

    public List<Parent> getRelatives() {
        return Collections.unmodifiableList(relatives); // Return an unmodifiable view of the list
    }

    public void addRelative(Parent relative) {
        this.relatives.add(relative);
    }

    public void removeRelative(Parent relative) {
        this.relatives.remove(relative);
    }

    @Override
    public String toString() {
        return pupilID + "\t" + super.toString();
    }
}
