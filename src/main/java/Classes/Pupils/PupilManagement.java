package Classes.Pupils;

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
import Classes.Classroom.ClassroomManagement;
import Classes.Person.Address;
import Classes.Person.Date;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;
import Main.Redux;

public class PupilManagement implements IFileManagement, ICRUD {
    private Pupil pupilList[];
    private Pupil searchResult[];
    private int currentIndex;
    private int searchResultLength;

    public PupilManagement() {
        pupilList = new Pupil[100];
        searchResult = new Pupil[100];
        currentIndex = 0;
        searchResultLength = 0;
    }

    public Pupil[] getPupilList() {
        return this.pupilList;
    }

    public void setPupilList(Pupil pupilList[]) {
        this.pupilList = pupilList;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Pupil[] getSearchResult() {
        return this.searchResult;
    }

    public void setSearchResult(Pupil searchResult[]) {
        this.searchResult = searchResult;
    }

    public int getSearchResultLength() {
        return this.searchResultLength;
    }

    public void setSearchResultLength(int searchResultLength) {
        this.searchResultLength = searchResultLength;
    }

    @Override
    public void initialize() {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\pupils.txt";
        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String parts[] = line.split("-");
                    if (parts.length >= 5) {
                        String pupilID = parts[0];
                        String fullName = parts[1];
                        String dobString = parts[2];
                        String sex = parts[5];

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
                            Pupil pupil = new Pupil(pupilID, fullName, dob, address, sex);
                            this.add(pupil);
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
                writer.write("Pupil Management List:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-3s", "ID", "Fullname", "Sex",
                        "BirthDate", "Address",
                        "Class"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    if (pupilList[i].getStatus()) {
                        writer.write(pupilList[i].toString());
                        writer.newLine();
                    }
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

    // Display method just using for searching
    public void display(int arrayLength) {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Search result:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-3s", "ID", "Fullname", "Sex",
                        "BirthDate", "Address",
                        "Class"));
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
        if (currentIndex < pupilList.length) {
            pupilList[currentIndex++] = (Pupil) obj;

        } else {
            System.out.println("Pupil Management List is full. Cannot add more.");
        }
    }

    @Override
    public void update(String ID) {
        Scanner sc = new Scanner(System.in);
        Pupil pupil = getPupilByID(ID);

        if (pupil != null) {
            boolean flag = true;
            do {
                String name = "";
                do {
                    System.out.println("Old Fullname: " + pupil.getFullname());
                    System.out.print("New Fullname (Format: Tran Duc Canh): ");
                    name = sc.nextLine();
                    if (!name.isEmpty()) {
                        flag = Pupil.isValidName(name);
                        if (flag) {
                            pupil.setFullname(name);
                        } else {
                            System.out.println("Fullname is invalid (Wrong format)!");
                        }
                    } else {
                        name = pupil.getFullname();
                    }

                } while (!flag);

                String birthDate = "";
                do {
                    System.out.println("Old BirthDate: " + pupil.getBirthDate());
                    System.out.print("New BirthDate (Format: 02/02/2017): ");
                    birthDate = sc.nextLine();

                    if (!birthDate.isEmpty()) {
                        flag = Date.isValidDateAndMonth(birthDate);
                        if (flag) {
                            Date newDob = new Date(birthDate);
                            pupil.setBirthDate(newDob);
                        } else {
                            System.out.println("BirthDate is invalid (Wrong format)!");
                        }

                    } else {
                        birthDate = pupil.getBirthDate().toString();
                    }
                } while (!flag);

                String sex = "";
                do {
                    System.out.println("Old Sex: " + pupil.getSex());
                    System.out.print("New Sex (Format: male / female): ");
                    sex = sc.nextLine();

                    if (!sex.isEmpty()) {
                        flag = Pupil.isValidSex(sex);
                        if (flag) {
                            pupil.setSex(sex);
                        } else {
                            System.out.println("Sex is invalid (Wrong format)!");
                        }
                    } else {
                        sex = pupil.getSex();
                    }
                } while (!flag);

                String address = "";
                do {
                    System.out.println("Old Address: " + pupil.getAddress());
                    System.out.print("New Address (Format: 66, Phan Van Tri, Phuong 9, Quan 3, Thanh pho Thu Duc): ");
                    address = sc.nextLine();
                    if (!address.isEmpty()) {
                        flag = Address.isValidAddress(address);
                        if (flag) {
                            Address newAddress = new Address(address);
                            pupil.setAddress(newAddress);
                        } else {
                            System.out.println("Address is invalid (Wrong format)!");
                        }
                    } else {
                        address = pupil.getAddress().toString();
                    }
                } while (!flag);

                ClassroomManagement.displayClassroomFormation();
                String className = "";
                do {
                    System.out.println("Old Class: " + pupil.getClassroom().getClassName());
                    System.out.print("New Class (Format: 1A1): ");
                    className = sc.nextLine();
                    if (!className.isEmpty()) {
                        flag = ClassroomManagement.isValidClassroom(className);
                        if (flag) {
                            int gradeNumber = className.charAt(0) - '0';
                            pupil.getClassroom().getGrade().setGradeNumber(gradeNumber);
                            pupil.getClassroom().setClassName(className);
                        } else {
                            System.out.println("Classroom is invalid (Wrong format)!");
                        }
                    } else {
                        className = pupil.getClassroom().getClassName();
                    }
                } while (!flag);
                String record = pupil.getPupilID() + "-" + name + "-" + birthDate + "-" + pupil.getAddress() + "-"
                        + className + "-" + sex;
                this.updateRecord(record);
                System.out.println("Update successfully!");
            } while (!flag);
        } else {
            System.out.println("Pupil with ID: " + ID + " is not found!");
        }
    }

    @Override
    public void delete(String ID) {
        int index = this.getPupilArrayIndex(ID);

        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    pupilList[i].setStatus(false);
                    Redux.add(pupilList[i]);
                }
            }
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Pupil with ID: " + ID + " is not found!");
        }
    }

    public String getLastPupilID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getStatus()) {
                ID = pupilList[i].getPupilID();
            }
        }
        return ID;
    }

    public Pupil getPupilByID(String ID) {
        Pupil pupil = null;
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getPupilID().equalsIgnoreCase(ID)) {
                if (pupilList[i].getStatus()) {
                    pupil = pupilList[i];
                    break;
                } else {
                    System.out.println("Pupil does not exist!");
                }
            }
        }
        return pupil;
    }

    public void findPupilsBy(String value, String findBy, Class<?> mainClass, Class<?> nestedClass) {
        Arrays.fill(searchResult, null);
        searchResultLength = 0;
        Pattern pattern = Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < currentIndex; i++) {
            try {
                if (nestedClass != null) {
                    // Get the nested object from the main object
                    Object nestedObject = mainClass.getMethod("get" + nestedClass.getSimpleName()).invoke(pupilList[i]);

                    // Use reflection to get the appropriate method from the nested class
                    Method getterMethod = nestedClass.getMethod(findBy);

                    // Invoke the method on the nested object
                    String attributeValue = (String) getterMethod.invoke(nestedObject);

                    if (pattern.matcher(attributeValue).find()) {
                        if (pupilList[i].getStatus()) {
                            this.searchResult[this.searchResultLength++] = pupilList[i];
                        } else {
                            System.out.println("Pupil does not exist!");
                        }
                    }
                } else {
                    // No nested class, directly invoke the method on the main class
                    Method getterMethod = mainClass.getMethod(findBy);
                    String attributeValue = (String) getterMethod.invoke(pupilList[i]);

                    if (pattern.matcher(attributeValue).find()) {
                        if (pupilList[i].getStatus()) {
                            this.searchResult[this.searchResultLength++] = pupilList[i];
                        } else {
                            System.out.println("Pupil does not exist!");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getPupilArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getPupilID().equalsIgnoreCase(ID)) {
                if (pupilList[i].getStatus()) {
                    index = i;
                    break;
                }
            }
        }
        return index;
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
        String pupilID = updatedRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(pupilID)) {
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
            // writeDatabase(updatedRecords);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found in the database. Deletion failed.");
        }
    }

    public static String readDatabase() {
        StringBuilder records = new StringBuilder();
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\pupils.txt";
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

    // <<<<<<< HEAD
    private void writeDatabase(String records) {
        // String relativePath = System.getProperty("user.dir") +
        // "\\Pupil-Management-OOP\\src\\main\\java\\Data\\pupils.txt";
        // =======
        // public static void writeDatabase(String records) {
        // String relativePath = System.getProperty("user.dir") +
        // "\\src\\main\\java\\Data\\pupils.txt";
        // >>>>>>> af208d989da2b5b73387018af840326e7acfc5b7
        // File file = new File(relativePath);
        // try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        // writer.write(records);
        // } catch (IOException e) {
        // Handle IOException
        // e.printStackTrace();
        // }
    }
}
