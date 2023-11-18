package Classes.Classroom;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;


import Classes.Teachers.Teacher;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class ClassroomManagement implements IFileManagement, ICRUD {
	private Classroom classroomManagement[];
	private Classroom searchList[];
	private int currentIndex;
	private int searchListLength;
	private static int classCounts[];
	private static String classroomFormation[];

	public ClassroomManagement() {
		classroomManagement = new Classroom[100];
		searchList = new Classroom[100];
		currentIndex = 0;
		searchListLength = 0;
		classCounts = new int[5];
		classroomFormation = new String[100];
		Arrays.fill(classroomFormation, "");
	}
    
	public Classroom[] getClassroomManagement() {
		return classroomManagement;
	}

	public void setClassroomManagement(Classroom classroomManagement[]) {
		this.classroomManagement = classroomManagement;
	}

	public Classroom[] getSearchList() {
		return searchList;
	}

	public void setSearchList(Classroom[] searchList) {
		this.searchList = searchList;
	}

    public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getSearchListLength() {
		return searchListLength;
	}

	public void setSearchListLength(int searchListLength) {
		this.searchListLength = searchListLength;
	}

	

	
    
	@Override
    public void initialize() {
		String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\classrooms.txt";

        File file = new File(relativePath);
		
        if (file.exists()) {
        	try (BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
        		String line;
        		while ((line = bufferedRead.readLine()) != null) {
        			String[] data = line.split("-");
					String className = data[0];
					String classManagerID = data[1];
					int gradeNumber = Integer.parseInt(data[2]);
					String gradeManagerID = data[3];
					

					Teacher classTeacher = new Teacher(classManagerID);
					Teacher gradeTeacher = new Teacher(gradeManagerID);
					Grade grade = new Grade(gradeNumber, gradeTeacher) ;
					Classroom classroom = new Classroom(className, classTeacher, grade);
					

					this.add(classroom);
        		}
        	}
        	catch (IOException e) {
        		e.printStackTrace();
        	}
        	System.out.println("File exsisted!");
        } 
		else {
        	System.out.println("File does not exist.");
        }
    }
    
    @Override
    public void display() {
    	String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";
        File file = new File(relativePath);
        
        if (file.exists()) {
        	try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(relativePath, true))) {
        		bufferedWrite.write("Classroom Management List:");
        		bufferedWrite.newLine();
        		bufferedWrite.write(String.format("%-5s\t%-10s\t%-1s\t%16s", "className", "classManager", "gradeNumber", "gradeManager"));
        		bufferedWrite.newLine();
        		for (int i = 0; i < currentIndex; i++) {
					if(classroomManagement[i].getStatus()) {
						bufferedWrite.write(classroomManagement[i].toString());
						bufferedWrite.newLine();
					}
                }
        		bufferedWrite.write("================================================================");
        		bufferedWrite.newLine();
        		System.out.println("Data written to " + relativePath);
        	} 
        	catch (IOException e) {
        		e.printStackTrace();
        	}
        	System.out.println("\"File exists.\"");
        } 
		else {
        	System.out.println("File does not exist.");
        }
    }

	
	public void fileSearchList(int arrayLength) {
		String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";
        File file = new File(relativePath);
        
        if (file.exists()) {
        	try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(relativePath, true))) {
        		bufferedWrite.write("Search List:");
        		bufferedWrite.newLine();
        		bufferedWrite.write(String.format("%-5s\t%-10s\t%-1s\t%16s", "className", "classManagerID", "gradeNumber", "gradeManagerID"));
        		bufferedWrite.newLine();
        		for (int i = 0; i < arrayLength; i++) {
					if(searchList[i].getStatus()) {
						bufferedWrite.write(searchList[i].toString());
						bufferedWrite.newLine();
					}
                }
        		bufferedWrite.write("================================================================");
        		bufferedWrite.newLine();
        		System.out.println("Data written to " + relativePath);
        	} 
        	catch (IOException e) {
        		e.printStackTrace();
        	}
        	System.out.println("\"File exists.\"");
        } 
		else {
        	System.out.println("File does not exist.");
        }
	}


	@Override
	public void add(Object obj) {
		if (currentIndex < classroomManagement.length) {
			classroomManagement[currentIndex] = (Classroom) obj;
        }
		else {
            System.out.println("Classroom Management List is full. Cannot add more.");
        }
		currentIndex++;
		System.out.println("Add classrooms successfully!"); 
    }

	@Override
	public void update(String ID) {
		Scanner sc = new Scanner(System.in);
		Classroom classroom = getClassNameByID(ID);

		if(classroom != null) {	
			boolean flag;	
			String newClassName = "";
			do {
				System.out.println("Old Classname: " + classroom.getClassName());
				System.out.print("New Classname: ");
				newClassName = sc.nextLine();
				if (!newClassName.isEmpty()) {
					classroom.setClassName(newClassName);
				}
				else {
					System.out.println("New Classname is empty!");
				}
				flag = validateClassname(newClassName);
				if(flag) {
					System.out.println("Classname is valid!");
				}
				else {
					System.out.println("Classname is invalid!");
				}
			} while (!flag);
			

			// System.out.println("Old Classmanager: " + classroom.getClassManager());
			// System.out.print("New Classmanager: ");
			// String newClassManagerID = sc.nextLine();
			// if (!newClassManagerID.isEmpty()) {
			// 	//Teacher classManager = new Teacher(newClassManagerID);
			// 	classroom.setClassManager(null);
			// }

			// System.out.println("Old Grade: " + classroom.getGrade());
			// System.out.print("New Grade: ");
			// String grade = sc.nextLine();
			// if (!grade.isEmpty()) {
			// 	String data[] = grade.split("-");
			// 	int gradeNumber = Integer.parseInt(data[0]);
			// 	String gradeManagerID = data[1]; 

			// 	//Teacher gradeteacher = new Teacher(gradeManagerID);
			// 	Grade newGrade = new Grade(gradeNumber, null);
			// 	classroom.setGrade(newGrade);
			// }
					
			System.out.println("Update successfully!");		
		}
		else {
			System.out.println("Classroom with ID: " + ID + " is not found!");
		}

	}

	@Override
	public void delete(String ID) {
		int index = this.getClassroomArrayIndex(ID); 
        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
				if(i == index) {
            		classroomManagement[i].setStatus(false);
				}
            }
			System.out.println("Delete successfully!"); 
        } 
		else {
            System.out.println("Classroom with ID: " + ID + " is not found!");
        }
	}

	public Classroom getClassNameByID(String ID) {
        Classroom classroom = null;
        for (int i = 0; i < currentIndex; i++) {
            if (classroomManagement[i].getClassName().equalsIgnoreCase(ID)) {
				if(classroomManagement[i].getStatus()) {	
					classroom = classroomManagement[i];
                	break;
				}
				else {
					System.out.println("Classroom does not exist");
				}
            }
        }
        return classroom;
    }

	public int getClassroomArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (classroomManagement[i].getClassName().equalsIgnoreCase(ID)) {
				if(classroomManagement[i].getStatus()) {
					index = i;
					break;
				}
            }
        }
        return index;
    }
	
	public void searchClassName(String className) {
		boolean flag = false;
		for(int i = 0; i < currentIndex; i++) {
			if(classroomManagement[i].getClassName().equalsIgnoreCase(className)) {
				flag = true;
				searchList[searchListLength] = classroomManagement[i];
				searchListLength++;
				break;	
			}	
		}
		if(!flag) {
			System.out.println("Class " + className + " does not exist!");		
		}	
	}

	
	// public void classroomFormationInitialize() {
	// 	for (int i = 0; i < currentIndex; i++) {
	// 		classroomFormation[i] = classroomManagement[i].getClassName();
	// 	}
	// }
	public String getLastClassManagerID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            if (classroomManagement[i].getStatus()) {
                ID = classroomManagement[i].getClassManager().getTeacherID();
            }
        }
        return ID;
    }

	public static boolean validateClassname(String className) {
        boolean flag = true;
        String classNameRegex = "([1-5]A([1-9]))";
        Pattern pattern = Pattern.compile(classNameRegex);
		Matcher matcher = pattern.matcher(className);

        if(!matcher.matches()) {
            flag = false;
        }
        return flag;
    }

	public static boolean validateGradeManager(String classManager) {
		boolean flag = true;
		String prefix = classManager.substring(0, 2);
		int number = Integer.parseInt(classManager.substring(2));
		number++;

		String result = String.format("%s%03d",prefix, number);
		
		return flag;
	}

	

}
	

