package Classes.Classroom;


import Classes.Teachers.Teacher;


public class Classroom {
    private String className;
    private Teacher classManager;
    private Grade grade;
	private boolean status = true;
    
    public Classroom() {
	}

	public Classroom(String className, Teacher classManager, Grade grade, boolean status) {
		this.className = className;
		this.classManager = classManager;
		this.grade = grade;
		this.status = status;
	}

	public Classroom(String className, Teacher classManager, Grade grade) {
		this.className = className;
		this.classManager = classManager;
		this.grade = grade;
	}

	public Classroom(String className, Teacher classManager) {
		this.className = className;
		this.classManager = classManager;
	}

	public Classroom(String className, Grade grade) {
		this.className = className;
		this.grade = grade;
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

	public Teacher getClassManager() {
		return classManager;
	}

	public void setClassManager(Teacher classManager) {
		this.classManager = classManager;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("%-10s\t%-10s\t%-5s", className, classManager, grade);
	}

}