package Classes.Teachers;

import Classes.Classroom.Classroom;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Person.Person;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Teacher extends Person {
    private String teacherID;
    private Classroom classroom;
    private String major;
    private boolean status = true;


    public Teacher() {
    }

    public Teacher(String teacherID, String fullname, Date birthDate, Address address) {
        super(fullname, birthDate, address);
        this.teacherID = teacherID;
    }

    public Teacher(String teacherID, String fullname, Date birthDate, Address address, String sex, String major) {
        super(fullname, birthDate, address, sex);
        this.teacherID = teacherID;
        this.major = major;
    }

    public Teacher(String teacherID, Classroom classroom, String major, String fullname, String sex, Date birthDate, Address address) {
        super(fullname, birthDate, address, sex);
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
    
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return teacherID  + "\t"+ super.toString() + "\t" + major + "\t" + classroom.getClassName();
    }
}
