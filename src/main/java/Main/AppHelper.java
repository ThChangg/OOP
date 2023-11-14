package Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Classroom.Classroom;
import Classes.Classroom.ClassroomManagement;
import Classes.Classroom.Grade;
import Classes.Parents.Parent;
import Classes.Parents.ParentManagement;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;
import Classes.Classroom.Grade;

public class AppHelper {
    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        ParentManagement parentManagement = new ParentManagement();
        TeacherManagement teacherManagement = new TeacherManagement();
        ClassroomManagement classroomManagement = new ClassroomManagement();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("========================== Menu ==========================");
            System.out.println("Please select: ");
            System.out.println("1. Initialize data");
            System.out.println("2. Print out data");
            System.out.println("3. Adding 1 or n person to");
            System.out.println("4. Update person information");
            System.out.println("5. Delete person");
            System.out.println("6. Searching for the person information");
            System.out.println("7. Statistics");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    appInitialize(pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    break;
                case 2:
                    appDisplay(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    break;
                case 3:
                System.out.println("3. Adding 1 or n person to");
                System.out.println("1) Add pupils");
                System.out.println("2) Add parents");
                int subOption = Integer.parseInt(sc.nextLine());

                switch (subOption) {
                    case 1:
                        AppHelper.addPupilsToPupilManagementList(pupilManagement, sc);
                        break;
                    case 2:
                        AppHelper.addParentsToParentManagementList(parentManagement, sc);
                        break;
                        case 3:
                        AppHelper.addClassroomsToClassroomManagementList(classroomManagement, sc);
                        break;
                    default:
                        System.out.println("Invalid sub-option");
                        break;
                }
                    break;
                case 4:
                System.out.println("4. Update person information");
                System.out.println("1) Update pupils");
                System.out.println("2) Update parents");
                int subOption1 = Integer.parseInt(sc.nextLine());

                switch (subOption1) {
                    case 1:
                        AppHelper.updatePupilData(pupilManagement, sc);
                        break;
                    case 2:
                        AppHelper.updateParentData(parentManagement, sc);
                        break;
                        case 3:
                        AppHelper.updateClassroomData(classroomManagement, sc);
                        break;
                    default:
                        System.out.println("Invalid sub-option");
                        break;
                }
                    break;
                case 5:
                System.out.println("5. Delete person");
                System.out.println("1) Delete pupils");
                System.out.println("2) Delete parents");
                int subOption2 = Integer.parseInt(sc.nextLine());

                switch (subOption2) {
                    case 1:
                        AppHelper.deletePupilData(pupilManagement, sc);
                        break;
                    case 2:
                        AppHelper.deleteParentData(parentManagement, sc);
                        break;
                        case 3:
                        AppHelper.deleteClassroomData(classroomManagement, sc);
                        break;
                    default:
                        System.out.println("Invalid sub-option");
                        break;
                }
                    break;
                case 6:
                System.out.println("6. Searching for the person information");
                    System.out.println("1) Search pupils");
                    System.out.println("2) Search parents");
                    int subOption3 = Integer.parseInt(sc.nextLine());

                    switch (subOption3) {
                        case 1:
                        	AppHelper.searchPupilData(pupilManagement, sc);
                            break;
                        case 2:
                            AppHelper.searchParentData(parentManagement, sc);
                            break;
                        default:
                            System.out.println("Invalid sub-option");
                            break;
                    }
                    break;
                case 7:

                    break;
                default:
                    System.out.println("Exited!");
                    break;
            }
        } while (option != 0);
        sc.close();
    }

    public static void appInitialize(Object... managementObjects) {
        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                ((PupilManagement) managementObject).initialize();
            } else if (managementObject instanceof ClassroomManagement) {
                ((ClassroomManagement) managementObject).initialize();
            } else if (managementObject instanceof TeacherManagement) {
                ((TeacherManagement) managementObject).initialize();
            }else if (managementObject instanceof ParentManagement) {
                ((ParentManagement) managementObject).initialize();
            }
            // Add more else if blocks for other management objects
        }
        System.out.println("App is now initialized!");
    }

    public static void appDisplay(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;
        ParentManagement parentManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            }else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
            
            
            // Add more else if blocks for other management objects
        }

        int option = 0;
        do {
            System.out.println("======================= Display data session =======================");
            System.out.println("1. Display pupils data");
            System.out.println("2. Display teachers data");
            System.out.println("3. Display parents data");
            System.out.println("4. Display points data");
            System.out.println("5. Display classrooms data");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    pupilManagement.display();
                    break;

                case 2:
                    teacherManagement.display();
                    break;

                case 3:
                    parentManagement.display();
                    break;

                case 4:

                    break;

                case 5:
                    classroomManagement.display();
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

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
        String addressRegex = "(\\d+),\\s(.*),\\sPhuong\\s(.*),\\sQuan\\s(.*),\\sThanh pho\\s(.*$)";
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(addressPart);

        boolean flag = true;
        if (!matcher.matches()) {
            flag = false;
        }
        return flag;
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
    }

    public static void deletePupilData(PupilManagement pupilManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pupilManagement.delete(ID);
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
                        "Address: (format: 18/29, Nguyen Van HOan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                boolean flag = isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!isValidAddress(inputAddress));
            Address address = new Address(inputAddress);

            String teacherID = createTeacherID(teacherManagement.getLastTeacherID());
            teacherManagement.add(new Teacher(teacherID, fullName, dob, address));

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

    public static void searchPupilData(PupilManagement pupilManagement, Scanner sc) {
        int option = 0;
        do {
            System.out.println("======================= Search for pupils data session =======================");
            System.out.println("1. Search pupils data by name");
            System.out.println("2. Search pupils data by class");
            System.out.println("3. Search pupils data by sex");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    pupilManagement.findPupilsByName(name);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }
    public static String createParentID(String lastParentID) {
        String prefix = lastParentID.substring(0, 2);
        int number = Integer.parseInt(lastParentID.substring(2));

        number++;

        // Format it back into the original string format, %s for a string, %03d for a
        // number with 3 digits, and padding 0 before if a number has less than 3 digits
        String result = String.format("%s%03d", prefix, number);
        return result;
    }
    public static void addParentsToParentManagementList(ParentManagement parentManagement, Scanner scanner) {
        char option = 'y';
        do {
            System.out.println("Add Parents: ");
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

            String parentID = createParentID(parentManagement.getLastParentID());
          parentManagement.add(new Parent(parentID, fullName, dob, address));

            System.out.println("Do you want to add more parents ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');

    }
    public static void updateParentData(ParentManagement parentManagement, Scanner scanner) {
        System.out.print("Enter parent ID: ");
        String ID = scanner.nextLine();
        parentManagement.update(ID);
    }
    public static void deleteParentData(ParentManagement parentManagement, Scanner scanner) {
        System.out.print("Enter parent ID: ");
        String ID = scanner.nextLine();
        parentManagement.delete(ID);
    }
    public static void searchParentData(ParentManagement parentManagement, Scanner sc) {
        int option = 0;
        do {
            System.out.println("======================= Search for parents data session =======================");
            System.out.println("1. Search parents data by name");
            System.out.println("2. Search parents data by class");
            System.out.println("3. Search parents data by sex");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    parentManagement.findParentsByName(name);
                    parentManagement.display(parentManagement.getSearchResultLength());
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }
    public static void addClassroomsToClassroomManagementList(ClassroomManagement classroomManagement, Scanner scanner) {
        char option = 'y';
        do {
            System.out.println("Add classrooms: ");
            System.out.print("Classname (Format: 6A1): ");
            String className = scanner.nextLine();
            

	        System.out.println("Add classManagerID: ");
            System.out.print("ClassManagerID (Format: GV0016): ");
            String classManagerID = scanner.nextLine();
            
            
            System.out.println("Add grade: ");
            System.out.print("GradeNumber (Format: 6): ");
            int gradeNumber = Integer.parseInt(scanner.nextLine());

            System.out.println("Add grademanager: ");
            System.out.print("GrademanagerID (Format: GV0016): ");
            String gradeManagerID = scanner.nextLine();
                
            Grade grade = new Grade(gradeNumber, null);   
            classroomManagement.add(new Classroom(className, grade));

            System.out.println("Do you want to add more classrooms ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');

    }

    public static void updateClassroomData(ClassroomManagement classroomManagement, Scanner scanner) {
        System.out.print("Enter class name: ");
        String ID = scanner.nextLine();
        classroomManagement.update(ID);
    }

    public static void deleteClassroomData(ClassroomManagement classroomManagement, Scanner scanner) {
        System.out.print("Enter class name: ");
        String ID = scanner.nextLine();
        classroomManagement.delete(ID);

    }
}
