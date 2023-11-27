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
        setupClassroomManagement(classroomManagement, teacherManagement);
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
                    addTeachersToTeacherManagementList(sc, teacherManagement);
                    break;

                case 3:
                    addParentsToParentManagementList(parentManagement, sc);
                case 4:

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
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
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
                flag = Pupil.isValidGender(sex);

                if (!flag) {
                    System.out.println("gender is invalid (Wrong format)!");
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
                flag = Parent.isValidName(fullName);

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

            String phoneNumber = "";
            do {
                System.out.print("Phone Number : (format: 0907xxxxxx): ");
                phoneNumber = scanner.nextLine();
                flag = Parent.isValidPhoneNumber(phoneNumber);

                if (!flag) {
                    System.out.println("Phone Number is invalid (Wrong format)!");
                }
            } while (!flag);

            String gender = "";
            do {
                System.out.print("gender (format: male / female): ");
                gender = scanner.nextLine();
                flag = Parent.isValidGender(gender);

                if (!flag) {
                    System.out.println("gender is invalid (Wrong format)!");
                }
            } while (!flag);

            String parentID = createNewID(parentManagement.getLastParentID());
            parentManagement.add(new Parent(parentID, fullName, dob, address, gender, phoneNumber));

            String record = parentID + "-" + fullName + "-" + date + "-" + inputAddress + "-" + "-" + phoneNumber + "-"
                    + "-"
                    + gender;
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

    public static void addTeachersToTeacherManagementList(Scanner scanner, Object... managementObjects) {

        TeacherManagement teacherManagement = null;
        ClassroomManagement classroomManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            }
        }

        char option = 'y';
        do {
            boolean flag;
            String fullName = "";
            System.out.println("Add teachers: ");
            do {
                System.out.print("Fullname (Format: Tran Le Anh Khoi): ");
                fullName = scanner.nextLine();
                flag = Teacher.isValidName(fullName);

                if (!flag) {
                    System.out.println("Fullname is invalid (Wrong format)!");
                }

            } while (!flag);

            String date = "";
            do {
                System.out.print("BirthDate: (format: 16/02/2000): ");
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
                        "Address: (format: 18/29, Nguyen Van Hoan, Phuong 9, Quan Tan Binh, Thanh pho Ho Chi Minh): ");
                inputAddress = scanner.nextLine();
                flag = Address.isValidAddress(inputAddress);

                if (!flag) {
                    System.out.println("Address is invalid (Wrong format)!");
                }
            } while (!flag);
            Address address = new Address(inputAddress);

            String major = "";
            do {
                System.out.print("Major (Format: Math, Literature, English, PE): ");
                major = scanner.nextLine();
                flag = TeacherManagement.isValidMajor(major);

                if (!flag) {
                    System.out.println("Major is invalid (Wrong format)!");
                }

            } while (!flag);

            String gender = "";
            do {
                System.out.print("Gender (format: male / female): ");
                gender = scanner.nextLine();
                flag = Teacher.isValidGender(gender);

                if (!flag) {
                    System.out.println("Gender is invalid (Wrong format)!");
                }
            } while (!flag);

            String teacherID = createNewID(teacherManagement.getLastTeacherID());
            teacherManagement.add(new Teacher(teacherID, fullName, dob, address, gender, major));
            String record = teacherID + "-" + fullName + "-" + date + "-" + inputAddress + "-"
                    + major + "-" + "null" + "-" + gender;
            teacherManagement.insertIntoDatabase(record);

            System.out.println("Do you want to add more teacher ? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');

    }

    public static void updateTeacherData(TeacherManagement teacherManagement, Scanner scanner) {
        System.out.print("Enter teacher ID: ");
        String ID = scanner.nextLine();
        teacherManagement.update(ID);
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
        String name;
        do {
            System.out.println("======================= Menu =======================");
            System.out.println("1. Search pupils data by name");
            System.out.println("2. Search pupils data by class");
            System.out.println("3. Search classroom data by class name");
            System.out.println("4. Search parents data by name");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    name = sc.nextLine();
                    pupilManagement.findPupilsBy(name, "getFullname", Pupil.class, null);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;

                case 2:
                    System.out.print("Enter ClassName: ");
                    name = sc.nextLine(); // Reusing the existing variable
                    pupilManagement.findPupilsBy(name, "getClassName", Pupil.class, Classroom.class);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;
                case 3:
                    System.out.println("Enter ClassName:");
                    String classname = sc.nextLine();
                    classroomManagement.searchClassName(classname);
                    classroomManagement.fileSearchList(classroomManagement.getSearchListLength());
                    break;
                case 4:
                    System.out.print("Enter Parent Name: ");
                    name = sc.nextLine();
                    parentManagement.findParentsBy(name);
                    parentManagement.display(parentManagement.getSearchResult(),
                            parentManagement.getSearchResultLength());
                    break;

                default:

                    break;
            }
        } while (option != 0);
    }

    // AppHelper Classroom Management
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

        int teacherIndex = 0;
        Classroom classroomList[] = classroomManagement.getClassroomManagement();
        Teacher teacherList[] = teacherManagement.getTeacherManagement();

        for (int i = 0; i < classroomManagement.getCurrentIndex(); i++) {
            // A class will be managed by a teacher
            classroomList[i].setClassManager(teacherList[teacherIndex]);
            teacherList[i].setClassroom(classroomList[i]);
            teacherIndex++;

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
            char option = 'y';
            do {
                System.out.println("Add classrooms: ");
                boolean flag;
                String className = "";
                do {
                    System.out.print("Classname (Format: 5A4): ");
                    className = sc.nextLine();
                    flag = classroomManagement.isValidClassroom(className);

                    if (!flag) {
                        System.out.println("Classname is invalid!");
                    }

                } while (!flag);

                String classManager = null;
                Teacher classTeacher = new Teacher(classManager);

                int gradeNumber = className.charAt(0) - '0';

                String gradeManager = classroomManagement.getGradeManagerByGradeNumber(gradeNumber);

                Teacher gradeTeacher = new Teacher(gradeManager);
                Grade grade = new Grade(gradeNumber, gradeTeacher);

                classroomManagement.add(new Classroom(className, classTeacher, grade));
                classroomManagement.classroomFormationInitialize();

                String write = className + "-" + classManager + "-" + gradeNumber + "-" + gradeManager;
                classroomManagement.insertIntoDatabase(write);

                System.out.println("Add classroom successfully!");
                System.out.println("Do you want to add more classrooms ? Yes(Y) : No(N)");
                option = sc.nextLine().charAt(0);
            } while (option == 'y' || option == 'Y');
        } else {
            System.out.println("All teachers have already taught a class. Cannot add a new class.");
        }

    }

    public static void updateClassroomData(ClassroomManagement classroomManagement, Scanner sc) {
        System.out.print("Enter class name: ");
        String ID = sc.nextLine();
        classroomManagement.update(ID);
    }

    public static void deleteClassroomData(ClassroomManagement classroomManagement, Scanner sc) {
        System.out.print("Enter class name: ");
        String ID = sc.nextLine();
        classroomManagement.delete(ID);
    }
}
