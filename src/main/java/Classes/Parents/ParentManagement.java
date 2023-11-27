package Classes.Parents;

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
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Person.Address;
import Classes.Person.Date;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;
import Main.Redux;

public class ParentManagement implements IFileManagement, ICRUD {
    private Parent parentList[];
    private Parent searchResult[];
    private int currentIndex;
    private int searchResultLength;

    public ParentManagement() {
        parentList = new Parent[100];
        searchResult = new Parent[100];
        currentIndex = 0;
        searchResultLength = 0;
    }

    public Parent[] getParentList() {
        return this.parentList;
    }

    public void setParentList(Parent parentList[]) {
        this.parentList = parentList;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Parent[] getSearchResult() {
        return this.searchResult;
    }

    public void setSearchResult(Parent searchResult[]) {
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
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\parents.txt";
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
                        String phoneNumber = parts[4];
                        String gender = parts[5];

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
                            Parent parent = new Parent(pupilID, fullName, dob, address, gender, phoneNumber);
                            this.add(parent);
                        } else {
                            System.out.println("Your address is invalid!");
                        }
                    } else {
                        System.out.println("Record does not have enough information");
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
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Parent Management List:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-11s", "ID", "Fullname", "gender",
                        "BirthDate", "Address",
                        "Phone Number"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    if (parentList[i].getStatus()) {
                        writer.write(parentList[i].toString());
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

    public void display(int arrayLength) {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Search result:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-11s\t%-5s\t%-20s", "Parent ID",
                        "Parent Fullname", "gender",
                        "BirthDate", "Address", "Phone Number", "Pupil ID", "Pupil Fullname"));
                writer.newLine();
                for (int i = 0; i < arrayLength; i++) {
                    Parent parent = searchResult[i];
                    String pupilInfo = (parent.getPupil() != null)
                            ? parent.getPupil().getPupilID() + "\t" + parent.getPupil().getFullname()
                            : "";
                    writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-11s\t%-5s\t%-20s",
                            parent.getParentID(), parent.getFullName(), parent.getGender(), parent.getBirthDate(),
                            parent.getAddress(), parent.getPhoneNumber(), "", pupilInfo));
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
        if (currentIndex < parentList.length) {
            parentList[currentIndex++] = (Parent) obj;
        } else {
            System.out.println("Parent Management List is full. Cannot add more.");
        }
    }

    @Override
    public void update(String ID) {
        Scanner sc = new Scanner(System.in);
        Parent parent = getParentByID(ID);

        if (parent != null) {
            System.out.println("Old Fullname: " + parent.getFullname());
            System.out.print("New Fullname (Format: Tran Duc Canh): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                parent.setFullname(name);
            }

            System.out.println("Old BirthDate: " + parent.getBirthDate());
            System.out.print("New BirthDate (Format: 02/02/2017): ");
            String birthDate = sc.nextLine();
            if (!birthDate.isEmpty()) {
                String dobParts[] = birthDate.split("/");
                String date = dobParts[0];
                String month = dobParts[1];
                String year = dobParts[2];

                Date newDob = new Date(date, month, year);
                parent.setBirthDate(newDob);
            }

            System.out.println("Old Address: " + parent.getAddress());
            System.out.print("New Address (Format: 66, Phan Van Tri, Phuong 9, Quan 3, Thanh pho Thu Duc): ");
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
                    parent.setAddress(newAddress);
                } else {
                    System.out.println("Your address is invalid!");
                }

                System.out.println("Old Phone Number: " + parent.getPhoneNumber());
                System.out.print("New Phone Number (Format: 0907xxxxxx): ");
                String phoneNumber = sc.nextLine();
                if (!phoneNumber.isEmpty()) {
                    if (Parent.isValidPhoneNumber(phoneNumber)) {
                        parent.setPhoneNumber(phoneNumber);
                    } else {
                        System.out.println("Phone Number is invalid (Wrong format)!");
                    }
                }
                System.out.println("Old Gender: " + parent.getGender());
                System.out.print("New Gender (Format: male / female): ");
                String gender = sc.nextLine();
                if (!gender.isEmpty()) {
                    if (Parent.isValidGender(gender)) {
                        parent.setGender(gender);
                    } else {
                        System.out.println("Gender is invalid (Wrong format)!");
                    }
                }

                String record = parent.getParentID() + "-" + name + "-" + birthDate + "-" + parent.getAddress() + "-"
                        + "-" + phoneNumber + "-"
                        + "-"
                        + gender;
                this.updateRecord(record);
                System.out.println("Update successfully!");

            } else {
                System.out.println("Parent with ID: " + ID + " is not found!");
            }
        }
    }

    @Override
    public void delete(String ID) {
        int index = this.getParentArrayIndex(ID);

        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    parentList[i].setStatus(false);
                    Redux.addToRecycleBin(parentList[i]);
                }
            }
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Parent with ID: " + ID + " is not found!");
        }
    }

    public String getLastParentID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            if (parentList[i].getStatus()) {
                ID = parentList[i].getParentID();
            }
        }
        return ID;
    }

    public Parent getParentByID(String ID) {
        Parent parent = null;
        for (int i = 0; i < currentIndex; i++) {
            if (parentList[i].getParentID().equalsIgnoreCase(ID)) {
                if (parentList[i].getStatus()) {
                    parent = parentList[i];
                    break;
                } else {
                    System.out.println("Parent does not exist!");
                }
            }
        }
        return parent;
    }

    public void findParentsBy(String searchValue) {
        // Clear previous search results
        Arrays.fill(searchResult, null);
        searchResultLength = 0;

        for (int i = 0; i < currentIndex; i++) {
            if (parentList[i].getStatus())
                if (parentList[i].getPupil().getFullname().toLowerCase().contains(searchValue)) {
                    searchResult[searchResultLength++] = parentList[i];
                }

        }

    }

    public int getParentArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (parentList[i].getParentID().equalsIgnoreCase(ID)) {
                if (parentList[i].getStatus()) {
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
        String parentID = updatedRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(parentID)) {
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
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\parents.txt";
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

    public static void writeDatabase(String records) {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\parents.txt";
        File file = new File(relativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }
}
