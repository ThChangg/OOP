package Classes.Pupils;


import java.lang.reflect.Field;
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

    public Pupil() {
    }

    public Pupil(String pupilID) {
        this.pupilID = pupilID;
        this.status = true;
    }

    public Pupil(String pupilID, String fullname, Date dob, Address address, String gender) {
        super(fullname, dob, address, gender);
        this.pupilID = pupilID;
        this.status = true;
    }

    public Pupil(String pupilID, String fullname, Date dob, Address address, String gender, Classroom classroom) {
        super(fullname, dob, address, gender);
        this.pupilID = pupilID;
        this.classroom = classroom;
        this.status = true;
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
        this.classroom = new Classroom(); // Assuming Classroom has a default constructor

        // Use reflection to copy fields from the parameter object to the current object
        Field[] fields = Classroom.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.set(this.classroom, field.get(classroom));
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        }
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

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return pupilID + "\t" + super.toString() + "\t" + classroom.getClassName();
    }
}
