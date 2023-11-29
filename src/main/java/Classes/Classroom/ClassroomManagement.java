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
					if (data.length >= 4) {
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
				initializeClassroomFormation();
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
			try (BufferedWriter bufferedWrite = new BufferedWriter(
					new FileWriter(Redux.getOutputRelativePath(), true))) {
				bufferedWrite.write("Classroom Management List:");
				bufferedWrite.newLine();
				bufferedWrite.write(String.format("%-5s\t%-20s\t%-15s\t%-10s", "className", "classManager",
						"gradeNumber", "gradeManager"));
				bufferedWrite.newLine();
				for (int i = 0; i < currentIndex; i++) {
					if (classroomManagement[i].getStatus()) {
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
			try (BufferedWriter bufferedWrite = new BufferedWriter(
					new FileWriter(Redux.getOutputRelativePath(), true))) {
				bufferedWrite.write("Classroom Search List:");
				bufferedWrite.newLine();
				bufferedWrite.write(String.format("%-5s\t%-20s\t%-15s\t%-10s", "className", "classManager",
						"gradeNumber", "gradeManager"));
				bufferedWrite.newLine();
				for (int i = 0; i < arrayLength; i++) {
					if (searchList[i].getStatus()) {
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
		} else {
			System.out.println("Classroom Management List is full. Cannot add more.");
		}
		currentIndex++;
	}

	@Override
	public void update(Object obj) {
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getClassName().equalsIgnoreCase(((Classroom) obj).getClassName())) {
				classroomManagement[i] = (Classroom) obj;
				break;
			}
		}
	}

	@Override
	public void delete(String ID) {
		int index = this.getClassroomArrayIndex(ID);
		if (index >= 0) {
			for (int i = 0; i < currentIndex; i++) {
				if (i == index) {
					classroomManagement[i].setStatus(false);
					Redux.addToRecycleBin(classroomManagement[i]);
				}
			}
			System.out.println("Delete successfully!");
		} else {
			System.out.println("Classroom with ID: " + ID + " is not found!");
		}
	}

	public Classroom getClassroomByID(String ID) {
		Classroom classroom = null;
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getClassName().equalsIgnoreCase(ID)) {
				if (classroomManagement[i].getStatus()) {
					classroom = classroomManagement[i];
					break;
				} else {
					System.out.println("Classroom does not exist");
				}
			}
		}
		return classroom;
	}

	public Classroom getClassroomByClassManagerID(String ID) {
		Classroom classroom = null;
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getClassManager().getTeacherID().equalsIgnoreCase(ID)) {
				if (classroomManagement[i].getStatus()) {
					classroom = classroomManagement[i];
					break;
				} 
			}
		}
		return classroom;
	}

	public int getClassroomArrayIndex(String ID) {
		int index = -1;
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getClassName().equalsIgnoreCase(ID)) {
				if (classroomManagement[i].getStatus()) {
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
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getStatus()) {
				if (classroomManagement[i].getClassName().contains(className.toUpperCase())) {
					flag = true;
					searchList[searchListLength] = classroomManagement[i];
					searchListLength++;
				}
			}
		}
		if (!flag) {
			System.out.println("Class " + className + " does not exist!");
		}
	}

	public void initializeClassroomFormation() {
		classroomFormation = new String[currentIndex];
		for (int i = 0; i < currentIndex; i++) {
			classroomFormation[i] = classroomManagement[i].getClassName();
		}

		Arrays.sort(classroomFormation);
	}

	public static void displayClassroomFormation() {
		int grade = 0;
		int index = 0;

		for (int i = 0; i < 5; i++) {
			System.out.print("Grade " + (grade + 1) + ": ");

			for (int j = 0; j < classCounts[grade]; j++) {
				System.out.print(classroomFormation[index] + "\t");
				index++;
			}

			System.out.println();
			grade++;
		}
	}

	public static void removeElementFromClassroomFormation(String className) {
		int index = -1;
		for (int i = 0; i < classroomFormation.length; i++) {
			if (classroomFormation[i].equalsIgnoreCase(className)) {
				index = i;
				break;
			}
		}

		for (int i = index; i < classroomFormation.length - 1; i++) {
			classroomFormation[i] = classroomFormation[i + 1];
		}
		classroomFormation[classroomFormation.length - 1] = null;
		classroomFormation = Arrays.copyOf(classroomFormation, classroomFormation.length - 1);

		String classNumber = className.substring(0, 2);
		switch (classNumber) {
			case "1A":
				classCounts[0]--;
				break;

			case "2A":
				classCounts[1]--;
				break;

			case "3A":
				classCounts[2]--;
				break;

			case "4A":
				classCounts[3]--;
				break;

			case "5A":
				classCounts[4]--;
				break;
		}

	}

	public String getLastClassManagerID() {
		String ID = "";
		for (int i = 0; i < currentIndex; i++) {
			ID = classroomManagement[i].getClassManager().getTeacherID();
		}
		return ID;
	}

	public boolean isValidNewClassroom(String className) {
		boolean flag = false;
		String classNameRegex = "([1-5])A([4-9]|[1-9][0-9]+)";
		Pattern pattern = Pattern.compile(classNameRegex);
		Matcher matcher = pattern.matcher(className);

		if (matcher.matches()) {
			for (int i = 0; i < currentIndex; i++) {
				if (!classroomManagement[i].getClassName().equalsIgnoreCase(className)) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		}

		return flag;
	}

	public boolean isValidClassroom(String className) {
		boolean flag = false;
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getClassName().equalsIgnoreCase(className)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public boolean isValidManager(String classManager) {
		boolean flag = true;
		for (int i = 0; i < currentIndex; i++) {
			if (classroomManagement[i].getClassManager() != null) {
				if (classroomManagement[i].getClassManager().getStatus()) {
					if (classManager.equals(classroomManagement[i].getClassManager().getTeacherID())) {
						flag = false;
						break;
					}
				}
			}
		}

		return flag;
	}

	public static String getGradeManagerByGradeNumber(int gradeNumber) {
		String gradeManager = "";
		switch (gradeNumber) {
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

	public static void insertIntoDatabase(String record) {
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

	public static void updateRecord(String updatedRecord) {
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
			if (i < records.length - 1) {
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
