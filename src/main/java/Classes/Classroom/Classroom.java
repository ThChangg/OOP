package Classes.Classroom;


import Classes.Teachers.Teacher;


public class Classroom {
    private String className;
    private Teacher classManagerID;
    private Grade grade;
	private boolean status;
    
    public Classroom() {
	}

	public Classroom(String className, Teacher classManagerID, Grade grade) {
		this.className = className;
		this.classManagerID = classManagerID;
		this.grade = grade;
		this.status = true;
	}

	public Classroom(String className, Grade grade) {
		this.className = className;
		this.grade = grade;
		this.status = true;
	}

	public Classroom(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Teacher getClassManagerID() {
		return classManagerID;
	}

	public void setClassManagerID(Teacher classManagerID) {
		this.classManagerID = classManagerID;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public boolean isStatus() {
		return status;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
//		return String.format("%-20s\t%-10s\t%-70s", className, classManagerID, grade);
		return className + "-" + classManagerID + "-" + grade;
	}

}