package Classes.Teachers;

import Classes.Person.Address;
import Classes.Person.Date;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;
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

public class TeacherManagement implements IFileManagement, ICRUD {
    private Teacher teacherManagement[];
    private int currentIndex;

    public TeacherManagement() {
        teacherManagement = new Teacher[100];
        currentIndex = 0;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
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
                            Teacher teacher = new Teacher(teacherID, fullName, dob, address);
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
            System.out.println("File exists.");
        } else {
            System.out.println("File does not exist.");
        }

    }

    @Override
    public void display() {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output2.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Teacher Management List:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-20s\t%-10s\t%-70s", "ID", "Fullname", "BirthDate", "Address"));
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
            System.out.println("File exists.");
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
}
