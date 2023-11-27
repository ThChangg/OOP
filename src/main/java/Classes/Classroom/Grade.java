package Classes.Classroom;

import Classes.Teachers.Teacher;

public class Grade {
    private int gradeNumber;
	private static int numberOfPupilsInGrade[] = new int[]{0, 0, 0, 0 , 0};
    private Teacher gradeManager;
	private boolean status;

	
	public Grade() {
	}

	public Grade(int gradeNumber, Teacher gradeManager, boolean status) {
		this.gradeNumber = gradeNumber;
		this.gradeManager = gradeManager;
		this.status = true;
	}

	public Grade(int gradeNumber, Teacher gradeManager) {
		this.gradeNumber = gradeNumber;
		this.gradeManager = gradeManager;
	}

	public Grade(int gradeNumber) {
		this.gradeNumber = gradeNumber;
		this.status = true;
	}

	public Grade(Teacher gradeManager) {
		this.gradeManager = gradeManager;
		this.status = true;
	}

	public int getGradeNumber() {
		return gradeNumber;
	}

	public void setGradeNumber(int gradeNumber) {
		this.gradeNumber = gradeNumber;
	}

	public Teacher getGradeManager() {
		return gradeManager;
	}

	public void setGradeManager(Teacher gradeManager) {
		this.gradeManager = gradeManager;
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
		return String.format("%-15d\t%-15s", gradeNumber, gradeManager.getTeacherID());
	}
}