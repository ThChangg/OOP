package Classes.Classroom;

import Classes.Teachers.Teacher;

public class Grade {
    private int gradeNumber;
    private Teacher gradeManagerID;
    
    
	public Grade() {
	}

	public Grade(int gradeNumber, Teacher gradeManagerID) {
		this.gradeNumber = gradeNumber;
		this.gradeManagerID = gradeManagerID;
	}

	public Grade(int gradeNumber) {
		this.gradeNumber = gradeNumber;
	}

	public int getGradeNumber() {
		return gradeNumber;
	}

	public void setGradeNumber(int gradeNumber) {
		this.gradeNumber = gradeNumber;
	}

	public Teacher getGradeManagerID() {
		return gradeManagerID;
	}

	public void setGradeManagerID(Teacher gradeManagerID) {
		this.gradeManagerID = gradeManagerID;
	}

	@Override
	public String toString() {
		return String.format("%10d\t%-10s", gradeNumber, gradeManagerID);
	}
    
    
}