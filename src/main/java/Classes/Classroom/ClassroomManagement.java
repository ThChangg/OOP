package Classes.Classroom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import Classes.Pupils.Pupil;
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
			classroomManagement[currentIndex++] = (Classroom) obj;
        } else {
            System.out.println("Classroom Management List is full. Cannot add more.");
        }
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
        try {
            FileWriter fWrite = new FileWriter("classrooms.txt");
            BufferedWriter bufferedWrite = new BufferedWriter(fWrite);
            
            for (int i = 0; i < currentIndex; i++) {
                bufferedWrite.write(classroomManagement[i].toString());
                bufferedWrite.newLine();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		
	}
}
