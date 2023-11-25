package Classes.Classroom;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class ClassroomManagement implements IFileManagement, ICRUD {
	private Classroom classroomManagement[];
	private int currentIndex;
	private static int classCounts[];
	private static String classroomFormation[];

	public ClassroomManagement() {
		this.classroomManagement = new Classroom[100];
		currentIndex = 0;
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

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	@Override
	public void initialize() {
		String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\classrooms.txt";

		File file = new File(relativePath);

		if (file.exists()) {
			try (BufferedReader bufferedRead = new BufferedReader(
					new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
				String line;
				while ((line = bufferedRead.readLine()) != null) {
					String[] data = line.split("-");
					String className = data[0];
					int gradeNumber = Integer.parseInt(data[2]);

					Grade grade = new Grade(gradeNumber);
					Classroom classroom = new Classroom(className, grade);
					// Teacher teacher = new Teacher( classManagerID, gradeManagerID);

					this.add(classroom);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			classroomFormationInitialize();
		} else {
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
				bufferedWrite.write(String.format("%-5s\t%-20s\t%-10s\t%-70s", "className", "classManagerID",
						"gradeNumber", "gradeManagerID"));
				bufferedWrite.newLine();
				for (int i = 0; i < currentIndex; i++) {
					bufferedWrite.write(classroomManagement[i].toString());
					bufferedWrite.newLine();
				}
				bufferedWrite.write("================================================================");
				bufferedWrite.newLine();
				System.out.println("Data written to " + relativePath);
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
	public void update(String ID) {
		Scanner sc = new Scanner(System.in);
		Classroom classroom = getClassNameByID(ID);

		if (classroom != null) {
			System.out.println("Old ClassName: " + classroom.getClassName());
			System.out.print("New ClassName: ");
			String newClassName = sc.nextLine();
			if (!newClassName.isEmpty()) {
				classroom.setClassName(newClassName);
			}

			System.out.println("Old ClassManagerID: " + classroom.getClassManagerID());
			System.out.print("New ClassManagerID: ");
			String newClassManagerID = sc.nextLine();
			if (!newClassManagerID.isEmpty()) {
				classroom.setClassManagerID(null);
			}

			System.out.println("Old Grade: " + classroom.getGrade());
			System.out.print("New Grade: ");
			String grade = sc.nextLine();
			if (!grade.isEmpty()) {
				String data[] = grade.split("-");
				int gradeNumber = Integer.parseInt(data[0]);
				String gradeManagerID = data[1];

				Grade newGrade = new Grade(gradeNumber, null);
				classroom.setGrade(newGrade);
			}

			System.out.println("Update successfully!");
		} else {
			System.out.println("Classroom with ID: " + ID + " is not found!");
		}

	}

	private Classroom getClassNameByID(String iD) {
		return null;
	}

	@Override
	public void delete(String ID) {
		// TODO Auto-generated method stub

	}

	public void classroomFormationInitialize() {
		for (int i = 0; i < currentIndex; i++) {
			classroomFormation[i] = classroomManagement[i].getClassName();
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

	public static boolean isValidClassroom(String className) {
		String regex = "([1-5])A(\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(className);
		boolean flag = true;

		if (matcher.matches()) {
			for (String classname : classroomFormation) {
				if (classname.equalsIgnoreCase(className)) {
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
		} else {
			flag = false;
		}

		return flag;
	}
}
