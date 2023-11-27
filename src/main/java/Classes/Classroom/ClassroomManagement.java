package Classes.Classroom;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;
import Classes.Redux.Redux;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class ClassroomManagement implements IFileManagement, ICRUD {
	private Classroom classroomManagement[];
	private Classroom searchList[];
	private int currentIndex;
	private int searchListLength;
	private static int classCounts[];
	private static String classroomFormation[];
	private static String inputRelativePath = System.getProperty("user.dir")
			+ "\\src\\main\\java\\Data\\classrooms.txt";

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
		File file = new File(inputRelativePath);

		if (file.exists()) {
			try (BufferedReader bufferedRead = new BufferedReader(
					new InputStreamReader(new FileInputStream(inputRelativePath), "UTF-8"))) {
				String line;
				while ((line = bufferedRead.readLine()) != null) {
					String[] data = line.split("-");
					if(data.length >= 4) {
						String className = data[0];
						String classManagerID = data[1];
						int gradeNumber = Integer.parseInt(data[2]);
						String gradeManagerID = data[3];
					

						Teacher classTeacher = new Teacher(classManagerID);
						Teacher gradeTeacher = new Teacher(gradeManagerID);
						Grade grade = new Grade(gradeNumber, gradeTeacher);
						Classroom classroom = new Classroom(className, classTeacher, grade);
					

						this.add(classroom);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File does not exist.");
		}
	}

	@Override
	public void display() {
		File file = new File(Redux.getOutputRelativePath());

		if (file.exists()) {
			try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
				bufferedWrite.write("Classroom Management List:");
				bufferedWrite.newLine();
				bufferedWrite.write(String.format("%-5s\t%-20s\t%-15s\t%-10s", "className", "classManager", "gradeNumber", "gradeManager"));
				bufferedWrite.newLine();
				for (int i = 0; i < currentIndex; i++) {
					if(classroomManagement[i].getStatus()) {
						bufferedWrite.write(classroomManagement[i].toString());
						bufferedWrite.newLine();
					}
				}
				bufferedWrite.write("================================================================");
				bufferedWrite.newLine();
				System.out.println("Data written to " + Redux.getOutputRelativePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File does not exist.");
		}
	}


	public void fileSearchList(int arrayLength) {
		File file = new File(Redux.getOutputRelativePath());

		if (file.exists()) {
			try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
				bufferedWrite.write("Classroom Search List:");
				bufferedWrite.newLine();
				bufferedWrite.write(String.format("%-5s\t%-20s\t%-15s\t%-10s", "className", "classManager", "gradeNumber", "gradeManager"));
				bufferedWrite.newLine();
				for (int i = 0; i < arrayLength; i++) {
					if(searchList[i].getStatus()) {
						bufferedWrite.write(searchList[i].toString());
						bufferedWrite.newLine();
					}
				}
				bufferedWrite.write("================================================================");
				bufferedWrite.newLine();
				System.out.println("Data written to " + Redux.getOutputRelativePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File does not exist.");
		}
	}
	

	@Override
	public void add(Object obj) {
		if (currentIndex < classroomManagement.length) {
			classroomManagement[currentIndex] = (Classroom) obj;
			String classNumber = ((Classroom) obj).getClassName().substring(0, 2);
			switch (classNumber) {
				case "1A":
					classCounts[0]++;
					break;

				case "2A":
					classCounts[1]++;
					break;

				case "3A":
					classCounts[2]++;
					break;

				case "4A":
					classCounts[3]++;
					break;

				case "5A":
					classCounts[4]++;
					break;
			}
        }
		else {
            System.out.println("Classroom Management List is full. Cannot add more.");
        }
		currentIndex++; 
    }
	

	@Override
	public void update(String ID) {
		Scanner sc = new Scanner(System.in);
		Classroom classroom = getClassNameByID(ID);

		if(classroom != null) {	
			boolean flag = true;
			do {
				String className = classroom.getClassName();
				
				String newClassManagerID = "";
				do {
					System.out.println("Old ClassManager: " + classroom.getClassManager().getTeacherID());
					System.out.print("New ClassManager: (format: GV016): ");
					newClassManagerID = sc.nextLine();
					if (!newClassManagerID.isEmpty()) {
						flag = isValidManager(newClassManagerID);
						if(flag) {
							classroom.getClassManager().setTeacherID(newClassManagerID);
						}
						else {
							System.out.println("Class manager is invalid!");
						}
					}
					else {
						flag = true;
						newClassManagerID = classroom.getClassManager().getTeacherID();
					}
				} while(!flag);
				
				int gradeNumber = className.charAt(0) - '0';
				classroom.getGrade().setGradeNumber(gradeNumber);

				String newGradeManagerID = "";
				do {
					System.out.println("Old GradeManager: " + classroom.getGrade().getGradeManager().getTeacherID());
					newGradeManagerID = getGradeManagerByGradeNumber(gradeNumber);
				} while (!flag);
				
			String write = className + "-" + newClassManagerID + "-" + gradeNumber + "-" + newGradeManagerID;
			this.updateRecord(write);
			System.out.println("Update successfully!");
			} while (!flag);			
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
					Redux.addToRecycleBin(classroomManagement[i]);
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
		searchListLength = 0;
		boolean flag = false;
		for(int i = 0; i < currentIndex; i++) {
			if(classroomManagement[i].getClassName().contains(className.toUpperCase())) {
				flag = true;
				searchList[searchListLength] = classroomManagement[i];
				searchListLength++;	
			}	
		}
		if(!flag) {
			System.out.println("Class " + className + " does not exist!");		
		}	
	}

	public static void displayClassroomFormation() {
		for (int i = 1; i <= 5; i++) {
			System.out.print("Grade " + i + ": ");
			for (int j = 1; j <= classCounts[i - 1]; j++) {
				System.out.print(i + "A" + j + "\t");
			}
			System.out.println();
		}
	}
	
	public String getLastClassManagerID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            ID = classroomManagement[i].getClassManager().getTeacherID();
        }
        return ID;
    }

	public static boolean isValidClassroom(String className) {
		if(className.equalsIgnoreCase("null")){
			return true;
		}
        boolean flag = true;
        String classNameRegex = "([1-5])A([4-9]|[1-9][0-9]+)";
        Pattern pattern = Pattern.compile(classNameRegex);
		Matcher matcher = pattern.matcher(className);

        if(!matcher.matches()) {
            flag = false;
        }

        return flag;
    }

	
	public boolean isValidManager(String classManager) {
		boolean flag = true;
		String managerRegex = "^(GV)[0][0-9][0-9]$";
		Pattern pattern = Pattern.compile(managerRegex);
		Matcher matcher = pattern.matcher(classManager);
		
		if(!matcher.matches()) {
			flag = false;
		} 
		else {
			for (int i = 0; i < currentIndex; i++) {
				if (classManager.equals(classroomManagement[i].getClassManager().getTeacherID())) {
					System.out.println("Teacher ID already exists. Please enter a different one.");
					flag = false;
					break;
				}
			}
		}
		
		return flag;
	}
	
	public static String getGradeManagerByGradeNumber(int gradeNumber) {
        String gradeManager = "";
        switch(gradeNumber) {
            case 1:
                gradeManager = "GV003";
                break;
            case 2:
                gradeManager = "GV004";
                break;
            case 3:
                gradeManager = "GV008";
                break;
            case 4:
                gradeManager = "GV011";
                break;
            case 5:
                gradeManager = "GV014";
                break;

            default:
                break;
        }

        return gradeManager;
    }

    public static String readDatabase() {
        StringBuilder records = new StringBuilder();
        File file = new File(inputRelativePath);
        try (Scanner sc = new Scanner(new FileReader(file))) {
            while (sc.hasNextLine()) {
                records.append(sc.nextLine()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records.toString().trim();
    }

    public static void writeDatabase(String records) {
        File file = new File(inputRelativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public void insertIntoDatabase(String record) {
        // Read existing records from the database file
        String existingRecords = readDatabase();

        // Check if the new record is not present in the existing records
        if (!existingRecords.contains(record)) {
            // Append the new record to the existing records
            writeDatabase(existingRecords + "\n" + record);
        } else {
            System.out.println("Record already exists in the database. Not added.");
        }
    }

    public void updateRecord(String updatedRecord) {
        String databaseContent = readDatabase();
        String records[] = databaseContent.split("\n");
        String className = updatedRecord.substring(0, 3);
        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(className)) {
                records[i] = updatedRecord;
                break;
            }
        }

        StringBuilder updatedContent = new StringBuilder();
        for (int i = 0; i < records.length; i++) {
            updatedContent.append(records[i]);
			if(i < records.length - 1) {
				updatedContent.append("\n");
			}
        }

        writeDatabase(updatedContent.toString());
    }

    public static void deleteRecord(String record) {
        // Read existing records from the database file
        String existingRecords = readDatabase();

        // Check if the record is present in the existing records
        if (existingRecords.contains(record)) {
            // Remove the record from the existing records
            String updatedRecords = existingRecords.replaceAll(record + "(\\n|$)", "").trim();

            // Update the database with the modified records
            writeDatabase(updatedRecords);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found in the database. Deletion failed.");
        }
    }
}
	

