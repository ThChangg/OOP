package Classes.Parents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import Classes.Redux.Redux;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class ParentManagement implements IFileManagement, ICRUD {
    private Parent parentList[];
    private Parent searchResult[];
    private int currentIndex;
    private int searchResultLength;
    private static String inputRelativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\parents.txt";

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
        File file = new File(inputRelativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputRelativePath), "UTF-8"))) {
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
                        Pattern pattern = Pattern.compile(Address.getAddressRegex());
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
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                writer.write("Parent Management List:");
                writer.newLine();
                writer.write(String.format(Redux.personInfoFormat + "\t%-11s", "ID", "Fullname", "Gender",
                        "BirthDate", "Address", "Phone Number"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    if (parentList[i].getStatus()) {
                        writer.write(parentList[i].toString());
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
                writer.write(String.format(Redux.personInfoFormat + "\t%-11s", "ID", "Fullname", "Gender",
                        "BirthDate", "Address", "Phone Number"));
                writer.newLine();
                for (int i = 0; i < arrayLength; i++) {
                    Parent parent = searchResult[i];
                    writer.write(parent.toString());
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
            boolean flag = true;
            String name = "";
            do {
                System.out.println("Old Fullname: " + parent.getFullname());
                System.out.print("New Fullname (Format: Tran Duc Canh): ");
                name = sc.nextLine();
                if (!name.isEmpty()) {
                    flag = Parent.isValidName(name);
                    if (flag) {
                        parent.setFullname(name);
                    } else {
                        System.out.println("Fullname is invalid (Wrong format)!");
                    }
                } else {
                    name = parent.getFullname();
                    flag = true;
                }

            } while (!flag);

            String birthDate = "";
            do {
                System.out.println("Old BirthDate: " + parent.getBirthDate());
                System.out.print("New BirthDate (Format: 02/02/1976): ");
                birthDate = sc.nextLine();

                if (!birthDate.isEmpty()) {
                    flag = Date.isValidDateAndMonth(birthDate);
                    if (flag) {
                        Date newDob = new Date(birthDate);
                        parent.setBirthDate(newDob);
                    } else {
                        System.out.println("BirthDate is invalid (Wrong format)!");
                    }

                } else {
                    birthDate = parent.getBirthDate().toString();
                    flag = true;
                }
            } while (!flag);

            String gender = "";
            do {
                System.out.println("Old gender: " + parent.getGender());
                System.out.print("New gender (Format: male / female): ");
                gender = sc.nextLine();

                if (!gender.isEmpty()) {
                    flag = Parent.isValidGender(gender);
                    if (flag) {
                        parent.setGender(gender);
                    } else {
                        System.out.println("gender is invalid (Wrong format)!");
                    }
                } else {
                    gender = parent.getGender();
                    flag = true;
                }
            } while (!flag);

            String address = "";
            do {
                System.out.println("Old Address: " + parent.getAddress());
                System.out
                        .print("New Address (Format: 66, Phan Van Tri, Phuong 9, Quan 3, Thanh pho Thu Duc): ");
                address = sc.nextLine();
                if (!address.isEmpty()) {
                    flag = Address.isValidAddress(address);
                    if (flag) {
                        Address newAddress = new Address(address);
                        parent.setAddress(newAddress);
                    } else {
                        System.out.println("Address is invalid (Wrong format)!");
                    }
                } else {
                    address = parent.getAddress().toString();
                    flag = true;
                }
            } while (!flag);

            String phoneNumber = "";
            do {
                System.out.println("Old Phone Number: " + parent.getPhoneNumber());
                System.out.print("New Phone Number (Format: 0907654321): ");
                phoneNumber = sc.nextLine();
                if (!phoneNumber.isEmpty()) {
                    flag = Parent.isValidPhoneNumber(phoneNumber);
                    if (flag) {
                        parent.setPhoneNumber(phoneNumber);
                    } else {
                        System.out.println("Phone Number is invalid (Wrong format)!");
                    }
                } else {
                    phoneNumber = parent.getPhoneNumber();
                    flag = true;
                }
            } while (!flag);

            String record = parent.getParentID() + "-" + name + "-" + birthDate + "-" + parent.getAddress()
                    + "-" + phoneNumber + "-" + gender;
            this.updateRecord(record);
            System.out.println("Update successfully!");

        } else {
            System.out.println("Parent with ID: " + ID + " is not found!");
        }
    }

    public void update(Object obj) {
        for (int i = 0; i < currentIndex; i++) {
            if (parentList[i].getParentID().equalsIgnoreCase(((Parent) obj).getParentID())) {
                parentList[i] = (Parent) obj;
                break;
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
            if (parentList[i].getStatus()) {
                if (parentList[i].getPupil().getFullname().toLowerCase().contains(searchValue.toLowerCase())) {
                    searchResult[searchResultLength++] = parentList[i];
                }
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
        StringBuilder records = new StringBuilder();;
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
}
