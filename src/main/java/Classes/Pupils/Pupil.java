package Classes.Pupils;

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

    public Pupil() {
    }

    public Pupil(String pupilID, String fullname, Date dob, Address address) {
        super(fullname, dob, address);
        this.pupilID = pupilID;
    }

    public Pupil(String pupilID, String fullname, Date dob, Address address, Classroom classroom) {
        super(fullname, dob, address);
        this.pupilID = pupilID;
        this.classroom = classroom;
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

    @Override
    public String toString() {
        return pupilID + "\t" + super.toString(); 
    }
}
