package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Classes.Account.Account;
import Classes.Account.AccountManagement;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;

public class Redux {
    private static AccountManagement accountManagement = new AccountManagement();
    public static boolean isLoggedIn = false;
    public static boolean isAdmin = false;
    public static Object deletedObjects[] = new Object[100];
    public static int deletedObjectsCount = 0;

    public static void add(Object obj) {
        if (deletedObjectsCount < deletedObjects.length) {
            deletedObjects[deletedObjectsCount++] = obj;
        } else {
            System.out.println("Recycle Bin is full. Please delete it first!");
        }
    }

    public static void displayRecycleBin(Scanner sc) {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);

        if (file.exists()) {
            // File exists, you can work with it
            boolean flags[] = checkInstances();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("========================== Recycle Bin ==========================");
                writer.newLine();
                if (flags[0]) {
                    // Check if existing at least one Pupil instance
                    writer.write("++++++++++++++++ Pupil Management List Session ++++++++++++++++");
                    writer.newLine();
                    writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-3s", "ID", "Fullname", "Sex",
                            "BirthDate", "Address",
                            "Class"));
                    writer.newLine();
                    for (int i = 0; i < deletedObjectsCount; i++) {
                        if (deletedObjects[i] instanceof Pupil) {
                            writer.write(deletedObjects[i].toString());
                            writer.newLine();
                        }
                    }
                    writer.write("================================================================");
                    writer.newLine();
                }

                if (flags[1]) {
                    // Check if existing at least one Teacher instance
                    writer.write("++++++++++++++++ Teacher Management List Session ++++++++++++++++");
                    writer.newLine();
                    writer.write(String.format("%-5s\t%-20s\t%-6s\t%-10s\t%-80s\t%-10s", "ID", "Fullname", "Sex",
                            "BirthDate", "Address",
                            "Major"));
                    writer.newLine();
                    for (int i = 0; i < deletedObjectsCount; i++) {
                        if (deletedObjects[i] instanceof Teacher) {
                            writer.write(deletedObjects[i].toString());
                            writer.newLine();
                        }
                    }
                    writer.write("================================================================");
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }

            if (!flags[0] && !flags[1]) {
                System.out.println("Recycle Bin is clear");
            } else {
                System.out.println("Do you want to restore or delete your data ? Restore(R/r) : Delete(D/d)");
                char option = sc.nextLine().charAt(0);
                switch (option) {
                    case 'r':
                    case 'R':
                        System.out.println("Do you want to restore your data ? Yes(Y/y) : No(N/n) : All(A/a)");
                        char restoreOption = sc.nextLine().charAt(0);
                        String restoreDataID = "";
                        if (restoreOption == 'y' || restoreOption == 'Y') {
                            System.out.print("Please enter data ID: ");
                            restoreDataID = sc.nextLine();
                            restoreData(restoreDataID);
                        } else if (restoreOption == 'a' || restoreOption == 'A') {
                            for (int i = 0; i < deletedObjectsCount; i++) {
                                restoreAllData();
                            }
                        }
                        break;

                    case 'd':
                    case 'D':
                        System.out
                                .println("Do you want to permanently delete your data ? Yes(Y/y) : No(N/n) : All(A/a)");
                        char deleteOption = sc.nextLine().charAt(0);
                        String deleteDataID = "";
                        if (deleteOption == 'y' || deleteOption == 'Y') {
                            System.out.print("Please enter data ID: ");
                            deleteDataID = sc.nextLine();
                            permanentlyDeleteData(deleteDataID);
                        } else if (deleteOption == 'a' || deleteOption == 'A') {
                            permanentlyDeleteAllData();
                        }
                        break;

                    default:
                        break;
                }
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public static void restoreData(String ID) {
        boolean isRestore = false;
        for (int i = 0; i < deletedObjectsCount; i++) {
            if (deletedObjects[i] instanceof Pupil) {
                Pupil pupil = (Pupil) deletedObjects[i];
                if (pupil.getPupilID().equalsIgnoreCase(ID)) {
                    pupil.setStatus(true);
                    isRestore = true;
                    removeElementFromRecycleBin(ID);
                    break;
                }
            } else if (deletedObjects[i] instanceof Teacher) {
                Teacher teacher = (Teacher) deletedObjects[i];
                if (teacher.getTeacherID().equalsIgnoreCase(ID)) {
                    // teacher.setStatus(true);
                    break;
                }
            } else {
                System.out.println("Instance not found!");
            }
        }
        if (isRestore) {
            System.out.println("Data restored successfully!");
        }
    }

    public static void restoreAllData() {
        for (int i = 0; i < deletedObjectsCount; i++) {
            if (deletedObjects[i] instanceof Pupil) {
                Pupil pupil = (Pupil) deletedObjects[i];
                pupil.setStatus(true);
            } else if (deletedObjects[i] instanceof Teacher) {
                // Similar restoration logic for Teacher
            }
        }
        System.out.println("All data have been restored successfully!");
        deletedObjectsCount = 0; // Reset the count as all data has been restored
    }

    public static void permanentlyDeleteData(String ID) {
        boolean isDelete = false;
        for (int i = 0; i < deletedObjectsCount; i++) {
            if (deletedObjects[i] instanceof Pupil) {
                Pupil pupil = (Pupil) deletedObjects[i];
                if (pupil.getPupilID().equalsIgnoreCase(ID)) {
                    String address = pupil.getAddress().toString().replace(" Duong ", " ");
                    String record = pupil.getPupilID() + "-" + pupil.getFullname() + "-" + pupil.getBirthDate() + "-"
                            + address + "-" + pupil.getClassroom().getClassName() + "-" + pupil.getSex();
                    PupilManagement.deleteRecord(record);
                    isDelete = true;
                    removeElementFromRecycleBin(ID);
                    break;
                }
            } else if (deletedObjects[i] instanceof Teacher) {
                Teacher teacher = (Teacher) deletedObjects[i];
                if (teacher.getTeacherID().equalsIgnoreCase(ID)) {
                    // teacher.setStatus(true);
                    // break;
                    String address = teacher.getAddress().toString().replace(" Duong ", " ");
                    String record = teacher.getTeacherID() + "-" + teacher.getFullname() + "-" + teacher.getBirthDate() + "-"
                            + address + "-" + teacher.getMajor() + teacher.getClassroom().getClassName() + "-" + teacher.getSex();
                    TeacherManagement.deleteTeacherRecord(record);
                    isDelete = true;
                    removeElementFromRecycleBin(ID);
                    break;
                }
            } else {
                System.out.println("Instance not found!");
            }
        }
        if (isDelete) {
            System.out.println("Data deleted successfully!");
        }
    }

    public static void permanentlyDeleteAllData() {
        for (int i = 0; i < deletedObjectsCount; i++) {
            if (deletedObjects[i] instanceof Pupil) {
                Pupil pupil = (Pupil) deletedObjects[i];
                String address = pupil.getAddress().toString().replace(" Duong ", " ");
                String pupilRecord = pupil.getPupilID() + "-" + pupil.getFullname() + "-" + pupil.getBirthDate() + "-"
                        + address + "-" + pupil.getClassroom().getClassName() + "-" + pupil.getSex();

                PupilManagement.deleteRecord(pupilRecord);
            } else if (deletedObjects[i] instanceof Teacher) {
                // Similar deletion logic for Teacher
            }
        }
        System.out.println("All data have been permanently deleted successfully!");
        deletedObjectsCount = 0; // Reset the count as all data has been permanently deleted
    }

    public static int getDeletedObjectIndex(String ID) {
        int index = -1;
        for (int i = 0; i < deletedObjects.length; i++) {
            if (deletedObjects[i] instanceof Pupil) {
                Pupil pupil = (Pupil) deletedObjects[i];
                if (pupil.getPupilID().equalsIgnoreCase(ID)) {
                    index = i;
                    break;
                }
            } else if (deletedObjects[i] instanceof Teacher) {
                Teacher pupil = (Teacher) deletedObjects[i];
                if (pupil.getTeacherID().equalsIgnoreCase(ID)) {
                    // pupil.setStatus(true);
                    // index = i;
                    // break;
                }
            }
        }
        return index;
    }

    public static void removeElementFromRecycleBin(String ID) {
        int pos = getDeletedObjectIndex(ID);
        if (pos >= 0) {
            for (int i = pos; i < deletedObjectsCount - 1; i++) {
                deletedObjects[i] = deletedObjects[i + 1];
            }
            deletedObjectsCount--;
        } else {
            System.out.println("No instance found!");
        }
    }

    public static boolean[] checkInstances() {
        boolean pupilFlag = false, teacherFlag = false;
        for (int i = 0; i < deletedObjectsCount; i++) {
            if (deletedObjects[i] instanceof Pupil) {
                pupilFlag = true;
            } else if (deletedObjects[i] instanceof Teacher) {
                teacherFlag = true;
            }
        }
        return new boolean[] { pupilFlag, teacherFlag };
    }

    public static void signInForm(Scanner sc) {
        boolean flag = false;

        System.out.println("Sign In");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        flag = accountManagement.authenticate(username, password);

        if (!flag) {
            System.out.println("Wrong username or password!");
        } else {
            isLoggedIn = flag;
            isAdmin = accountManagement.authorize(username, password) ? true : false;
        }
    }

    public static void signUpForm(Scanner sc) {
        System.out.println("Sign Up");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = sc.nextLine();
        if (accountManagement.isVerifiedUsername(username)) {
            if (password.equals(confirmPassword)) {
                Account newAccount = new Account(username, password);
                accountManagement.add(newAccount);
            } else {
                System.out.println("Password is not matched!");
            }
        } else {
            System.out.println("Username is already in use!");
        }
    }
}
