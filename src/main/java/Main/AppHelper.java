package Main;

import java.util.Scanner;

import Classes.Classroom.ClassroomManagement;
import Classes.Parents.Parent;
import Classes.Parents.ParentManagement;
import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;
import Classes.Classroom.Classroom;
import Classes.Classroom.Grade;

public class AppHelper {
    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        ParentManagement parentManagement = new ParentManagement();
        TeacherManagement teacherManagement = new TeacherManagement();
        ClassroomManagement classroomManagement = new ClassroomManagement();
        Scanner sc = new Scanner(System.in);
        int option = -1;
        boolean isCloseApp = false;

        do {
            while (option != 0 && !Redux.isLoggedIn && !isCloseApp) {
                System.out.println("Have you got an account yet ? Yes(Y/y) : No(N/n) : Close App(X/x)");
                char opt = sc.nextLine().charAt(0);
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
                System.out.println("========================== Menu ==========================");
                System.out.println("Please select: ");
                System.out.println("1. Initialize data");
                System.out.println("2. Print out data");
                if (Redux.isAdmin) {
                    System.out.println("3. Adding 1 or n person to");
                    System.out.println("4. Update person information");
                    System.out.println("5. Delete person");
                }
                System.out.println("6. Searching for the person information");
                if (Redux.isAdmin) {
                    System.out.println("7. Statistics");
                }
                System.out.println("8. Logout");
                System.out.println("0. Exit");
            }

            System.out.print("Your option: ");
            option = !isCloseApp || (option == 1) ? Integer.parseInt(sc.nextLine()) : 0;
            switch (option) {
                case 1:
                    appInitialize(pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    break;
                case 2:
                    appDisplay(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    break;
                case 3:
                    if (Redux.isAdmin) {
                        appCreate(sc, pupilManagement, teacherManagement, parentManagement, classroomManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 4:
                    if (Redux.isAdmin) {
                        appUpdate(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 5:
                    if (Redux.isAdmin) {
                        appDelete(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    } else {
                        System.out.println("Permission denied. You are not an admin.");
                    }
                    break;
                case 7:
                    // Case 7 logic
                    break;
                case 6:
                    appSearch(sc, pupilManagement, classroomManagement, teacherManagement, parentManagement);
                    break;
                case 8:
                    Redux.isLoggedIn = false;
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
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
                parentManagement.initialize();
            }
            // Add more else if blocks for other management objects
        }
        setupPupilManagement(pupilManagement, classroomManagement, parentManagement);
        System.out.println("App is now initialized!");
    }

    public static void setupPupilManagement(Object... managementObjects) {
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
            // Add more else if blocks for other management objects
        }

        int count = 0;
        int classroomIndex = 0;
        Pupil pupilList[] = pupilManagement.getPupilList();
        Classroom classroomList[] = classroomManagement.getClassroomManagement();
        Parent parentList[] = parentManagement.getParentList();

        for (int i = 0; i < pupilManagement.getCurrentIndex(); i++) {
            pupilList[i].setClassroom(classroomList[classroomIndex]);
            pupilList[i].setParents(parentList[i]);
            parentList[i].setPupil(pupilList[i]);
            count++;

            if (count == 4) {
                count = 0;
                classroomIndex++;
            }
        }
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
            } else if (managementObject instanceof ParentManagement) {
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

    public static void appCreate(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            }
        }

        int option = 0;
        do {
            System.out.println("======================= Create data session =======================");
            System.out.println("1. Create pupils data");
            System.out.println("2. Create teachers data");
            System.out.println("3. Create parents data");
            System.out.println("4. Create points data");
            System.out.println("5. Create classrooms data");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    addPupilsToPupilManagementList(sc, pupilManagement, classroomManagement);
                    break;

                case 2:
                    addTeachersToTeacherManagementList(teacherManagement, sc);
                    break;

                case 3:
                    addParentsToParentManagementList(parentManagement, sc);
                    break;

                case 4:

                    break;

                case 5:
                    addClassroomsToClassroomManagementList(classroomManagement, sc);
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

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
        }

        int option = 0;
        do {
            System.out.println("======================= Create data session =======================");
            System.out.println("1. Update pupils data");
            System.out.println("2. Update teachers data");
            System.out.println("3. Update parents data");
            System.out.println("4. Update points data");
            System.out.println("5. Update classroom data");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    updatePupilData(pupilManagement, sc);
                    break;

                case 2:
                    updateTeacherData(teacherManagement, sc);
                    break;

                case 3:
                    updateParentData(parentManagement, sc);
                    break;

                case 4:

                    break;

                case 5:
                    updateClassroomData(classroomManagement, sc);
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

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
        }

        int option = 0;
        do {
            System.out.println("======================= Create data session =======================");
            System.out.println("1. Delete pupils data");
            System.out.println("2. Delete teachers data");
            System.out.println("3. Delete parents data");
            System.out.println("4. Delete points data");
            System.out.println("5. Delete classroom data");
            System.out.println("6. Recycle Bin");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
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

    public static String createNewID(String lastID) {
        String prefix = lastID.substring(0, 2);
        int number = Integer.parseInt(lastID.substring(2));

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

        char option = 'y';
        do {
            boolean flag;
            String fullName = "";
            System.out.println("Add pupils: ");
            do {
                System.out.print("Fullname (Format: Nguyen Duc Canh): ");
                fullName = scanner.nextLine();
                flag = Pupil.isValidName(fullName);

                if (!flag) {
                    System.out.println("Fullname is invalid (Wrong format)!");
                }

            } while (!flag);

            String date = "";
            do {
                System.out.print("BirthDate (format: 03/03/2017): ");
                date = scanner.nextLine();
                flag = Date.isValidDateAndMonth(date);

                if (!flag) {
                    System.out.println("BirthDate is invalid (Wrong format)!");
                }
            } while (!flag);
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address (format: 03, Nguyen Van Troi, Phuong 5, Quan Binh Thanh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                flag = Address.isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!flag);
            Address address = new Address(inputAddress);

            String sex = "";
            do {
                System.out.print("Sex (format: male / female): ");
                sex = scanner.nextLine();
                flag = Pupil.isValidSex(sex);

                if (!flag) {
                    System.out.println("Sex is invalid (Wrong format)!");
                }
            } while (!flag);

            ClassroomManagement.displayClassroomFormation();
            String className = "";
            do {
                System.out.print("Class (format: 1A1): ");
                className = scanner.nextLine();
                flag = ClassroomManagement.isValidClassroom(className);

                if (!flag) {
                    System.out.println("Class is invalid (No class available)!");
                }
            } while (!flag);
            int gradeNumber = className.charAt(0) - '0';
            Grade grade = new Grade(gradeNumber);
            Classroom classroom = new Classroom(className, grade);

            String pupilID = createNewID(pupilManagement.getLastPupilID());
            pupilManagement.add(new Pupil(pupilID, fullName, dob, address, sex, classroom));

            String record = pupilID + "-" + fullName + "-" + date + "-" + inputAddress + "-" + className + "-" + sex;
            pupilManagement.insertIntoDatabase(record);

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

    public static void addParentsToParentManagementList(ParentManagement parentManagement, Scanner scanner) {
        char option = 'y';
        do {
            boolean flag;
            String fullName = "";
            System.out.println("Add Parents: ");
            do {
                System.out.print("Fullname (Format: Nguyen Duc Canh): ");
                fullName = scanner.nextLine();
                flag = Pupil.isValidName(fullName);

                if (!flag) {
                    System.out.println("Fullname is invalid (Wrong format)!");
                }

            } while (!flag);

            String date = "";
            do {
                System.out.print("BirthDate (format: 03/03/2017): ");
                date = scanner.nextLine();
                flag = Date.isValidDateAndMonth(date);

                if (!flag) {
                    System.out.println("BirthDate is invalid (Wrong format)!");
                }
            } while (!flag);
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address (format: 03, Nguyen Van Troi, Phuong 5, Quan Binh Thanh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                flag = Address.isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!flag);
            Address address = new Address(inputAddress);

            String sex = "";
            do {
                System.out.print("Sex (format: male / female): ");
                sex = scanner.nextLine();
                flag = Parent.isValidSex(sex);

                if (!flag) {
                    System.out.println("Sex is invalid (Wrong format)!");
                }
            } while (!flag);

            String parentID = createNewID(parentManagement.getLastParentID());
            parentManagement.add(new Parent(parentID, fullName, dob, address, sex));

            String record = parentID + "-" + fullName + "-" + date + "-" + inputAddress + "-" + "-" + sex;
            parentManagement.insertIntoDatabase(record);

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
                boolean flag = Date.isValidDateAndMonth(date);

                if (!flag) {
                    System.out.println("BirthDate is invalid (Wrong format)!");
                }
            } while (!Date.isValidDateAndMonth(date));
            Date dob = new Date(date);

            String inputAddress = "";
            do {
                System.out.print(
                        "Address: (format: 18/29, Nguyen Van Hoan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                boolean flag = Address.isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!Address.isValidAddress(inputAddress));
            Address address = new Address(inputAddress);

            String teacherID = createNewID(teacherManagement.getLastTeacherID());
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

    public static void appSearch(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
        }

        int option = 0;
        do {
            System.out.println("======================= Menu =======================");
            System.out.println("1. Search pupils data by name");
            System.out.println("2. Search pupils data by class");
            System.out.println("3. Search parents data by name");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    pupilManagement.findPupilsBy(name, "getFullname", Pupil.class, null);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;

                case 2:
                    System.out.print("Enter ClassName: ");
                    String className = sc.nextLine();
                    pupilManagement.findPupilsBy(className, "getClassName", Pupil.class, Classroom.class);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;
                case 3:
                    System.out.print("Enter Parent Name: ");
                    String parentName = sc.nextLine();
                    parentManagement.findParentsBy(parentName, "getFullName", Parent.class, null);
                    parentManagement.display(parentManagement.getSearchResultLength());
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    public static void addClassroomsToClassroomManagementList(ClassroomManagement classroomManagement,
            Scanner scanner) {
        char option = 'y';
        System.out.println("Add classrooms: ");
        do {
            System.out.print("ClassName (Format: 5A4): ");
            String className = scanner.nextLine();

            System.out.print("ClassManagerID (Format: GV016): ");
            String classManagerID = scanner.nextLine();

            System.out.print("GradeNumber (Format: 5): ");
            int gradeNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("GradeManagerID (Format: GV016): ");
            String gradeManagerID = scanner.nextLine();

            Grade grade = new Grade(gradeNumber, null);
            classroomManagement.add(new Classroom(className, grade));

            classroomManagement.classroomFormationInitialize();

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
