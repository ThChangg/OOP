package Classes.Pupils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Classroom.ClassroomManagement;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Teachers.TeacherManagement;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

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

    public Pupil[] getPupilManagement() {
        return this.pupilList;
    }

    public void setPupilManagement(Pupil pupilManagement[]) {
        this.pupilList = pupilManagement;
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
                        String classID = parts[4];

                        String dobParts[] = dobString.split("/");
                        String date = dobParts[0];
                        String month = dobParts[1];
                        String year = dobParts[2];

                        Date dob = new Date(date, month, year);

                        String addressPart = parts[3];
                        String addressRegex = "(\\d+),\\s(.*),\\sPhuong\\s(.*),\\sQuan\\s(.*),\\sThanh pho\\s(.*$)";
                        Pattern pattern = Pattern.compile(addressRegex);
                        Matcher matcher = pattern.matcher(addressPart);
                        if (matcher.matches()) {
                            String streetNumber = matcher.group(1);
                            String streetName = "Duong " + matcher.group(2);
                            String ward = "Phuong " + matcher.group(3);
                            String district = "Quan " + matcher.group(4);
                            String city = "Thanh pho " + matcher.group(5);

                            Address address = new Address(streetNumber, streetName, ward, district, city);
                            Pupil pupil = new Pupil(pupilID, fullName, dob, address);
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
            System.out.println("File exists.");
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
                writer.write(String.format("%-5s\t%-20s\t%-10s\t%-70s", "ID", "Fullname", "BirthDate", "Address"));
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
            System.out.println("File exists.");
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
                writer.write(String.format("%-5s\t%-20s\t%-10s\t%-70s", "ID", "Fullname", "BirthDate", "Address"));
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
            System.out.println("File exists.");
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
            System.out.println("Old Fullname: " + pupil.getFullname());
            System.out.print("New Fullname (Format: Tran Duc Canh): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                pupil.setFullname(name);
            }

            System.out.println("Old BirthDate: " + pupil.getBirthDate());
            System.out.print("New BirthDate (Format: 02/02/2017): ");
            String birthDate = sc.nextLine();
            if (!birthDate.isEmpty()) {
                String dobParts[] = birthDate.split("/");
                String date = dobParts[0];
                String month = dobParts[1];
                String year = dobParts[2];

                Date newDob = new Date(date, month, year);
                pupil.setBirthDate(newDob);
            }

            System.out.println("Old Address: " + pupil.getAddress());
            System.out.print("New Address (Format: 66, Phan Van Tri, Phuong 9, Quan 3, Thanh pho Thu Duc): ");
            String address = sc.nextLine();
            if (!address.isEmpty()) {
                String addressRegex = "(\\d+),\\s(.*),\\sPhuong\\s(.*),\\sQuan\\s(.*),\\sThanh pho\\s(.*$)";
                Pattern pattern = Pattern.compile(addressRegex);
                Matcher matcher = pattern.matcher(address);
                if (matcher.matches()) {
                    String streetNumber = matcher.group(1);
                    String streetName = "Duong " + matcher.group(2);
                    String ward = "Phuong " + matcher.group(3);
                    String district = "Quan " + matcher.group(4);
                    String city = "Thanh pho " + matcher.group(5);

                    Address newAddress = new Address(streetNumber, streetName, ward, district, city);
                    pupil.setAddress(newAddress);
                } else {
                    System.out.println("Your address is invalid!");
                }
            }
            System.out.println("Update successfully!");
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
                }
            }
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Pupil with ID: " + ID + " is not found!");
        }
    }

    public void delete(String ID, Object ...managementObjects) {
        // for (Object managementObject : managementObjects) {
        //     if (managementObject instanceof PupilManagement) {
        //         ((PupilManagement) managementObject).delete(ID);
        //     } else if (managementObject instanceof ClassroomManagement) {
        //         ((ClassroomManagement) managementObject).initialize();
        //     } else if (managementObject instanceof TeacherManagement) {
        //         ((TeacherManagement) managementObject).initialize();
        //     }
        //     // Add more else if blocks for other management objects
        // }
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

    public void findPupilsByName(String name) {
        Pattern pattern = Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < currentIndex; i++) {
            if (pattern.matcher(pupilList[i].getFullname()).find()) {
                if (pupilList[i].getStatus()) {
                    this.searchResult[this.searchResultLength++] = pupilList[i];
                } else {
                    System.out.println("Pupil does not exist!");
                }
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
}
