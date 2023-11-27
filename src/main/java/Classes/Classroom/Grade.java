package Classes.Classroom;

import Classes.Teachers.Teacher;

public class Grade {
    private int gradeNumber;
    private Teacher gradeManagerID;
	private static int numberOfPupilsInGrade[];
	private boolean status;

	public Grade() {
	}

	public Grade(int gradeNumber, Teacher gradeManagerID) {
		this.gradeNumber = gradeNumber;
		this.gradeManagerID = gradeManagerID;
		numberOfPupilsInGrade = new int[]{0, 0, 0, 0 , 0};
		this.status = true;
	}

	public Grade(int gradeNumber) {
		this.gradeNumber = gradeNumber;
		numberOfPupilsInGrade = new int[]{0, 0, 0, 0 , 0};
		this.status = true;
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

	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public static int[] getNumberOfPupilsInGrade() {
		return numberOfPupilsInGrade;
	}

	public static void setNumberOfPupilsInGrade(int numberOfPupils, int gradeNumber) {
		numberOfPupilsInGrade[gradeNumber - 1] = numberOfPupils;
	}

	@Override
	public String toString() {
		//return String.format("%10d\t%-10s", gradeNumber, gradeManagerID);
		return  gradeNumber + "-" + gradeManagerID;
	}
}