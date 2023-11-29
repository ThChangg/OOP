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
import Classes.Points.Conduct;
import Classes.Points.Point;
import Classes.Points.PointManagement;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Redux.Redux;
import Classes.Statistics.Statistics;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;

public class AppHelper {
    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        ParentManagement parentManagement = new ParentManagement();
        TeacherManagement teacherManagement = new TeacherManagement();
        ClassroomManagement classroomManagement = new ClassroomManagement();
        PointManagement pointManagement = new PointManagement();

        int option = -1;
        boolean isCloseApp = false;
        boolean isAppInitialized = false;
        Scanner sc = new Scanner(System.in);

        do {
            while (option != 0 && !Redux.isLoggedIn && !isCloseApp) {
                System.out.println("Have you got an account yet ? Yes(Y/y) : No(N/n) : Close App(X/x)");
                char opt;
                do {
                    System.out.print("Enter your option: ");
                    String input = sc.nextLine().trim();

                    // Check if the input is not empty before accessing the first character
                    opt = input.isEmpty() ? '\0' : input.charAt(0);
                } while (opt == '\0');

                switch (opt) {
                    case 'y':
                    case 'Y':
                        Redux.signInForm(sc);
                        break;

                    case 'n':
                    case 'N':
                        Redux.signUpForm(sc);
                        break;

                    case 'x':
                    case 'X':
                        isCloseApp = true;
                        break;
                }
            }

            if (Redux.isLoggedIn) {
                if (!isAppInitialized) {
                    appInitialize(pupilManagement, classroomManagement, teacherManagement, parentManagement,
                            pointManagement);
                    isAppInitialized = true;
                }

                System.out.println("========================== Menu ==========================");
                System.out.println("Please select: ");
                System.out.println("1. Print out data");
                if (Redux.isAdmin) {
                    System.out.println("2. Adding data");
                    System.out.println("3. Update data");
                    System.out.println("4. Delete data");
                }
                System.out.println("5. Searching for data");
                if (Redux.isAdmin) {
                    System.out.println("6. Statistics");
                }
                System.out.println("7. Logout");
                System.out.println("0. Exit");
            }

            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();

                if (!isCloseApp && input.isEmpty()) {
                    System.out.println("Please enter your option!");
                    option = -1;
                } else {
                    try {
                        option = isCloseApp ? 0 : Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                }
            } while (option == -1);

            switch (option) {
                case 1:
                    appDisplay(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement,
                            pointManagement);
                    break;
                case 2:
                    if (Redux.isAdmin) {
                        appCreate(sc, pupilManagement, teacherManagement,
                                parentManagement, classroomManagement, pointManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 3:
                    if (Redux.isAdmin) {
                        appUpdate(sc, pupilManagement, classroomManagement, teacherManagement,
                                parentManagement, pointManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 4:
                    if (Redux.isAdmin) {
                        appDelete(sc, pupilManagement, classroomManagement, teacherManagement,
                                parentManagement, pointManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 5:
                    appSearch(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement,
                            pointManagement);
                    break;
                case 6:
                    if (Redux.isAdmin) {
                        appStatistics(sc, pupilManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 7:
                    Redux.isLoggedIn = false;
                    Redux.isAdmin = false;
                    break;
                default:
                    System.out.println("Exited!");
                    break;
            }
        } while (option != 0);
        sc.close();
    }

    public static void appInitialize(Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;
        ParentManagement parentManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
                pupilManagement.initialize();
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
                classroomManagement.initialize();
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
                teacherManagement.initialize();
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
                pointManagement.initialize();
            } else {
                parentManagement = (ParentManagement) managementObject;
                parentManagement.initialize();
            }
            // Add more else if blocks for other management objects

        }
        setupPupilManagement(pupilManagement, classroomManagement, parentManagement, pointManagement);
        setupClassroomManagement(classroomManagement, teacherManagement);
        System.out.println("App is now initialized!");
    }

    public static void setupPupilManagement(Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ClassroomManagement classroomManagement = null;
        ParentManagement parentManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            }
        }

        Pupil pupilList[] = pupilManagement.getPupilList();
        Classroom classroomList[] = classroomManagement.getClassroomManagement();

        for (int i = 0; i < pupilManagement.getCurrentIndex(); i++) {
            for (int j = 0; j < classroomManagement.getCurrentIndex(); j++) {
                if (pupilList[i].getClassroom().getClassName().equalsIgnoreCase(classroomList[j].getClassName())) {
                    pupilList[i].setClassroom(classroomList[j]);
                    break;
                }
            }
        }

        Parent parentList[] = parentManagement.getParentList();
        for (int i = 0; i < pupilManagement.getCurrentIndex(); i++) {
            for (int j = 0; j < parentManagement.getCurrentIndex(); j++) {
                if (pupilList[i].getPupilID().equalsIgnoreCase(parentList[j].getPupil().getPupilID())) {
                    pupilList[i].setParents(parentList[j]);
                    parentList[j].setPupil(pupilList[i]);
                    break;
                }
            }
        }

        Point listPoint[] = pointManagement.getListPoint();
        for (int i = 0; i < pupilManagement.getCurrentIndex(); i++) {
            for (int j = 0; j < pointManagement.getCurrentIndex(); j++) {
                if (pupilList[i].getPupilID().equalsIgnoreCase(listPoint[j].getPupil().getPupilID())) {
                    pupilList[i].setSubjectPoints(listPoint[i]);
                    listPoint[i].setPupil(pupilList[i]);
                    break;
                }
            }
        }

        for (int i = 1; i <= 5; i++) {
            Grade.setNumberOfPupilsInGrade(12, i);
        }
    }

    public static void pupilConnection(Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            }
        }

        Pupil pupilList[] = pupilManagement.getPupilList();
        int pupilListLength = pupilManagement.getCurrentIndex();

        if (parentManagement != null) {
            Parent parentList[] = parentManagement.getParentList();
            int parentListLength = parentManagement.getCurrentIndex();

            for (int i = 0; i < pupilListLength; i++) {
                for (int j = 0; j < parentListLength; j++) {
                    if (pupilList[i] != null) {
                        if (parentList[j] != null) {
                            if (pupilList[i].getPupilID().equalsIgnoreCase(parentList[j].getPupil().getPupilID())) {
                                pupilList[i].setParents(parentList[j]);
                                parentList[j].setPupil(pupilList[i]);
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (pointManagement != null) {
            Point pointList[] = pointManagement.getListPoint();
            int pointListLength = pointManagement.getCurrentIndex();

            for (int i = 0; i < pupilListLength; i++) {
                for (int j = 0; j < pointListLength; j++) {
                    if (pupilList[i] != null) {
                        if (pointList[j] != null) {
                            if (pupilList[i].getPupilID().equalsIgnoreCase(pointList[j].getPupil().getPupilID())) {
                                pupilList[i].setSubjectPoints(pointList[j]);
                                pointList[j].setPupil(pupilList[i]);
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    public static void classroomConnection(Object... managementObjects) {
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;
        // PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            }
        }

        Teacher teacherList[] = teacherManagement.getTeacherManagement();
        int teacherListLength = teacherManagement.getCurrentIndex();
        Classroom classroomList[] = classroomManagement.getClassroomManagement();
        int classroomListLength = classroomManagement.getCurrentIndex();

        for (int i = 0; i < teacherListLength; i++) {
            for (int j = 0; j < classroomListLength; j++) {
                if (teacherList[i].getTeacherID()
                        .equalsIgnoreCase(classroomList[j].getClassManager().getTeacherID())) {
                    teacherList[i].setClassroom(classroomList[j]);
                    classroomList[j].setClassManager(teacherList[i]);
                }
            }
        }
    }

    public static void appDisplay(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;
        ParentManagement parentManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            } else {
                parentManagement = (ParentManagement) managementObject;
            }

            // Add more else if blocks for other management objects
        }

        int option = -1;
        do {
            System.out.println("======================= Display data session =======================");
            System.out.println("1. Display pupils data");
            System.out.println("2. Display teachers data");
            System.out.println("3. Display parents data");
            System.out.println("4. Display points data");
            System.out.println("5. Display classrooms data");
            System.out.println("0. Exit");

            boolean isEmptyInput;
            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();
                isEmptyInput = input.isEmpty();

                if (!isEmptyInput) {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                } else {
                    System.out.println("Please enter your option!");
                }
            } while (isEmptyInput || option == -1);

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
                    pointManagement.display();
                    break;

                case 5:
                    classroomManagement.display();
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static void appCreate(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;
        PointManagement pointManagement = null;
        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            }
        }

        int option = -1;
        do {
            System.out.println("======================= Create data session =======================");
            System.out.println("1. Create pupils data");
            System.out.println("2. Create teachers data");
            System.out.println("3. Create parents data");
            System.out.println("4. Create points data");
            System.out.println("5. Create classrooms data");
            System.out.println("0. Exit");

            boolean isEmptyInput;
            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();
                isEmptyInput = input.isEmpty();

                if (!isEmptyInput) {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                } else {
                    System.out.println("Please enter your option!");
                }
            } while (isEmptyInput || option == -1);

            switch (option) {
                case 1:
                    addPupilsToPupilManagementList(sc, pupilManagement, classroomManagement);
                    break;

                case 2:
                    addTeachersToTeacherManagementList(sc, teacherManagement);
                    break;

                case 3:
                    addParentsToParentManagementList(sc, pupilManagement, parentManagement);
                    break;

                case 4:
                    addPointToPointManagementList(sc, pointManagement, pupilManagement);
                    break;

                case 5:
                    addClassroomsToClassroomManagementList(sc, classroomManagement, teacherManagement);
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static void appUpdate(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            } else {
                classroomManagement = (ClassroomManagement) managementObject;
            }
        }

        int option = -1;
        do {
            System.out.println("======================= Update data session =======================");
            System.out.println("1. Update pupils data");
            System.out.println("2. Update teachers data");
            System.out.println("3. Update parents data");
            System.out.println("4. Update points data");
            System.out.println("5. Update classroom data");
            System.out.println("0. Exit");

            boolean isEmptyInput;
            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();
                isEmptyInput = input.isEmpty();

                if (!isEmptyInput) {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                } else {
                    System.out.println("Please enter your option!");
                }
            } while (isEmptyInput || option == -1);

            switch (option) {
                case 1:
                    updatePupilData(sc, pupilManagement, classroomManagement, parentManagement);
                    break;

                case 2:
                    updateTeacherData(sc, teacherManagement);
                    break;

                case 3:
                    updateParentData(sc, pupilManagement, parentManagement);
                    break;

                case 4:
                    updatePointData(sc, pupilManagement, pointManagement);
                    break;

                case 5:
                    updateClassroomData(sc, classroomManagement, teacherManagement);
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static void appDelete(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            } else {
                classroomManagement = (ClassroomManagement) managementObject;
            }
        }

        int option = -1;
        do {
            System.out.println("======================= Delete data session =======================");
            System.out.println("1. Delete pupils data");
            System.out.println("2. Delete teachers data");
            System.out.println("3. Delete parents data");
            System.out.println("4. Delete points data");
            System.out.println("5. Delete classroom data");
            System.out.println("6. Recycle Bin");
            System.out.println("0. Exit");

            boolean isEmptyInput;
            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();
                isEmptyInput = input.isEmpty();

                if (!isEmptyInput) {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                } else {
                    System.out.println("Please enter your option!");
                }
            } while (isEmptyInput || option == -1);

            switch (option) {
                case 1:
                    deletePupilData(pupilManagement, sc);
                    break;

                case 2:
                    deleteTeacherData(teacherManagement, sc);
                    break;

                case 3:
                    deleteParentData(parentManagement, sc);
                    break;

                case 4:
                    deletePointData(pointManagement, sc);
                    break;

                case 5:
                    deleteClassroomData(classroomManagement, sc);
                    break;
                case 6:
                    Redux.displayRecycleBin(sc);
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static String createNewID(String IDPrefix, int IDSuffix) {
        String prefix = IDPrefix;
        int number = IDSuffix;

        number++;

        // Format it back into the original string format, %s for a string, %03d for a
        // number with 3 digits, and padding 0 before if a number has less than 3 digits
        String result = String.format("%s%03d", prefix, number);
        return result;
    }

    public static void addPupilsToPupilManagementList(Scanner scanner, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ClassroomManagement classroomManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            }

        }

        char option = 'x';
        System.out.println("Do you want to add new pupils ? Yes(Y/y) : No (N/n)");
        option = scanner.nextLine().charAt(0);
        while (option == 'y' || option == 'Y') {
            boolean flag;
            String fullName = "";
            System.out.println("Add pupils: ");
            do {
                System.out.print("Fullname (Format: Nguyen Duc Canh): ");
                fullName = scanner.nextLine();
                if (!fullName.isEmpty()) {
                    flag = Pupil.isValidName(fullName);

                    if (!flag) {
                        System.out.println("Fullname is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);

            String date = "";
            do {
                System.out.print("BirthDate (format: 03/03/2017): ");
                date = scanner.nextLine();
                if (!date.isEmpty()) {
                    flag = Date.isValidDateAndMonth(date);

                    if (!flag) {
                        System.out.println("BirthDate is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address (format: 03, Nguyen Van Troi, Phuong 5, Quan Binh Thanh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                if (!inputAddress.isEmpty()) {
                    flag = Address.isValidAddress(inputAddress);

                    if (!flag) {
                        System.out.println("Address is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);
            Address address = new Address(inputAddress);

            String gender = "";
            do {
                System.out.print("Gender (format: male / female): ");
                gender = scanner.nextLine();
                if (!gender.isEmpty()) {
                    flag = Pupil.isValidGender(gender);

                    if (!flag) {
                        System.out.println("Gender is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);

            ClassroomManagement.displayClassroomFormation();
            Classroom classroomList[] = classroomManagement.getClassroomManagement();
            Classroom classroom = null;
            String className = "";
            do {
                System.out.print("Class (format: 1A1): ");
                className = scanner.nextLine();
                if (!className.isEmpty()) {
                    for (int i = 0; i < classroomManagement.getCurrentIndex(); i++) {
                        if (classroomList[i].getClassName().equalsIgnoreCase(className)) {
                            flag = true;
                            classroom = classroomList[i];
                            break;
                        } else {
                            flag = false;
                        }
                    }
                } else {
                    flag = false;
                }

                if (!flag) {
                    System.out.println("Class is invalid (No class available)!");
                }
            } while (!flag);

            int gradeNumber = className.charAt(0) - '0';
            Grade.setNumberOfPupilsInGrade(Grade.getNumberOfPupilsInGrade()[gradeNumber - 1] + 1, gradeNumber);

            String pupilID = createNewID("HS", Integer.parseInt(pupilManagement.getLastPupilID().substring(2)));
            Pupil newPupil = new Pupil(pupilID, fullName, dob, address, gender, new Classroom(className));
            newPupil.setClassroom(classroom);
            pupilManagement.add(newPupil);

            String record = pupilID + "-" + fullName + "-" + date + "-" + inputAddress + "-" + className + "-" + gender;
            PupilManagement.insertIntoDatabase(record);

            System.out.println("Do you want to add more pupils ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        }
    }

    public static void updatePupilData(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ClassroomManagement classroomManagement = null;
        ParentManagement parentManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
        }

        char option = 'x';
        System.out.println("Do you want to update pupils data? Yes(Y/y) : No (N/n)");
        option = sc.nextLine().charAt(0);
        Pupil pupil = null;
        while (option == 'Y' || option == 'y') {
            System.out.print("Enter pupil's ID: ");
            String ID = sc.nextLine();
            pupil = pupilManagement.getPupilByID(ID);
            if (pupil != null) {
                boolean flag = true;
                String name = "";
                do {
                    System.out.println("Old Fullname: " + pupil.getFullname());
                    System.out.print("New Fullname (Format: Tran Duc Canh): ");
                    name = sc.nextLine();
                    if (!name.isEmpty()) {
                        flag = Pupil.isValidName(name);
                        if (flag) {
                            System.out.println("New Fullname: " + name + " confirmed!");
                        } else {
                            System.out.println("Fullname is invalid (Wrong format)!");
                        }
                    } else {
                        name = pupil.getFullname();
                        flag = true;
                    }
                } while (!flag);

                String birthDate = "";
                Date newDob = null;
                do {
                    System.out.println("Old BirthDate: " + pupil.getBirthDate());
                    System.out.print("New BirthDate (Format: 02/02/2017): ");
                    birthDate = sc.nextLine();

                    if (!birthDate.isEmpty()) {
                        flag = Date.isValidDateAndMonth(birthDate);
                        if (flag) {
                            newDob = new Date(birthDate);
                            System.out.println("New BirthDate: " + birthDate + " confirmed!");
                        } else {
                            System.out.println("BirthDate is invalid (Wrong format)!");
                        }

                    } else {
                        newDob = pupil.getBirthDate();
                        birthDate = pupil.getBirthDate().toString();
                        flag = true;
                    }
                } while (!flag);

                String gender = "";
                do {
                    System.out.println("Old Gender: " + pupil.getGender());
                    System.out.print("New Gender (Format: male / female): ");
                    gender = sc.nextLine();

                    if (!gender.isEmpty()) {
                        flag = Pupil.isValidGender(gender);
                        if (flag) {
                            System.out.println("New Gender: " + gender + " confirmed!");
                        } else {
                            System.out.println("Gender is invalid (Wrong format)!");
                        }
                    } else {
                        gender = pupil.getGender();
                        flag = true;
                    }
                } while (!flag);

                String address = "";
                Address newAddress = null;
                do {
                    System.out.println("Old Address: " + pupil.getAddress());
                    System.out
                            .print("New Address (Format: 66, Phan Van Tri, Phuong 9, Quan 3, Thanh pho Thu Duc): ");
                    address = sc.nextLine();
                    if (!address.isEmpty()) {
                        flag = Address.isValidAddress(address);
                        if (flag) {
                            newAddress = new Address(address);
                            System.out.println("New Address: " + address + " confirmed!");
                        } else {
                            System.out.println("Address is invalid (Wrong format)!");
                        }
                    } else {
                        newAddress = pupil.getAddress();
                        address = pupil.getAddress().toString();
                        flag = true;
                    }
                } while (!flag);

                ClassroomManagement.displayClassroomFormation();
                Classroom classroomList[] = classroomManagement.getClassroomManagement();
                Classroom classroom = null;
                String className = "";
                do {
                    System.out.println("Old Class: " + pupil.getClassroom().getClassName());
                    System.out.print("New Class (Format: 1A1): ");
                    className = sc.nextLine();
                    if (!className.isEmpty()) {
                        flag = classroomManagement.isValidClassroom(className);
                        if (flag) {
                            int newGradeNumber = className.charAt(0) - '0';
                            int oldGradeNumber = pupil.getClassroom().getClassName().charAt(0) - '0';

                            if (Math.abs(newGradeNumber - oldGradeNumber) > 0) {
                                Grade.setNumberOfPupilsInGrade(Grade.getNumberOfPupilsInGrade()[newGradeNumber - 1] + 1,
                                        newGradeNumber);
                                Grade.setNumberOfPupilsInGrade(Grade.getNumberOfPupilsInGrade()[oldGradeNumber - 1] - 1,
                                        oldGradeNumber);
                            } 

                            for (int i = 0; i < classroomManagement.getCurrentIndex(); i++) {
                                if (classroomList[i].getClassName().equalsIgnoreCase(className)) {
                                    classroom = classroomList[i];
                                    break;
                                }
                            }
                            System.out.println("New classroom: " + className + " confirmed!");
                        } else {
                            System.out.println("Classroom is invalid (Wrong format or Classroom not existed)!");
                        }
                    } else {
                        classroom = pupil.getClassroom();
                        className = pupil.getClassroom().getClassName();
                        flag = true;
                    }
                } while (!flag);

                Pupil updatedPupil = new Pupil(pupil.getPupilID(), name, newDob, newAddress, gender,
                        new Classroom(className));
                updatedPupil.setClassroom(classroom);
                pupilManagement.update(updatedPupil);
                pupilConnection(pupilManagement, parentManagement);

                String customizedAddress = updatedPupil.getAddress().toString().replace(" Duong ", " ");
                String record = pupil.getPupilID() + "-" + name + "-" + birthDate + "-" + customizedAddress + "-"
                        + className + "-"
                        + gender;
                PupilManagement.updateRecord(record);
                System.out.println("Update successfully!");
            } else {
                System.out.println("Pupil with ID: " + ID + " is not found!");
            }
            System.out.println("Do you want to update more pupils ? Yes(Y) : No(N)");
            option = sc.nextLine().charAt(0);
        }

    }

    public static void deletePupilData(PupilManagement pupilManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pupilManagement.delete(ID);
    }

    public static void addParentsToParentManagementList(Scanner scanner, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
        }

        if (pupilManagement.hasUninitializedPupil()) {
            char option = 'x';
            System.out.println("Do you want to add new parents ? Yes(Y/y) : No (N/n)");
            option = scanner.nextLine().charAt(0);
            while ((option == 'y' || option == 'Y') && pupilManagement.hasUninitializedPupil()) {
                boolean flag;
                String fullName = "";
                System.out.println("Add Parents: ");
                do {
                    System.out.print("Fullname (Format: Tao Minh Duc): ");
                    fullName = scanner.nextLine();
                    if (!fullName.isEmpty()) {
                        flag = Parent.isValidName(fullName);

                        if (!flag) {
                            System.out.println("Fullname is invalid (Wrong format)!");
                        }
                    } else {
                        flag = false;
                    }
                } while (!flag);

                String date = "";
                do {
                    System.out.print("BirthDate (format: 03/03/1976): ");
                    date = scanner.nextLine();
                    if (!date.isEmpty()) {
                        flag = Date.isValidDateAndMonth(date);

                        if (!flag) {
                            System.out.println("BirthDate is invalid (Wrong format)!");
                        }
                    } else {
                        flag = false;
                    }
                } while (!flag);
                Date dob = new Date(date);

                String inputAddress = "";
                do {
                    System.out.print(
                            "Address (format: 03, Nguyen Van Troi, Phuong 5, Quan Binh Thanh, Thanh pho Ho Chi Minh): ");
                    inputAddress = scanner.nextLine();
                    if (!inputAddress.isEmpty()) {
                        flag = Address.isValidAddress(inputAddress);

                        if (!flag) {
                            System.out.println("Address is invalid (Wrong format)!");
                        }
                    } else {
                        flag = false;
                    }
                } while (!flag);
                Address address = new Address(inputAddress);

                String phoneNumber = "";
                do {
                    System.out.print("Phone Number (format: 0907123456): ");
                    phoneNumber = scanner.nextLine();
                    if (!phoneNumber.isEmpty()) {
                        flag = Parent.isValidPhoneNumber(phoneNumber);

                        if (!flag) {
                            System.out.println("Phone Number is invalid (Wrong format)!");
                        }
                    } else {
                        flag = false;
                    }
                } while (!flag);

                String gender = "";
                do {
                    System.out.print("gender (format: male / female): ");
                    gender = scanner.nextLine();
                    if (!gender.isEmpty()) {
                        flag = Parent.isValidGender(gender);

                        if (!flag) {
                            System.out.println("gender is invalid (Wrong format)!");
                        }
                    } else {
                        flag = false;
                    }
                } while (!flag);

                String parentID = createNewID("PH", Integer.parseInt(parentManagement.getLastParentID().substring(2)));
                String pupilID = createNewID("HS", Integer.parseInt(parentManagement.getLastParentID().substring(2)));
                Parent newParent = new Parent(parentID, fullName, dob, address, gender, phoneNumber,
                        new Pupil(pupilID));
                parentManagement.add(newParent);

                String record = parentID + "-" + fullName + "-" + date + "-" + inputAddress + "-" + phoneNumber
                        + "-" + gender + "-" + newParent.getPupil().getPupilID();
                ParentManagement.insertIntoDatabase(record);
                AppHelper.pupilConnection(pupilManagement, parentManagement);

                System.out.println("Do you want to add more parents ? Yes(Y) : No(N)");
                option = scanner.nextLine().charAt(0);
            }
        } else {
            System.out.println("Cannot create parent data without creating a new pupil data first!");
        }
    }

    public static void updateParentData(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
        }

        char option = 'x';
        System.out.println("Do you want to update parents data? Yes(Y/y) : No (N/n)");
        option = sc.nextLine().charAt(0);
        Parent parent = null;
        while (option == 'Y' || option == 'y') {
            System.out.print("Enter parent's ID: ");
            String ID = sc.nextLine();
            parent = parentManagement.getParentByID(ID);
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
                            System.out.println("New Fullname: " + name + " confirmed!");
                        } else {
                            System.out.println("Fullname is invalid (Wrong format)!");
                        }
                    } else {
                        name = parent.getFullname();
                        flag = true;
                    }

                } while (!flag);

                String birthDate = "";
                Date newDob = null;
                do {
                    System.out.println("Old BirthDate: " + parent.getBirthDate());
                    System.out.print("New BirthDate (Format: 02/02/1976): ");
                    birthDate = sc.nextLine();

                    if (!birthDate.isEmpty()) {
                        flag = Date.isValidDateAndMonth(birthDate);
                        if (flag) {
                            newDob = new Date(birthDate);
                            System.out.println("New BirthDate: " + birthDate + " confirmed!");
                        } else {
                            System.out.println("BirthDate is invalid (Wrong format)!");
                        }

                    } else {
                        newDob = parent.getBirthDate();
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
                            System.out.println("New Gender: " + gender + " confirmed!");
                        } else {
                            System.out.println("gender is invalid (Wrong format)!");
                        }
                    } else {
                        gender = parent.getGender();
                        flag = true;
                    }
                } while (!flag);

                String address = "";
                Address newAddress = null;
                do {
                    System.out.println("Old Address: " + parent.getAddress());
                    System.out
                            .print("New Address (Format: 66, Phan Van Tri, Phuong 9, Quan 3, Thanh pho Thu Duc): ");
                    address = sc.nextLine();
                    if (!address.isEmpty()) {
                        flag = Address.isValidAddress(address);
                        if (flag) {
                            newAddress = new Address(address);
                            System.out.println("New Address: " + address + " confirmed!");
                        } else {
                            System.out.println("Address is invalid (Wrong format)!");
                        }
                    } else {
                        newAddress = parent.getAddress();
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
                            System.out.println("New Phone Number: " + phoneNumber + " confirmed!");
                        } else {
                            System.out.println("Phone Number is invalid (Wrong format)!");
                        }
                    } else {
                        phoneNumber = parent.getPhoneNumber();
                        flag = true;
                    }
                } while (!flag);

                Parent updatedParent = new Parent(parent.getParentID(), name, newDob, newAddress, gender, phoneNumber,
                        parent.getPupil());
                parentManagement.update(updatedParent);
                pupilConnection(pupilManagement, parentManagement);

                String customizedAddress = updatedParent.getAddress().toString().replace(" Duong ", " ");
                String record = parent.getParentID() + "-" + name + "-" + birthDate + "-" + customizedAddress
                        + "-" + phoneNumber + "-" + gender + "-" + parent.getPupil().getPupilID();
                ParentManagement.updateRecord(record);
                System.out.println("Update successfully!");

            } else {
                System.out.println("Parent with ID: " + ID + " is not found!");
            }
            System.out.println("Do you want to update parents data ? Yes(Y) : No(N)");
            option = sc.nextLine().charAt(0);
        }
    }

    public static void deleteParentData(ParentManagement parentManagement, Scanner scanner) {
        System.out.print("Enter parent ID: ");
        String ID = scanner.nextLine();
        parentManagement.delete(ID);
    }

    public static void addTeachersToTeacherManagementList(Scanner scanner, Object... managementObjects) {
        TeacherManagement teacherManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            }
        }

        char option = 'x';
        System.out.println("Do you want to add new teachers ? Yes(Y/y) : No (N/n)");
        option = scanner.nextLine().charAt(0);
        while (option == 'y' || option == 'Y') {
            boolean flag;
            String fullName = "";
            System.out.println("Add teachers: ");
            do {
                System.out.print("Fullname (Format: Tran Anh Khoi): ");
                fullName = scanner.nextLine();
                if (!fullName.isEmpty()) {
                    flag = Pupil.isValidName(fullName);

                    if (!flag) {
                        System.out.println("Fullname is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);

            String date = "";
            do {
                System.out.print("BirthDate: (format: 16/02/2000): ");
                date = scanner.nextLine();
                if (!date.isEmpty()) {
                    flag = Date.isValidDateAndMonth(date);

                    if (!flag) {
                        System.out.println("BirthDate is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address: (format: 18/29, Nguyen Van Hoan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                if (!inputAddress.isEmpty()) {
                    flag = Address.isValidAddress(inputAddress);

                    if (!flag) {
                        System.out.println("Address is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);
            Address address = new Address(inputAddress);

            String major = "";
            do {
                System.out.print("Major (Format: Math, Literature, English, PE): ");
                major = scanner.nextLine();
                if (!major.isEmpty()) {
                    flag = TeacherManagement.isValidMajor(major);

                    if (!flag) {
                        System.out.println("Major is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }

            } while (!flag);

            String gender = "";
            do {
                System.out.print("Gender (format: male / female): ");
                gender = scanner.nextLine();
                if (!gender.isEmpty()) {
                    flag = Teacher.isValidGender(gender);

                    if (!flag) {
                        System.out.println("Gender is invalid (Wrong format)!");
                    }
                } else {
                    flag = false;
                }
            } while (!flag);

            String teacherID = createNewID("GV", Integer.parseInt(teacherManagement.getLastTeacherID().substring(2)));
            teacherManagement.add(new Teacher(teacherID, fullName, dob, address, gender, major));
            String record = teacherID + "-" + fullName + "-" + date + "-" + inputAddress + "-"
                    + major + "-" + gender;
            TeacherManagement.insertIntoDatabase(record);

            System.out.println("Do you want to add more teachers ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        }

    }

    public static void updateTeacherData(Scanner sc, Object... managementObjects) {
        TeacherManagement teacherManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            }
        }

        char option = 'x';
        System.out.println("Do you want to update teachers data? Yes(Y/y) : No (N/n)");
        option = sc.nextLine().charAt(0);
        Teacher teacher = null;
        while (option == 'Y' || option == 'y') {
            System.out.print("Enter teacher's ID: ");
            String ID = sc.nextLine();
            teacher = teacherManagement.getTeacherByID(ID);

            if (teacher != null) {
                boolean flag = true;
                String name = "";
                do {
                    System.out.println("Old Fullname: " + teacher.getFullname());
                    System.out.print("New Fullname (Format: Tran Duy Sang): ");
                    name = sc.nextLine();
                    if (!name.isEmpty()) {
                        flag = Teacher.isValidName(name);
                        if (flag) {
                            System.out.println("New Fullname: " + name + " confirmed!");
                        } else {
                            System.out.println("Fullname is invalid (Wrong format)!");
                        }
                    } else {
                        name = teacher.getFullname();
                        flag = true;
                    }
                } while (!flag);

                String birthDate = "";
                Date newDob = null;
                do {
                    System.out.println("Old BirthDate: " + teacher.getBirthDate());
                    System.out.print("New BirthDate (Format: 16/02/2000): ");
                    birthDate = sc.nextLine();

                    if (!birthDate.isEmpty()) {
                        flag = Date.isValidDateAndMonth(birthDate);
                        if (flag) {
                            newDob = new Date(birthDate);
                            System.out.println("New BirthDate: " + birthDate + " confirmed!");
                        } else {
                            System.out.println("BirthDate is invalid (Wrong format)!");
                        }

                    } else {
                        newDob = teacher.getBirthDate();
                        birthDate = teacher.getBirthDate().toString();
                        flag = true;
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
                            System.out.println("New Gender: " + gender + " confirmed!");
                        } else {
                            System.out.println("Gender is invalid (Wrong format)!");
                        }
                    } else {
                        gender = teacher.getGender();
                        flag = true;
                    }
                } while (!flag);

                String address = "";
                Address newAddress = null;
                do {
                    System.out.println("Old Address: " + teacher.getAddress());
                    System.out.print(
                            "New Address (Format: 18/29, Nguyen Van Hoan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                    address = sc.nextLine();
                    if (!address.isEmpty()) {
                        flag = Address.isValidAddress(address);
                        if (flag) {
                            newAddress = new Address(address);
                            System.out.println("New Address: " + address + " confirmed!");
                        } else {
                            System.out.println("Address is invalid (Wrong format)!");
                        }
                    } else {
                        newAddress = teacher.getAddress();
                        address = teacher.getAddress().toString();
                        flag = true;
                    }
                } while (!flag);

                String major = "";
                do {
                    System.out.println("Old Major: " + teacher.getMajor());
                    System.out.print("New Major (Format: Math, Literature, English, PE): ");
                    major = sc.nextLine();

                    if (!major.isEmpty()) {
                        flag = TeacherManagement.isValidMajor(major);
                        if (flag) {
                            System.out.println("New Major: " + major + " confirmed!");
                        } else {
                            System.out.println("Major is invalid (Wrong format)!");
                        }
                    } else {
                        major = teacher.getMajor();
                        flag = true;
                    }
                } while (!flag);

                Teacher updatedTeacher = new Teacher(teacher.getTeacherID(), name, newDob, newAddress, gender, major);
                teacherManagement.update(updatedTeacher);

                String customizedAddress = updatedTeacher.getAddress().toString().replace(" Duong ", " ");
                String record = teacher.getTeacherID() + "-" + name + "-" + birthDate + "-" + customizedAddress + "-"
                        + major + "-" + gender;
                TeacherManagement.updateRecord(record);
                System.out.println("Update successfully!");
            } else {
                System.out.println("Teacher with ID: " + ID + " is not found!");
            }

            System.out.println("Do you want to update more teachers ? Yes(Y) : No(N)");
            option = sc.nextLine().charAt(0);
        }

    }

    public static void deleteTeacherData(TeacherManagement teacherManagement, Scanner scanner) {
        System.out.print("Enter teacher ID: ");
        String ID = scanner.nextLine();
        teacherManagement.delete(ID);
    }

    public static void appSearch(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            } else {
                classroomManagement = (ClassroomManagement) managementObject;
            }
        }

        int option = -1;
        do {
            System.out.println("======================= Menu =======================");
            System.out.println("1. Search pupils data by ID");
            System.out.println("2. Search pupils data by name");
            System.out.println("3. Search pupils data by class");
            System.out.println("4. Search classroom data by class name");
            System.out.println("5. Search teacher data by name");
            System.out.println("6. Search teacher data by class");
            System.out.println("7. Search parents data by pupil's name");
            System.out.println("8. Search Point data by Pupil's ID");
            System.out.println("0. Exit");

            boolean isEmptyInput;
            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();
                isEmptyInput = input.isEmpty();

                if (!isEmptyInput) {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                } else {
                    System.out.println("Please enter your option!");
                }
            } while (isEmptyInput || option == -1);

            String searchText = "";
            switch (option) {
                case 1:
                    System.out.println("Enter pupil's ID: ");
                    searchText = sc.nextLine();
                    pupilManagement.findPupilsBy(searchText, "getPupilID", Pupil.class, null, true);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    searchText = sc.nextLine();
                    pupilManagement.findPupilsBy(searchText, "getFullname", Pupil.class, null, false);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;

                case 3:
                    System.out.print("Enter ClassName: ");
                    searchText = sc.nextLine();
                    pupilManagement.findPupilsBy(searchText, "getClassName", Pupil.class, Classroom.class, false);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;
                case 4:
                    System.out.println("Enter ClassName:");
                    searchText = sc.nextLine();
                    classroomManagement.searchClassName(searchText);
                    classroomManagement.fileSearchList(classroomManagement.getSearchListLength());
                    break;
                case 5:
                    System.out.print("Enter name: ");
                    searchText = sc.nextLine();
                    teacherManagement.findTeachersBy(searchText, "getFullname", Teacher.class, null);
                    teacherManagement.display(teacherManagement.getSearchResultLength());
                    break;

                case 6:
                    System.out.print("Enter ClassName: ");
                    searchText = sc.nextLine();
                    teacherManagement.findTeachersBy(searchText, "getClassName", Teacher.class, Classroom.class);
                    teacherManagement.display(teacherManagement.getSearchResultLength());
                    break;

                case 7:
                    System.out.print("Enter Pupil Name: ");
                    searchText = sc.nextLine();
                    parentManagement.findParentsBy(searchText);
                    parentManagement.display(parentManagement.getSearchResultLength());
                    break;

                case 8:
                    System.out.print("Enter Pupil ID to search: ");
                    String pupilID = sc.nextLine();
                    pointManagement.findPointByPupilID(pupilID);
                    pointManagement.display(pointManagement.getSearchResultLength());
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static void appStatistics(Scanner sc, PupilManagement pupilManagement) {
        int option = -1;
        do {
            System.out.println("======================= Menu =======================");
            System.out.println("1. Statistics on the number of students by gender");
            System.out.println("2. Statistics on the number of students by grade");
            System.out.println("3. Statistics on the number of students by performances");
            System.out.println("0. Exit");

            boolean isEmptyInput;
            do {
                System.out.print("Your option: ");
                String input = sc.nextLine().trim();
                isEmptyInput = input.isEmpty();

                if (!isEmptyInput) {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        option = -1;
                    }
                } else {
                    System.out.println("Please enter your option!");
                }
            } while (isEmptyInput || option == -1);

            switch (option) {
                case 1:
                    Statistics.statisticsOnTheNumberOfPupilsByGender(pupilManagement);
                    break;

                case 2:
                    Statistics.statisticsOnTheNumberOfPupilsByGrade(pupilManagement);
                    break;

                case 3:
                    Statistics.statisticsOnTheNumberOfPupilsByPerformances(pupilManagement);
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static void setupClassroomManagement(Object... managementObjects) {
        ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            }
            // Add more else if blocks for other management objects
        }

        Classroom classroomList[] = classroomManagement.getClassroomManagement();
        Teacher teacherList[] = teacherManagement.getTeacherManagement();

        for (int i = 0; i < classroomManagement.getCurrentIndex(); i++) {
            for (int j = 0; j < teacherManagement.getCurrentIndex(); j++) {
                if (classroomList[i].getClassManager().getTeacherID().equalsIgnoreCase(teacherList[j].getTeacherID())) {
                    // A class will be managed by a teacher
                    classroomList[i].setClassManager(teacherList[j]);
                    teacherList[j].setClassroom(classroomList[i]);

                    // Every three classes will form a block and have a teacher managing that block
                    // and the teacher managing that block is the default
                    switch (classroomList[i].getGrade().getGradeNumber()) {
                        case 1:
                            classroomList[i].getGrade().setGradeManager(teacherList[2]); // GV003
                            break;

                        case 2:
                            classroomList[i].getGrade().setGradeManager(teacherList[3]); // GV004
                            break;

                        case 3:
                            classroomList[i].getGrade().setGradeManager(teacherList[7]); // GV008
                            break;

                        case 4:
                            classroomList[i].getGrade().setGradeManager(teacherList[10]); // GV011
                            break;

                        case 5:
                            classroomList[i].getGrade().setGradeManager(teacherList[13]); // GV014
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    }

    public static void addClassroomsToClassroomManagementList(Scanner sc, Object... managementObjects) {
        ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            }
        }
        if (teacherManagement.hasUninitializedClassroom()) {
            char option = 'x';
            System.out.println("Do you want to create classrooms data? Yes(Y/y) : No (N/n)");
            option = sc.nextLine().charAt(0);
            while ((option == 'y' || option == 'Y') && teacherManagement.hasUninitializedClassroom()) {
                System.out.println("Add classrooms: ");
                boolean flag;
                String className = "";
                do {
                    System.out.print("Classname (Format: 5A4): ");
                    className = sc.nextLine();
                    if (!className.isEmpty()) {
                        flag = classroomManagement.isValidNewClassroom(className);

                        if (!flag) {
                            System.out.println("Classname is invalid!");
                        }
                    } else {
                        flag = false;
                    }
                } while (!flag);

                String classManager = "null";
                Teacher classTeacher = new Teacher(classManager);

                int gradeNumber = className.charAt(0) - '0';

                String gradeManager = ClassroomManagement.getGradeManagerByGradeNumber(gradeNumber);

                Teacher gradeTeacher = new Teacher(gradeManager);
                Grade grade = new Grade(gradeNumber, gradeTeacher);

                classroomManagement.add(new Classroom(className, classTeacher, grade));
                classroomManagement.initializeClassroomFormation();

                String write = className + "-" + classManager + "-" + gradeNumber + "-" + gradeManager;
                ClassroomManagement.insertIntoDatabase(write);

                System.out.println("Add classroom successfully!");
                System.out.println("Do you want to add more classrooms ? Yes(Y) : No(N)");
                option = sc.nextLine().charAt(0);
            }
        } else {
            System.out.println("All teachers have already taught a class. Cannot add a new class.");
        }

    }

    public static void updateClassroomData(Scanner sc, Object... managementObjects) {
        ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            }
        }

        char option = 'x';
        System.out.println("Do you want to update classrooms data? Yes(Y/y) : No (N/n)");
        option = sc.nextLine().charAt(0);
        Classroom classroom = null;
        Teacher oldClassManager = null;
        Teacher newClassManager = null;
        Classroom updatedClassroom2 = null;
        boolean isTwoClassroomsAlreadyHadManager = false;
        String record2 = "";
        while (option == 'Y' || option == 'y') {
            System.out.print("Enter classname: ");
            String className = sc.nextLine();
            classroom = classroomManagement.getClassroomByID(className);

            if (classroom != null) {
                boolean flag = true;
                String newClassManagerID = "";
                do {
                    System.out.println("Old ClassManager: " + classroom.getClassManager().getTeacherID());
                    System.out.print("New ClassManager: (format: GV016): ");
                    newClassManagerID = sc.nextLine();

                    String managerRegex = "^(GV)[0][0-9][0-9]$";
                    Pattern pattern = Pattern.compile(managerRegex);
                    Matcher matcher = pattern.matcher(newClassManagerID);

                    if (!newClassManagerID.isEmpty()) {
                        if (matcher.matches()) {
                            flag = classroomManagement.isValidManager(newClassManagerID);
                            if (flag) {
                                newClassManager = teacherManagement.getTeacherByID(newClassManagerID);
                                oldClassManager = classroom.getClassManager();

                                // Case classroom has already had a manager but the entered teacher hasn't
                                // managed a class before
                                if (oldClassManager != null) {
                                    oldClassManager.setClassroom(null);
                                }

                                System.out.println("New class manager: " + newClassManagerID + " confirmed!");
                            } else {
                                isTwoClassroomsAlreadyHadManager = true;
                                // Swap the class manager of 2 classroom if the entered teacherID has already
                                // managed a class and the being updated classroom also has already had a
                                // manager
                                newClassManager = teacherManagement.getTeacherByID(newClassManagerID);
                                oldClassManager = classroom.getClassManager();
                                flag = true;

                                // update the classroom that we swap its class manager
                                updatedClassroom2 = new Classroom(
                                        classroomManagement.getClassroomByClassManagerID(newClassManagerID)
                                                .getClassName(),
                                        oldClassManager,
                                        classroomManagement.getClassroomByClassManagerID(newClassManagerID).getGrade());

                                record2 = classroomManagement.getClassroomByClassManagerID(newClassManagerID)
                                        .getClassName()
                                        + "-" + oldClassManager.getTeacherID() + "-"
                                        + classroomManagement.getClassroomByClassManagerID(newClassManagerID).getGrade()
                                                .getGradeNumber()
                                        + "-"
                                        + classroomManagement.getClassroomByClassManagerID(newClassManagerID).getGrade()
                                                .getGradeManager().getTeacherID();
                            }
                        } else {
                            flag = false;
                            System.out.println("Class manager is invalid!");
                        }
                    } else {
                        newClassManager = teacherManagement.getTeacherByID(className);
                        newClassManagerID = classroom.getClassManager().getTeacherID();
                        flag = true;
                    }
                } while (!flag);

                // update the current on update classroom
                Classroom updatedClassroom1 = new Classroom(classroom.getClassName(), newClassManager,
                        classroom.getGrade());
                classroomManagement.update(updatedClassroom1);

                String record1 = classroom.getClassName() + "-" + newClassManagerID + "-"
                        + classroom.getGrade().getGradeNumber() + "-"
                        + classroom.getGrade().getGradeManager().getTeacherID();
                ClassroomManagement.updateRecord(record1);

                if (!isTwoClassroomsAlreadyHadManager) {
                    classroomManagement.update(updatedClassroom1);
                    ClassroomManagement.updateRecord(record1);
                } else {
                    classroomManagement.update(updatedClassroom1);
                    ClassroomManagement.updateRecord(record1);
                    classroomManagement.update(updatedClassroom2);
                    ClassroomManagement.updateRecord(record2);
                }

                classroomConnection(classroomManagement, teacherManagement);
                System.out.println("Update successfully!");
            } else {
                System.out.println("Classroom with className: " + className + " is not found!");
            }
            System.out.println("Do you want to update more classes ? Yes(Y) : No(N)");
            option = sc.nextLine().charAt(0);
        }
    }

    public static void deleteClassroomData(ClassroomManagement classroomManagement, Scanner sc) {
        System.out.print("Enter class name: ");
        String ID = sc.nextLine();
        classroomManagement.delete(ID);

    }

    public static void addPointToPointManagementList(Scanner scanner, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            }

        }

        if (pupilManagement.hasUninitializedPupil()) {
            char option = 'x';
            System.out.println("Do you want to add new points ? Yes(Y/y) : No (N/n)");
            option = scanner.nextLine().charAt(0);
            while ((option == 'y' || option == 'Y') && pupilManagement.hasUninitializedPupil()) {
                boolean flag;
                double literaturePoint, mathPoint, physicalEducationPoint, englishPoint;
                System.out.println("Add Point: ");
                do {
                    System.out.print("Literature Point (0-10): ");
                    literaturePoint = Double.parseDouble(scanner.nextLine());
                    flag = Point.isPoint(literaturePoint);
                    if (!flag) {
                        System.out.println("Point is invalid!");
                    }
                } while (!flag);

                do {
                    System.out.print("Math Point (0-10): ");
                    mathPoint = Double.parseDouble(scanner.nextLine());
                    flag = Point.isPoint(mathPoint);
                    if (!flag) {
                        System.out.println("Point is invalid!");
                    }
                } while (!flag);

                do {
                    System.out.print("PhysicalEducation Point (0-10): ");
                    physicalEducationPoint = Double.parseDouble(scanner.nextLine());
                    flag = Point.isPoint(physicalEducationPoint);
                    if (!flag) {
                        System.out.println("Point is invalid!");
                    }
                } while (!flag);

                do {
                    System.out.print("English Point (0-10): ");
                    englishPoint = Double.parseDouble(scanner.nextLine());
                    flag = Point.isPoint(englishPoint);
                    if (!flag) {
                        System.out.println("Point is invalid!");
                    }
                } while (!flag);

                int conductValue;
                do {
                    System.out.print("Conduct Point (0 - 100): ");
                    conductValue = Integer.parseInt(scanner.nextLine());
                } while (conductValue < 0 || conductValue > 100);

                // Create a new point ID using some logic (createNewID method not provided)
                String pointID = createNewID("PO", Integer.parseInt(pointManagement.getLastPointID().substring(2)));
                String pupilID = createNewID("HS", Integer.parseInt(pointManagement.getLastPointID().substring(2)));
                Point newPoint = new Point(pointID, literaturePoint, mathPoint, physicalEducationPoint, englishPoint,
                        new Conduct(conductValue), new Pupil(pupilID));
                pointManagement.add(newPoint);

                // Use the record string and insert into the database
                String record = pointID + "-" + literaturePoint + "-" + mathPoint + "-" + physicalEducationPoint + "-"
                        + englishPoint + "-" + conductValue + "-" + newPoint.getPupil().getPupilID();
                PointManagement.insertIntoDatabase(record);
                AppHelper.pupilConnection(pupilManagement, pointManagement);

                System.out.println("Do you want to add more points ? Yes(Y) : No(N)");
                option = scanner.nextLine().charAt(0);
            }
        } else {
            System.out.println("Cannot create point data without creating a new pupil data first!");
        }
    }

    public static void updatePointData(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
            }
        }

        char option = 'x';
        System.out.println("Do you want to update points data? Yes(Y/y) : No (N/n)");
        option = sc.nextLine().charAt(0);
        Point point = null;
        while (option == 'Y' || option == 'y') {
            System.out.print("Enter pointID: ");
            String ID = sc.nextLine();
            point = pointManagement.getPointByID(ID);
            if (point != null) {
                boolean flag = true;
                Double literaturePoint = 0.0;
                do {
                    System.out.println("Old LiteraturePoint: " + point.getLiteraturePoint());
                    System.out.print("New LiteraturePoint (0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        literaturePoint = Double.parseDouble(input);
                        flag = Point.isPoint(literaturePoint);
                        if (flag) {
                            System.out.println("New LiteraturePoint: " + literaturePoint + " confirmed!");
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        literaturePoint = point.getLiteraturePoint();
                        flag = true;
                    }
                } while (!flag);

                Double mathPoint = 0.0;
                do {
                    System.out.println("Old MathPoint: " + point.getMathPoint());
                    System.out.print("New MathPoint (0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        mathPoint = Double.parseDouble(input);
                        flag = Point.isPoint(mathPoint);
                        if (flag) {
                            System.out.println("New MathPoint: " + mathPoint + " confirmed!");
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        mathPoint = point.getMathPoint();
                        flag = true;
                    }
                } while (!flag);

                Double physicalEducationPoint = 0.0;
                do {
                    System.out.println("Old PhysicalEducationPoint: " + point.getPhysicalEducationPoint());
                    System.out.print("New PhysicalEducationPoint (0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        physicalEducationPoint = Double.parseDouble(input);
                        flag = Point.isPoint(physicalEducationPoint);
                        if (flag) {
                            System.out.println("New PhysicalEducationPoint: " + physicalEducationPoint + " confirmed!");
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        physicalEducationPoint = point.getPhysicalEducationPoint();
                        flag = true;
                    }
                } while (!flag);

                Double englishPoint = 0.0;
                do {
                    System.out.println("Old EnglishPoint: " + point.getEnglishPoint());
                    System.out.print("New EnglishPoint (0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        englishPoint = Double.parseDouble(input);
                        flag = Point.isPoint(englishPoint);
                        if (flag) {
                            System.out.println("New EnglishPoint: " + englishPoint + " confirmed!");
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        englishPoint = point.getEnglishPoint();
                        flag = true;
                    }
                } while (!flag);

                String conductInput = "";
                int conductPoint = 0;
                Conduct conduct = null;
                do {
                    System.out.println("Old ConductPoint: " + point.getConduct().getPointConduct());
                    System.out.print("New ConductPoint (0-100): ");
                    conductInput = sc.nextLine().trim();

                    if (!conductInput.isEmpty()) {
                        try {
                            conductPoint = Integer.parseInt(conductInput);
                            if (conductPoint >= 0 && conductPoint <= 100) {
                                conduct = new Conduct(conductPoint);
                                System.out.println("New ConductPoint: " + conductPoint + " confirmed!");
                            } else {
                                System.out.println("Invalid input. Please enter an integer value between 0 and 100.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter an integer value.");
                        }
                    } else {
                        conduct = point.getConduct();
                        conductPoint = point.getConduct().getPointConduct();
                        flag = true;
                    }
                } while (!flag);

                Point updatedPoint = new Point(point.getPointID(), literaturePoint, mathPoint, physicalEducationPoint,
                        englishPoint, conduct, point.getPupil());
                pointManagement.update(updatedPoint);
                pointManagement.calculateRank(updatedPoint);
                updatedPoint.calculatePerformance();
                pupilConnection(pupilManagement, pointManagement);

                // Update record
                String record = point.getPointID() + "-" + literaturePoint + "-" + mathPoint + "-"
                        + physicalEducationPoint + "-"
                        + englishPoint + "-" + conductInput + "-" + point.getPupil().getPupilID();
                PointManagement.updateRecord(record);
                System.out.println("Update successfully!");
            } else {
                System.out.println("Point ID " + ID + " not found.");
            }
            System.out.println("Do you want to update more points ? Yes(Y) : No(N)");
            option = sc.nextLine().charAt(0);
        }
    }

    public static void deletePointData(PointManagement pointManagement, Scanner scanner) {
        System.out.print("Enter point ID: ");
        String ID = scanner.nextLine();
        pointManagement.delete(ID);
    }
}