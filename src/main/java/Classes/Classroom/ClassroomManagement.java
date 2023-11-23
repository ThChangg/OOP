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

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;



import Interfaces.ICRUD;
import Interfaces.IFileManagement;
import Main.Redux;


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
			// classroomFormationInitialize();
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
				System.out.println("Data written to " + relativePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File does not exist.");
		}
	}


	public void fileSearchList(int arrayLength) {
		String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";
		File file = new File(relativePath);

		if (file.exists()) {
			try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(relativePath, true))) {
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
				
				int gradeNumber = className.charAt(0) - '0';
				classroom.getGrade().setGradeNumber(gradeNumber);

				String newClassManagerID = "";
				do {
					System.out.println("Old Classmanager: " + classroom.getClassManager().getTeacherID());
					System.out.print("New Classmanager: (format: GV016): ");
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
						classroom.getClassManager().getTeacherID();
					}
				} while(!flag);
				

				String newGradeManagerID = "";
				do {
					System.out.println("Old Grademanager: " + classroom.getGrade().getGradeManager().getTeacherID());
					System.out.print("New Grademagager: (format: (GV004 or GV011)): ");
					newGradeManagerID = sc.nextLine();
					if (!newGradeManagerID.isEmpty()) {
						flag = isValidManager(newGradeManagerID);
						if(flag) {
							classroom.getGrade().getGradeManager().setTeacherID(newGradeManagerID);
						}
						else {
							System.out.println("Grade manager is invalid!");
						}
					}
					else {
						classroom.getGrade().getGradeManager().setTeacherID(newGradeManagerID);
					}
				} while (!flag);

				
				String fullName = "";
				do {
					System.out.println("Old Fullname: " + classroom.getClassManager().getFullname());
					System.out.print("New Fullname (Format: Pham Xuan Thu): ");
					fullName = sc.nextLine();
					if(!fullName.isEmpty()) {
						flag = Teacher.isValidName(fullName);
						if(flag) {
							classroom.getClassManager().setFullname(fullName);;
						}
						else {
							System.out.println("Fullname is invalid!");
						}
					}
					else {
						classroom.getClassManager().getFullname();
					}
				} while (!flag); 

				
				String date = "";
				do {
					System.out.println("Old BirthDate: " + classroom.getClassManager().getBirthDate());
					System.out.print("New BirthDate (format: 13/04/1982): ");
					date = sc.nextLine();
					if(!date.isEmpty()) {
						flag = Date.isValidDateAndMonth(date);
						if(flag) {
							Date newDate = new Date(date);
							classroom.getClassManager().setBirthDate(newDate);
						}
						else {
							System.out.println("Date is invalid!");
						}
					}
					else {
						classroom.getClassManager().getBirthDate();
					}
				} while(!flag);


				String address = "";
				do {
					System.out.println("Old Address: " + classroom.getClassManager().getAddress());
					System.out.print("New Address (format: 04, Phan Van Tri, Phuong 2, Quan 5, Thanh pho Ho Chi Minh): ");
					address = sc.nextLine();
					if(!address.isEmpty()) {
						flag = Address.isValidAddress(address);
						if(flag) {
							Address newAddress = new Address(address);
							classroom.getClassManager().setAddress(newAddress);
						}
						else {
							System.out.println("Address is invalid!");
						}
					}
					else {
						classroom.getClassManager().getAddress();
					}	
				} while(!flag);
				

				String sex = "";
				do {
					System.out.println("Old Sex: " + classroom.getClassManager().getSex());
					System.out.print("New Sex (format: male / female): ");
					sex = sc.nextLine();
					if(!sex.isEmpty()) {
						flag = Teacher.isValidSex(sex);
						if(flag) {
							classroom.getClassManager().setSex(sex);
						}
						else {
							System.out.println("Sex is invalid!");
						}
					}
					else {
						classroom.getClassManager().getSex();
					}	
				} while (!flag);
				

				String major = "";
				do {
					System.out.println("Old Major: " + classroom.getClassManager().getMajor());
					System.out.println("New Major (format: Math): ");
					major = sc.nextLine();
					if(!major.isEmpty()) {
						flag = TeacherManagement.isValidMajor(major);
						if(flag) {
							classroom.getClassManager().setMajor(major);
						}
						else {
							System.out.println("Major is invalid!");
						}
					}
					else {
						classroom.getClassManager().getMajor();
					}
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
					Redux.add(classroomManagement[i]);
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
		}
	}
	
	public String getLastClassManagerID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            if (classroomManagement[i].getStatus()) {
                ID = classroomManagement[i].getClassManager().getTeacherID();
            }
        }
        return ID;
    }

	public static boolean isValidClassroom(String className) {
        boolean flag = true;
        String classNameRegex = "([1-5]A([4-9]))";
        Pattern pattern = Pattern.compile(classNameRegex);
		Matcher matcher = pattern.matcher(className);

        if(!matcher.matches()) {
            flag = false;
        }
        return flag;
    }

	
	public static boolean isValidManager(String classManager) {
		boolean flag = true;
		String managerRegex = "^(GV)[0][0-9][0-9]$";
		Pattern pattern = Pattern.compile(managerRegex);
		Matcher matcher = pattern.matcher(classManager);

		if(!matcher.matches()) {
			flag = false;
		}
		return flag;
	}


    public static String readDatabase() {
        StringBuilder records = new StringBuilder();
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\classrooms.txt";
        File file = new File(relativePath);
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
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\classrooms.txt";
        File file = new File(relativePath);
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
        for (String record : records) {
            updatedContent.append(record).append("\n");
        }

        writeDatabase(updatedContent.toString());
    }

    public static void deleteRecord(String record) {
        // Read existing records from the database file
        String existingRecords = readDatabase();

        // Check if the record is present in the existing records
        if (existingRecords.contains(record)) {
            // Remove the record from the existing records
            String updatedRecords = existingRecords.replace(record, "").trim();

            // Update the database with the modified records
            writeDatabase(updatedRecords);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found in the database. Deletion failed.");
        }
    }
}
	

