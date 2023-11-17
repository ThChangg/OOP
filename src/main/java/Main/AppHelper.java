package Main;

import java.util.Scanner;

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Points.Conduct;
import Classes.Points.Point;
import Classes.Points.PointManagement;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;

public class AppHelper {
    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        // ParentManagement parentManagement = new ParentManagement();
        TeacherManagement teacherManagement = new TeacherManagement();
        // ClassroomManagement classroomManagement = new ClassroomManagement();
        PointManagement pointManagement = new PointManagement();
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
                    appInitialize(pupilManagement, /*classroomManagement,*/ teacherManagement, pointManagement);
                    break;
                case 2:
                    appDisplay(sc, pupilManagement,/* classroomManagement,*/  teacherManagement, pointManagement);
                    break;
                case 3:
                    // addPupilsToPupilManagementList(pupilManagement, sc);
                    // addClassroomsToClassroomManagementList(classroomManagement, sc);
                    addPointToPointManagementList(pointManagement, sc);

                    break;
                case 4:
                    // updatePupilData(pupilManagement, sc);
                    // updateClassroomData(classroomManagement, sc);
                    updatePointData(pointManagement, sc);
                    break;
                case 5:
                    // deletePupilData(pupilManagement, sc);
                    // deleteClassroomData(classroomManagement, sc);
                    deletePointData(pointManagement, sc);
                    break;
                case 6:
                    searchPointData(pointManagement, sc);
                    // searchPupilData(pupilManagement, sc);
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
        PupilManagement pupilManagement = null;
        // ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;
        // ParentManagement parentManagement = null;
        PointManagement pointManagement=null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
                pupilManagement.initialize();
            } /*else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
                classroomManagement.initialize();
            } */else if (managementObject instanceof TeacherManagement) {
                ((TeacherManagement) managementObject).initialize();
            } else if (managementObject instanceof PointManagement) {
                ((PointManagement) managementObject).initialize();
            }
            // Add more else if blocks for other management objects
            System.out.println("App is now initialized!");
        }
        System.out.println("App is now initialized!");
    }

    public static void setupPupilManagement(Object... managementObjects) {
        PupilManagement pupilManagement = null;
        // ClassroomManagement classroomManagement = null;
        // ParentManagement parentManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } /*else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }*/
            // Add more else if blocks for other management objects
        }

        int count = 0;
        int classroomIndex = 0;
        Pupil pupilList[] = pupilManagement.getPupilList();
        // Classroom classroomList[] = classroomManagement.getClassroomManagement();
        // Parent parentList[] = parentManagement.getParentManagement();
        
        // for (int i = 0; i < pupilManagement.getCurrentIndex(); i++) {
        //     pupilList[i].setClassroom(classroomList[classroomIndex]);
        //     pupilList[i].setParents(parentList[i]);
        //     count++;

        //     if (count == 4) {
        //         count = 0;
        //         classroomIndex++;
        //     }
        // }
    }

    public static void appDisplay(Scanner sc, Object... managementObjects) {
        PupilManagement pupilManagement = null;
        // ClassroomManagement classroomManagement = null;
        TeacherManagement teacherManagement = null;
        PointManagement pointManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            }/*  else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            } */else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } else if (managementObject instanceof PointManagement) {
                pointManagement = (PointManagement) managementObject;
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
                    // parentManagement.display();
                    break;

                case 4:
                    pointManagement.display();
                    break;

                case 5:
                    // classroomManagement.display();
                    // break;

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

    // public static boolean isValidAddress(String address) {
    //     String addressPart = address;
    //     String addressRegex = "(\\d+),\\s(.*),\\sPhuong\\s(.*),\\sQuan\\s(.*),\\sThanh pho\\s(.*$)";
        // Pattern pattern = Pattern.compile(addressRegex);
        // Matcher matcher = pattern.matcher(addressPart);

        // boolean flag = true;
        // if (!matcher.matches()) {
        //     flag = false;
        // }
        // return flag;
    // }
    

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
        // ClassroomManagement classroomManagement = null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } /*else if (managementObject instanceof ClassroomManagement) {
                classroomManagement = (ClassroomManagement) managementObject;
            }*/
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

            // ClassroomManagement.displayClassroomFormation();
            String className = "";
            do {
                System.out.print("Class (format: 1A1): ");
                className = scanner.nextLine();
                // flag = ClassroomManagement.isValidClassroom(className);

                if (!flag) {
                    System.out.println("Class is invalid (No class available)!");
                }
            } while (!flag);
            int gradeNumber = className.charAt(0) - '0';
            // Grade grade = new Grade(gradeNumber);
            // Classroom classroom = new Classroom(className, grade);

            String pupilID = createNewID(pupilManagement.getLastPupilID());
            // pupilManagement.add(new Pupil(pupilID, fullName, dob, address, sex, classroom));

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
        // ParentManagement parentManagement = null;
        TeacherManagement teacherManagement = null;
        // ClassroomManagement classroomManagement = null;
        PointManagement pointManagement =null;

        for (Object managementObject : managementObjects) {
            if (managementObject instanceof PupilManagement) {
                pupilManagement = (PupilManagement) managementObject;
            } else if (managementObject instanceof TeacherManagement) {
                teacherManagement = (TeacherManagement) managementObject;
            } /*else if (managementObject instanceof ParentManagement) {
                parentManagement = (ParentManagement) managementObject;
            }
            else if (managementObject instanceof PointManagement) {
                parentManagement = (PointManagement) managementObject;
            }*/
        }

        int option = 0;
        do {
            System.out.println("======================= Menu =======================");
            System.out.println("1. Search pupils data by name");
            System.out.println("2. Search pupils data by class");
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
                    // pupilManagement.findPupilsBy(className, "getClassName", Pupil.class, Classroom.class);
                    pupilManagement.display(pupilManagement.getSearchResultLength());
                    break;

                default:
                    break;
            }
        } while (option != 0);
    }

    // public static void addClassroomsToClassroomManagementList(ClassroomManagement classroomManagement,
    //         Scanner scanner) {
    //     char option = 'y';
    //     do {
    //         System.out.println("Add classrooms: ");
    //         System.out.print("Classname (Format: 6A1): ");
    //         String className = scanner.nextLine();

    //         System.out.println("Add classManagerID: ");
    //         System.out.print("ClassManagerID (Format: GV0016): ");
    //         String classManagerID = scanner.nextLine();

    //         System.out.println("Add grade: ");
    //         System.out.print("GradeNumber (Format: 6): ");
    //         int gradeNumber = Integer.parseInt(scanner.nextLine());

    //         System.out.println("Add grademanager: ");
    //         System.out.print("GrademanagerID (Format: GV0016): ");
    //         String gradeManagerID = scanner.nextLine();

    //         Grade grade = new Grade(gradeNumber, null);
    //         classroomManagement.add(new Classroom(className, grade));

    //         System.out.println("Do you want to add more classrooms ? Yes(Y) : No(N)");
    //         option = scanner.nextLine().charAt(0);
    //     } while (option == 'y' || option == 'Y');

    // }

    // public static void updateClassroomData(ClassroomManagement classroomManagement, Scanner scanner) {
    //     System.out.print("Enter class name: ");
    //     String ID = scanner.nextLine();
    //     classroomManagement.update(ID);
    // }

    // public static void deleteClassroomData(ClassroomManagement classroomManagement, Scanner scanner) {
    //     System.out.print("Enter class name: ");
    //     String ID = scanner.nextLine();
    //     classroomManagement.delete(ID);

    // }

    public static void updatePointData(PointManagement pointManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pointManagement.update(ID);
        
    }

    public static void deletePointData(PointManagement pointManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pointManagement.delete(ID);
    }

    public static void searchPointData(PointManagement pointManagement, Scanner scanner) {
        System.out.print("Enter pupil ID to search: ");
        String ID = scanner.nextLine();
        Point foundPoint = pointManagement.searchPointByPupilID(ID);

        foundPoint.calculatePerformance();

        if (foundPoint != null) {
            System.out.println("Point found: " + foundPoint.toString() + foundPoint.getConduct().getRank() + "\t"
                    + foundPoint.getPerformance());
        } else {
            System.out.println("Point not found.");
        }
    }

    public static boolean isPoint(double value) {
        return value >= 0 && value <= 10;
    }

    public static void addPointToPointManagementList(PointManagement pointManagement, Scanner scanner) {
        char option = 'y';
        do {
            System.out.println("Add points: ");
            System.out.print("Pupil ID: ");
            String pupilID = scanner.nextLine();

            double literaturePoint, mathPoint, physicalEducationPoint, englishPoint;

            do {
                System.out.print("Literature Point (0-10): ");
                literaturePoint = scanner.nextDouble();
            } while (!isPoint(literaturePoint));

            do {
                System.out.print("Math Point (0-10): ");
                mathPoint = scanner.nextDouble();
            } while (!isPoint(mathPoint));

            do {
                System.out.print("Physical Education Point (0-10): ");
                physicalEducationPoint = scanner.nextDouble();
            } while (!isPoint(physicalEducationPoint));

            do {
                System.out.print("English Point (0-10): ");
                englishPoint = scanner.nextDouble();
            } while (!isPoint(englishPoint));

            scanner.nextLine(); // Consume the newline character

            System.out.print("Conduct: ");
            int conductValue = Integer.parseInt(scanner.nextLine());

            Point point = new Point(pupilID, literaturePoint, mathPoint, physicalEducationPoint, englishPoint,
                    new Conduct(conductValue));
            pointManagement.add(point);

            System.out.println("Do you want to add more points? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');
    }
}
