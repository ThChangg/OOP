package Classes.Classroom;

import Classes.Teachers.Teacher;

public class Grade {
    private int gradeNumber;
    private Teacher gradeManager;
	private boolean status = true;

	
	public Grade() {
	}

	public Grade(int gradeNumber, Teacher gradeManager, boolean status) {
		this.gradeNumber = gradeNumber;
		this.gradeManager = gradeManager;
		this.status = status;
	}

	public Grade(int gradeNumber, Teacher gradeManager) {
		this.gradeNumber = gradeNumber;
		this.gradeManager = gradeManager;
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

	@Override
	public String toString() {
		return String.format("%10d\t%15s", gradeNumber, gradeManager);
	}
}