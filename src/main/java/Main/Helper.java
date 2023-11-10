package Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;

public class Helper {
    public static boolean isValidDateAndMonth(String date) {
        String dateParts[] = date.split("/");
        int day = Integer.parseInt(dateParts[0]), month = Integer.parseInt(dateParts[1]);
        if (month < 1 || month > 12) {
            return false; // Invalid month
        }

        int[] daysInMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (day < 1 || day > daysInMonth[month]) {
            return false; // Invalid day for the given month
        }

        return true;
    }

    public static boolean isValidAddress(String address) {
        String addressPart = address;
        String addressRegex = "(\\d+),\\s(.*),\\sPhường\\s(.*),\\sQuận\\s(.*),\\sThành phố\\s(.*$)";
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(addressPart);
        if (matcher.matches()) {
            String streetNumber = matcher.group(1);
            String streetName = matcher.group(2);
            String ward = matcher.group(3);
            String district = matcher.group(4);
            String city = matcher.group(5);

            if (streetNumber.isEmpty() || streetName.isEmpty() || ward.isEmpty() || district.isEmpty()
                    || city.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static String createPupilID(String lastPupilID) {
        String prefix = lastPupilID.substring(0, 2);
        int number = Integer.parseInt(lastPupilID.substring(2));

        number++;

        // Format it back into the original string format, %s for a string, %03d for a
        // number with 3 digits, and padding 0 before if a number has less than 3 digits
        String result = String.format("%s%03d", prefix, number);
        return result;
    }

    public static void addPupilsToPupilManagementList(PupilManagement pupilManagement, Scanner scanner) {
        char option = 'y';
        do {
            System.out.println("Add pupils: ");
            System.out.print("Fullname (Format: Nguyen Duc Canh): ");
            String fullName = scanner.nextLine();

            String date = "";
            do {
                System.out.print("BirthDate: (format: 03/03/2017): ");
                date = scanner.nextLine();
                boolean flag = isValidDateAndMonth(date);

                if (!flag) {
                    System.out.println("BirthDate is invalid (Wrong format)!");
                }
            } while (!isValidDateAndMonth(date));
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address: (format: 03, Nguyen Van Troi, Phuong 5, Quan Binh Thanh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                boolean flag = isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!isValidAddress(inputAddress));
            Address address = new Address(inputAddress);

            String pupilID = createPupilID(pupilManagement.getLastPupilID());
            pupilManagement.add(new Pupil(pupilID, fullName, dob, address));

            System.out.println("Do you want to add more pupils ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');

    }

    public static void updatePupilData(PupilManagement pupilManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pupilManagement.update(ID);
        System.out.println("Update successfully!");
    }

    public static void deletePupilData(PupilManagement pupilManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pupilManagement.delete(ID);
        System.out.println("Delete successfully!");
    }
    public static String createTeacherID(String lastTeacherID) {
        String prefix = lastTeacherID.substring(0, 2);
        int number = Integer.parseInt(lastTeacherID.substring(2));

        number++;

        // Format it back into the original string format, %s for a string, %03d for a
        // number with 3 digits, and padding 0 before if a number has less than 3 digits
        String result = String.format("%s%03d", prefix, number);
        return result;
    }

    public static void addTeachersToTeacherManagementList(TeacherManagement teacherManagement, Scanner scanner) {
        char option = 'y';
        do {
            System.out.println("Add teachers: ");
            System.out.print("Fullname (Format: Tran Le Anh Khoi): ");
            String fullName = scanner.nextLine();

            String date = "";
            do {
                System.out.print("BirthDate: (format: 16/02/2000): ");
                date = scanner.nextLine();
                boolean flag = isValidDateAndMonth(date);

                if (!flag) {
                    System.out.println("BirthDate is invalid (Wrong format)!");
                }
            } while (!isValidDateAndMonth(date));
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address: (format: 18, Nguyen Van Hoan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                boolean flag = isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!isValidAddress(inputAddress));
            Address address = new Address(inputAddress);

            String teacherID = createTeacherID(teacherManagement.getLastTeacherID());
            teacherManagement.add(new Teacher (teacherID, fullName, dob, address));

            System.out.println("Do you want to add more teacher ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');

    }

    public static void updateTeacherData(TeacherManagement teacherManagement, Scanner scanner) {
        System.out.print("Enter teacher ID: ");
        String ID = scanner.nextLine();
        teacherManagement.update(ID);
        System.out.println("Update successfully!");
    }

    public static void deleteTeacherData(TeacherManagement teacherManagement, Scanner scanner) {
        System.out.print("Enter teacher ID: ");
        String ID = scanner.nextLine();
        teacherManagement.delete(ID);
        System.out.println("Delete successfully!");
    }
    
    public static void searchTeacherID(TeacherManagement teacherManagement, Scanner scanner){
        System.out.println("Enter teacher ID: ");
        String ID = scanner.nextLine();
        Teacher teacher = teacherManagement.getTeacherByID(ID);
        System.out.println(teacher.toString());
    }
    
    public static void searchTeacherName(TeacherManagement teacherManagement, Scanner scanner){
        System.out.println("Enter teacher Name: ");
        String fullName = scanner.nextLine();
        Teacher teacher = teacherManagement.getTeacherByName(fullName);
        System.out.println(teacher.toString());
    }
    /*public static void searchTeacherClassroom(TeacherManagement teacherManagement, Scanner scanner){
        System.out.println("Enter teacher Classroom: ");
        String classRoom = scanner.nextLine();
        Teacher teacher = teacherManagement.getTeacherByClassName(classRoom);
        System.out.println(teacher.toString());
    }
    public static void searchTeacherMajor(TeacherManagement teacherManagement, Scanner scanner){
        System.out.println("Enter teacher Name: ");
        String major = scanner.nextLine();
        Teacher[] teacher = teacherManagement.getTeacherByMajor(major);
        System.out.println(teacher.toString());
    }*/
}
