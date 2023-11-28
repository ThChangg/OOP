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
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Redux.Redux;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class PupilManagement implements IFileManagement, ICRUD {
    private Pupil pupilList[];
    private int currentIndex;
    private Pupil searchResult[];
    private int searchResultLength;
    private static String inputRelativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\pupils.txt";

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
                            Pupil pupil = new Pupil(pupilID, fullName, dob, address, gender);
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
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                writer.write("Pupil Management List:");
                writer.newLine();
                writer.write(String.format(Redux.personInfoFormat + "\t%-3s", "ID", "Fullname", "Gender",
                        "BirthDate", "Address", "Class"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    if (pupilList[i].getStatus()) {
                        writer.write(pupilList[i].toString());
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

    // Display method just using for searching
    public void display(int arrayLength) {
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                writer.write("Search result:");
                writer.newLine();
                writer.write(String.format(Redux.personInfoFormat + "\t%-3s", "ID", "Fullname", "Gender",
                        "BirthDate", "Address", "Class"));
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
        if (currentIndex < pupilList.length) {
            pupilList[currentIndex++] = (Pupil) obj;

        } else {
            System.out.println("Pupil Management List is full. Cannot add more.");
        }
    }

    @Override
    public void update(Object obj) {
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getPupilID().equalsIgnoreCase(((Pupil) obj).getPupilID())) {
                pupilList[i] = (Pupil) obj;
                break;
            }
        }
    }

    @Override
    public void delete(String ID) {
        int index = this.getPupilArrayIndex(ID);

        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    pupilList[i].setStatus(false);
                    Redux.addToRecycleBin(pupilList[i]);
                }
            }
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Pupil with ID: " + ID + " is not found!");
        }
    }

    public int getNumberOfPupilsByPerformances(String performance) {
        int numberOfPupils = 0;
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getSubjectPoints().getPerformance().equalsIgnoreCase(performance)) {
                numberOfPupils++;
            }
        }
        return numberOfPupils;
    }

    public int getNumberOfPupilsByGradeAndPerformances(String grade, String performance) {
        int numberOfPupils = 0;
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getSubjectPoints().getPerformance().equalsIgnoreCase(performance)) {
                if (pupilList[i].getClassroom().getClassName().substring(0, 2).equalsIgnoreCase(grade)) {
                    numberOfPupils++;
                }
            }
        }
        return numberOfPupils;
    }

    public String getLastPupilID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            ID = pupilList[i].getPupilID();
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
                }
            }
        }
        return pupil;
    }

    public void findPupilsBy(String value, String findBy, Class<?> mainClass, Class<?> nestedClass,
            boolean isAccuracy) {
        Arrays.fill(searchResult, null);
        searchResultLength = 0;
        Pattern pattern = Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < currentIndex; i++) {
            try {
                if (nestedClass != null) {
                    // Get the nested object from the main object
                    Object nestedObject = mainClass.getMethod("get" + nestedClass.getSimpleName())
                            .invoke(pupilList[i]);

                    // Use reflection to get the appropriate method from the nested class
                    Method getterMethod = nestedClass.getMethod(findBy);

                    // Invoke the method on the nested object
                    String attributeValue = (String) getterMethod.invoke(nestedObject);

                    if (!isAccuracy) {
                        if (pattern.matcher(attributeValue).find()) {
                            if (pupilList[i].getStatus()) {
                                this.searchResult[this.searchResultLength++] = pupilList[i];
                            }
                        }
                    } else {
                        if (pattern.matcher(attributeValue).matches()) {
                            if (pupilList[i].getStatus()) {
                                this.searchResult[this.searchResultLength++] = pupilList[i];
                            }
                        }
                    }
                } else {
                    // No nested class, directly invoke the method on the main class
                    Method getterMethod = mainClass.getMethod(findBy);
                    String attributeValue = (String) getterMethod.invoke(pupilList[i]);

                    if (!isAccuracy) {
                        if (pattern.matcher(attributeValue).find()) {
                            if (pupilList[i].getStatus()) {
                                this.searchResult[this.searchResultLength++] = pupilList[i];
                            }
                        }
                    } else {
                        if (pattern.matcher(attributeValue).matches()) {
                            if (pupilList[i].getStatus()) {
                                this.searchResult[this.searchResultLength++] = pupilList[i];
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasUninitializedPupil() {
        boolean flag = false;
        for (int i = 0; i < currentIndex; i++) {
            if (pupilList[i].getParents() == null || pupilList[i].getSubjectPoints() == null) {
                flag = true;
                break;
            }
        }
        return flag;
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
        String pupilID = updatedRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(pupilID)) {
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
}
