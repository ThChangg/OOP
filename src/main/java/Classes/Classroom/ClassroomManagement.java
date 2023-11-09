package Classes.Classroom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


import Classes.Teachers.Teacher;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;


public class ClassroomManagement implements IFileManagement, ICRUD {
    private Classroom classroomManagement[];
    private int currentIndex;
    private static int numberOfPupil;
    
    public ClassroomManagement() {
    	this.classroomManagement = new Classroom[100];
		currentIndex = 0;
		numberOfPupil = 0;
	}
    
    public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public static int getNumberOfPupil() {
		return numberOfPupil;
	}

	public static void setNumberOfPupil(int numberOfPupil) {
		ClassroomManagement.numberOfPupil = numberOfPupil;
	}
	
	@Override
	public void add(Object obj) {
		if (currentIndex < classroomManagement.length) {
			classroomManagement[currentIndex] = (Classroom) obj;
        } else {
            System.out.println("Classroom Management List is full. Cannot add more.");
        }
		currentIndex++;
		numberOfPupil++;
    }
    
	@Override
    public void initialize() {
		String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\classrooms.txt";
        File file = new File(relativePath);
		
        if (file.exists()) {
        	try (
        		BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
        		String line;
        		while ((line = bufferedRead.readLine()) != null) {
        			String[] data = line.split("-");
        			String className = data[0];
        			String classManagerID = data[1];
        			int gradeNumber = Integer.parseInt(data[2]);
        			String gradeManagerID = data[3];
        			
        			Grade grade = new Grade(gradeNumber);
        			Classroom classroom = new Classroom(className, grade);
        			//Teacher teacher = new Teacher(classManagerID, gradeManagerID);
        			
        			this.add(classroom);
        			
        		}
        	}
        	catch (IOException e) {
        		e.printStackTrace();
        	}
        	System.out.println("File exsisted!");
        } else {
        	System.out.println("File does not exist.");
        }
    }
    
    
    public void display() {
    	String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";
        File file = new File(relativePath);
        
        if (file.exists()) {
        	try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(relativePath, true))) {
        		bufferedWrite.write("Classroom Management List:");
        		bufferedWrite.newLine();
        		bufferedWrite.write(String.format("%-5s\t%-20s\t%-10s\t%-70s", "className", "classManagerID", "gradeNumber", "gradeManagerID"));
        		bufferedWrite.newLine();
        		for (int i = 0; i < currentIndex; i++) {
        			bufferedWrite.write(classroomManagement[i].toString());
                    bufferedWrite.newLine();
                }
        		bufferedWrite.write("================================================================");
        		bufferedWrite.newLine();
        		System.out.println("Data written to " + relativePath);
        	} 
        	catch (IOException e) {
        		e.printStackTrace();
        	}
        	System.out.println("\"File exists.\"");
        } else {
        	System.out.println("File does not exist.");
        }
    }

	@Override
	public void update(String ID) {
		Scanner sc = new Scanner(System.in);
		Classroom classroom = getClassNameByID(ID);
		if(classroom != null) {
			System.out.println("Old Classname: " + classroom.getClassName());
            System.out.print("New Classname: ");
			String newClassName = sc.nextLine();
            if (!newClassName.isEmpty()) {
                classroom.setClassName(newClassName);
            }


			System.out.println("Old classManagerID: " + classroom.getClassManagerID());
            System.out.print("New classManagerID: ");
			String newClassManagerID = sc.nextLine();
            if (!newClassManagerID.isEmpty()) {
                // classroom.setClassManagerID(newClassManagerID);
            }


			System.out.println("Old Grade: " + classroom.getGrade());
            System.out.print("New Grade: ");
			String newGrade = sc.nextLine();
            if (!newGrade.isEmpty()) {
                // classroom.setGrade(newGrade);
            }
		}
		else {
            System.out.println("Classroom with ID: " + ID + " is not found!");
		}
	}

	@Override
	public void delete(String ID) {
		for(int i=0; i < currentIndex; i++) {
			if(classroomManagement[i].getClassName().equalsIgnoreCase(ID)) {
				classroomManagement[i] = classroomManagement[i + 1];
				continue;
			}
		}
		currentIndex--;
		System.out.println("Classroom with ID: " + ID + " is not found!");
	}

	public Classroom getClassNameByID(String ID) {
        Classroom classroom = null;
        for (int i = 0; i < currentIndex; i++) {
            if (classroomManagement[i].getClassName().equals(ID)) {
                classroom = classroomManagement[i];
                break;
            }
        }
        return classroom;
    }
	
}
