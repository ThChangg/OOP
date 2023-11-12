package Main;

import java.util.Scanner;

import Classes.Classroom.ClassroomManagement;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.TeacherManagement;
public class App {

    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        TeacherManagement teacherManagement = new TeacherManagement();
        ClassroomManagement classroomManagement = new ClassroomManagement();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("==========================Menu==========================");
            System.out.println("Please select: ");
            System.out.println("1. Initialize data");
            System.out.println("2. Print out data");
            System.out.println("3. Adding 1 or n person to");
            System.out.println("4. Update person information");
            System.out.println("5. Delete person");
            System.out.println("6. Searching for the pupil information");
            System.out.println("7. Statistics");
            System.out.println("8. Initialize Teacher data");
            System.out.println("9. Print out Teacher data");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //pupilManagement.initialize();
                    classroomManagement.initialize();
                    //System.out.println("Pupil Management List is now initialized!");
                    break;
                case 2:
                    //pupilManagement.display();
                    classroomManagement.display();
                    break;
                case 3:
                    //Helper.addPupilsToPupilManagementList(pupilManagement, sc);
                    Helper.addClassroomsToClassroomManagementList(classroomManagement, sc);
                    break;
                case 4:
                    //Helper.updatePupilData(pupilManagement, sc);
                    Helper.updateClassroomData(classroomManagement, sc);
                    break;
                case 5:
                    //Helper.deletePupilData(pupilManagement, sc);
                    Helper.deleteClassroomData(classroomManagement, sc);
                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:
                    teacherManagement.initialize();
                    System.out.println("Teacher Management List is now initialized!");
                break;
                case 9:
                    teacherManagement.display();
                    break;
                default:
                    System.out.println("Exited!");
                    break;
            }
        } while (option != 0);
        sc.close();
    }

    public static void main(String[] args) {
        Menu();
    }
}
