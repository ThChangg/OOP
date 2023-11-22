package Classes.Teachers;

import Classes.Classroom.Classroom;
import Classes.Person.Address;
import Classes.Person.Date;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherManagement implements IFileManagement, ICRUD {
    private Teacher teacherManagement[];
    private Teacher searchResult[];
    private int currentIndex;
    private int searchResultLength;

    public TeacherManagement() {
        teacherManagement = new Teacher[100];
        searchResult = new Teacher[100];
        currentIndex = 0;
        searchResultLength = 0;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Teacher[] getTeacherManagement() {
        return teacherManagement;
    }

    public void setTeacherManagement(Teacher[] teacherManagement) {
        this.teacherManagement = teacherManagement;
    }

    public Teacher[] getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(Teacher[] searchResult) {
        this.searchResult = searchResult;
    }

    public int getSearchResultLength() {
        return searchResultLength;
    }

    public void setSearchResultLength(int searchResultLength) {
        this.searchResultLength = searchResultLength;
    }

    @Override
    public void initialize() {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\teachers.txt";
        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split("-");
                    if (parts.length >= 5) {
                        String teacherID = parts[0];
                        String fullName = parts[1];
                        String dobString = parts[2];
                        String major = parts[4];
                        Classroom classroom = new Classroom();
                        classroom.setClassName(parts[5]);
                        String sex = parts[6];

                        String dobParts[] = dobString.split("/");
                        String date = dobParts[0];
                        String month = dobParts[1];
                        String year = dobParts[2];

                        Date dob = new Date(date, month, year);

                        String addressPart = parts[3];
                        String addressRegex = "(\\S.*),\\s(.*),\\s(Phuong\\s.*),\\s(Quan\\s.*),\\s(Thanh pho\\s.*$)";
                        Pattern pattern = Pattern.compile(addressRegex);
                        Matcher matcher = pattern.matcher(addressPart);
                        if (matcher.matches()) {
                            String houseNumber = matcher.group(1);
                            String streetName = "Duong " + matcher.group(2);
                            String ward = matcher.group(3);
                            String district = matcher.group(4);
                            String city = matcher.group(5);

                            Address address = new Address(houseNumber, streetName, ward, district, city);
                            Teacher teacher = new Teacher(teacherID, classroom, major, fullName, sex, dob, address);
                            this.add(teacher);
                        } else {
                            System.out.println("Your address is invalid!");
                        }
                    } else {
                        System.out.println("Record does not have enough information");
                    }
                }
            } catch (IOException e) {
                ((Throwable) e).printStackTrace();
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    @Override
    public void display() {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Teacher Management List:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-10s\t%-10s", "ID", "Fullname", "Sex",
                        "BirthDate", "Address", "Major", "Class"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    writer.write(teacherManagement[i].toString());
                    writer.newLine();
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + relativePath);
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

        public void display(int arrayLength) {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Search result:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-10s\t", "ID", "Fullname", "Sex",
                        "BirthDate", "Address", "Major", "Class"));
                writer.newLine();
                for (int i = 0; i < arrayLength; i++) {
                    writer.write(searchResult[i].toString());
                    writer.newLine();
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + relativePath);
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
    @Override
    public void add(Object obj) {
        if (currentIndex < teacherManagement.length) {
            teacherManagement[currentIndex++] = (Teacher) obj;
        } else {
            System.out.println("Teacher Management List is full. Cannot add more.");
        }
    }

    @Override
    public void update(String ID) {
        Scanner sc = new Scanner(System.in);
        Teacher teacher = getTeacherByID(ID);

        if (teacher != null) {
            System.out.println("Old Fullname: " + teacher.getFullname());
            System.out.print("New Fullname (Format: Tran Le Anh Khoi): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                teacher.setFullname(name);
            }

            System.out.println("Old BirthDate: " + teacher.getBirthDate());
            System.out.print("New BirthDate (Format: 25/08/2000): ");
            String birthDate = sc.nextLine();
            if (!birthDate.isEmpty()) {
                String dobParts[] = birthDate.split("/");
                String date = dobParts[0];
                String month = dobParts[1];
                String year = dobParts[2];

                Date newDob = new Date(date, month, year);
                teacher.setBirthDate(newDob);
            }

            System.out.println("Old Address: " + teacher.getAddress());
            System.out.print("New Address (Format: 16A, To Ky, Phuong Trung My Tay, Quan 12, Thanh pho Ho Chi Minh): ");
            String address = sc.nextLine();
            if (!address.isEmpty()) {
                String addressRegex = "(\\S.*),\\s(.*),\\s(Phuong\\s.*),\\s(Quan\\s.*),\\s(Thanh pho\\s.*$)";
                Pattern pattern = Pattern.compile(addressRegex);
                Matcher matcher = pattern.matcher(address);
                if (matcher.matches()) {
                    String streetNumber = matcher.group(1);
                    String streetName = "Duong " + matcher.group(2);
                    String ward = matcher.group(3);
                    String district = matcher.group(4);
                    String city = matcher.group(5);

                    Address newAddress = new Address(streetNumber, streetName, ward, district, city);
                    teacher.setAddress(newAddress);
                } else {
                    System.out.println("Your address is invalid!");
                }
            }
        } else {
            System.out.println("Teacher with ID: " + ID + " is not found!");
        }
    }

    @Override
    public void delete(String ID) {
        int index = this.getTeacherArrayIndex(ID);

        if (index >= 0) {
            for (int i = index; i < currentIndex - 1; i++) {
                this.teacherManagement[i] = this.teacherManagement[i + 1];
            }
            this.currentIndex--;
        } else {
            System.out.println("Teacher with ID: " + ID + " is not found!");
        }
    }

    public String getLastTeacherID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            ID = teacherManagement[i].getTeacherID();
        }
        return ID;
    }

    public Teacher getTeacherByID(String ID) {
        Teacher teacher = null;
        for (int i = 0; i < currentIndex; i++) {
            if (teacherManagement[i].getTeacherID().equalsIgnoreCase(ID)) {
                teacher = teacherManagement[i];
                break;
            }
        }
        return teacher;
    }

    public void findTeachersBy(String value, String findBy, Class<?> mainClass, Class<?> nestedClass) {
        Arrays.fill(searchResult, null);
        searchResultLength = 0;
        Pattern pattern = Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < currentIndex; i++) {
            try {
                if (nestedClass != null) {
                    // Get the nested object from the main object
                    Object nestedObject = mainClass.getMethod("get" + nestedClass.getSimpleName()).invoke(teacherManagement[i]);

                    // Use reflection to get the appropriate method from the nested class
                    Method getterMethod = nestedClass.getMethod(findBy);

                    // Invoke the method on the nested object
                    String attributeValue = (String) getterMethod.invoke(nestedObject);

                    if (pattern.matcher(attributeValue).find()) {
                        if (teacherManagement[i].getStatus()) {
                            this.searchResult[this.searchResultLength++] = teacherManagement[i];
                        } else {
                            System.out.println("Teacher does not exist!");
                        }
                    }
                } else {
                    // No nested class, directly invoke the method on the main class
                    Method getterMethod = mainClass.getMethod(findBy);
                    String attributeValue = (String) getterMethod.invoke(teacherManagement[i]);

                    if (pattern.matcher(attributeValue).find()) {
                        if (teacherManagement[i].getStatus()) {
                            this.searchResult[this.searchResultLength++] = teacherManagement[i];
                        } else {
                            System.out.println("Teacher does not exist!");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getTeacherArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (teacherManagement[i].getTeacherID().equalsIgnoreCase(ID)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public static boolean isValidMajor(String major) {
        boolean flag = false;
        String validMajor[] = {"Math", "Literature", "English", "PE"};

        if(major == null || major.trim().isEmpty()) {
            flag = false;
        }
        
        for(String inputMajor : validMajor)  {
            if(major.equalsIgnoreCase(inputMajor)) {
                flag = true;
            }
        }
        return flag;
    }
    
    
    public void insertTeacherIntoDatabase(String record) {
        // Read existing records from the database file
        String existingRecords = readTeacherDatabase();

        // Check if the new record is not present in the existing records
        if (!existingRecords.contains(record)) {
            // Append the new record to the existing records
            writeTeacherDatabase(existingRecords + "\n" + record);
        } else {
            System.out.println("Record already exists in the database. Not added.");
        }
    }

    public void updateTeacherRecord(String updatedTeacherRecord) {
        String databaseContent = readTeacherDatabase();
        String records[] = databaseContent.split("\n");
        String teacherID = updatedTeacherRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(teacherID)) {
                records[i] = updatedTeacherRecord;
                break;
            }
        }

        StringBuilder updatedContent = new StringBuilder();
        for (String record : records) {
            updatedContent.append(record).append("\n");
        }

        writeTeacherDatabase(updatedContent.toString());
    }

    public static void deleteTeacherRecord(String record) {
        // Read existing records from the database file
        String existingRecords = readTeacherDatabase();

        // Check if the record is present in the existing records
        if (existingRecords.contains(record)) {
            // Remove the record from the existing records
            String updatedRecords = existingRecords.replace(record, "").trim();

            // Update the database with the modified records
            writeTeacherDatabase(updatedRecords);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found in the database. Deletion failed.");
        }
    }

    public static String readTeacherDatabase() {
        StringBuilder records = new StringBuilder();
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\teachers.txt";
        File file = new File(relativePath);
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                records.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
        return records.toString().trim();
    }

    public static void writeTeacherDatabase(String records) {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\teachers.txt";
        File file = new File(relativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }
}
