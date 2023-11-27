package Classes.Teachers;

import Classes.Classroom.Classroom;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Person.Person;


public class Teacher extends Person {
    private String teacherID;
    private Classroom classroom;
    private String major;
    private boolean status = true;
    public Teacher() {
    }

    public Teacher(String teacherID) {
        this.teacherID = teacherID;
    }
 
    public Teacher(String teacherID, String fullname, Date birthDate, Address address) {
        super(fullname, birthDate, address);
        this.teacherID = teacherID;
    }

    public Teacher(String teacherID, Classroom classroom, String major, String fullname, Date birthDate, Address address) {
        super(fullname, birthDate, address);
        this.teacherID = teacherID;
        this.classroom = classroom;
        this.major = major;
    }

    public Teacher(String teacherID, String fullname, Date birthDate, Address address,  String gender, String major) {
        super(fullname, birthDate, address, gender);
        this.teacherID = teacherID;
        this.major = major;
    }

    public Teacher(String teacherID, String fullname, Date birthDate, Address address, String major, Classroom classroom, String gender) {
        super(fullname, birthDate, address, gender);
        this.teacherID = teacherID;
        this.classroom = classroom;
        this.major = major;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if(classroom == null){
            return teacherID  + "\t"+ super.toString() + "\t" + major + "\t" + null;
        }
        return teacherID  + "\t"+ super.toString() + "\t" + major + "\t" + classroom.getClassName();
    }

}
