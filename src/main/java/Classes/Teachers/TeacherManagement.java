package Classes.Teachers;

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Redux.Redux;
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
    private static String inputRelativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\teachers.txt";
    private int searchResultLength;

    public TeacherManagement() {
        teacherManagement = new Teacher[100];
        searchResult = new Teacher[100];
        currentIndex = 0;
        searchResultLength = 0;
    }

    public Teacher[] getTeacherManagement() {
        return teacherManagement;
    }

    public void setTeacherManagement(Teacher[] teacherManagement) {
        this.teacherManagement = teacherManagement;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getSearchResultLength() {
        return searchResultLength;
    }

    @Override
    public void initialize() {
        File file = new File(inputRelativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputRelativePath), "UTF-8"))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split("-");
                    if (parts.length >= 5) {
                        String teacherID = parts[0];
                        String fullName = parts[1];
                        String dobString = parts[2];
                        String major = parts[4];
                        String gender = parts[5];

                        String dobParts[] = dobString.split("/");
                        String date = dobParts[0];
                        String month = dobParts[1];
                        String year = dobParts[2];

                        Date dob = new Date(date, month, year);

                        String addressPart = parts[3];
                        Pattern pattern = Pattern.compile(Address.getAddressRegex());
                        Matcher matcher = pattern.matcher(addressPart);
                        if (matcher.matches()) {
                            String houseNumber = matcher.group(1);
                            String streetName = "Duong " + matcher.group(2);
                            String ward = matcher.group(3);
                            String district = matcher.group(4);
                            String city = matcher.group(5);

                            Address address = new Address(houseNumber, streetName, ward, district, city);
                            Teacher teacher = new Teacher(teacherID, fullName, dob, address, gender, major);
                            this.add(teacher);
                        } else {
                            System.out.println("Your address is invalid2!");
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
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                writer.write("Teacher Management List:");
                writer.newLine();
                writer.write(String.format(Redux.personInfoFormat + "\t%-10s", "ID", "Fullname", "Gender",
                        "BirthDate", "Address", "Major"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    if (teacherManagement[i].getStatus()) {
                        writer.write(teacherManagement[i].toString());
                        writer.newLine();
                    }
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + Redux.getOutputRelativePath());
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void display(int arrayLength) {
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                writer.write("Search result:");
                writer.newLine();
                writer.write(String.format(Redux.personInfoFormat + "\t%-10s", "ID", "Fullname", "Gender",
                        "BirthDate", "Address", "Major"));
                writer.newLine();
                for (int i = 0; i < arrayLength; i++) {
                    writer.write(searchResult[i].toString());
                    writer.newLine();
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + Redux.getOutputRelativePath());
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
            boolean flag = true;
            do {
                String name = "";
                do {
                    System.out.println("Old Fullname: " + teacher.getFullname());
                    System.out.print("New Fullname (Format: Tran Le Anh Khoi): ");
                    name = sc.nextLine();
                    if (!name.isEmpty()) {
                        flag = Teacher.isValidName(name);
                        if (flag) {
                            teacher.setFullname(name);
                        } else {
                            System.out.println("Fullname is invalid (Wrong format)!");
                        }
                    } else {
                        name = teacher.getFullname();
                    }

                } while (!flag);

                String birthDate = "";
                do {
                    System.out.println("Old BirthDate: " + teacher.getBirthDate());
                    System.out.print("New BirthDate (Format: 16/02/2000): ");
                    birthDate = sc.nextLine();

                    if (!birthDate.isEmpty()) {
                        flag = Date.isValidDateAndMonth(birthDate);
                        if (flag) {
                            Date newDob = new Date(birthDate);
                            teacher.setBirthDate(newDob);
                        } else {
                            System.out.println("BirthDate is invalid (Wrong format)!");
                        }

                    } else {
                        birthDate = teacher.getBirthDate().toString();
                    }
                } while (!flag);

                String gender = "";
                do {
                    System.out.println("Old Gender: " + teacher.getGender());
                    System.out.print("New Gender (Format: male / female): ");
                    gender = sc.nextLine();

                    if (!gender.isEmpty()) {
                        flag = Teacher.isValidGender(gender);
                        if (flag) {
                            teacher.setGender(gender);
                        } else {
                            System.out.println("Gender is invalid (Wrong format)!");
                        }
                    } else {
                        gender = teacher.getGender();
                    }
                } while (!flag);

                String address = "";
                do {
                    System.out.println("Old Address: " + teacher.getAddress());
                    System.out.print(
                            "New Address (Format: 18/29, Nguyen Van Hoan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                    address = sc.nextLine();
                    if (!address.isEmpty()) {
                        flag = Address.isValidAddress(address);
                        if (flag) {
                            Address newAddress = new Address(address);
                            teacher.setAddress(newAddress);
                        } else {
                            System.out.println("Address is invalid (Wrong format)!");
                        }
                    } else {
                        address = teacher.getAddress().toString();
                    }
                } while (!flag);

                String major = "";
                do {
                    System.out.println("Old Major: " + teacher.getMajor());
                    System.out.print("New Major (Format: Math, Literature, English, PE): ");
                    major = sc.nextLine();

                    if (!major.isEmpty()) {
                        flag = isValidMajor(major);
                        if (flag) {
                            teacher.setMajor(major);
                        } else {
                            System.out.println("Major is invalid (Wrong format)!");
                        }
                    } else {
                        major = teacher.getMajor();
                    }
                } while (!flag);

                String record = teacher.getTeacherID() + "-" + name + "-" + birthDate + "-" + teacher.getAddress() + "-"
                        + major + "-" + gender;
                this.updateRecord(record);
                System.out.println("Update successfully!");
            } while (!flag);
        } else {
            System.out.println("Teacher with ID: " + ID + " is not found!");
        }
    }

    @Override
    public void delete(String ID) {
        int index = this.getTeacherArrayIndex(ID);
        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    teacherManagement[i].setStatus(false);
                    Redux.addToRecycleBin(teacherManagement[i]);
                }
            }
            System.out.println("Delete successfully!");
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

    public void findTeachersBy(String value, String findBy, Class<?> mainClass, Class<?> nestedClass) {
        Arrays.fill(searchResult, null);
        searchResultLength = 0;
        Pattern pattern = Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < currentIndex; i++) {
            try {
                if (nestedClass != null) {
                    // Get the nested object from the main object
                    Object nestedObject = mainClass.getMethod("get" + nestedClass.getSimpleName())
                            .invoke(teacherManagement[i]);

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

    public static boolean isValidMajor(String major) {
        boolean flag = false;
        String validMajor[] = { "Math", "Literature", "English", "PE" };

        if (major == null || major.trim().isEmpty()) {
            flag = false;
        }

        for (String inputMajor : validMajor) {
            if (major.equalsIgnoreCase(inputMajor)) {
                flag = true;
            }
        }
        return flag;
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
        String teacherID = updatedRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(teacherID)) {
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

    public static String readDatabase() {
        StringBuilder records = new StringBuilder();
        File file = new File(inputRelativePath);
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

    public static void writeDatabase(String records) {
        File file = new File(inputRelativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

    public boolean hasUninitializedClassroom() {
        boolean flag = false;
        for (int i = 0; i < currentIndex; i++) {
            if (teacherManagement[i].getClassroom() == null) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
