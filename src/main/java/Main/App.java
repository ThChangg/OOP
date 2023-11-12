package Main;

import java.util.Scanner;

import Classes.Points.Point;
import Classes.Points.PointManagement;
import Classes.Pupils.PupilManagement;

public class App {

    /**
     * 
     */
    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        PointManagement pointManagement = new PointManagement();
       
        Scanner sc = new Scanner(System.in);
        int option = 0;
    //     do {
    //         System.out.println("==========================Menu==========================");
    //         System.out.println("Please select: ");
    //         System.out.println("1. Initialize data");
    //         System.out.println("2. Print out data");
    //         System.out.println("3. Adding 1 or n point to");
    //         System.out.println("4. Update point ");
    //         System.out.println("5. Delete point");
    //         System.out.println("6. Searching for the pupil information");
    //         System.out.println("7. Statistics");
            
    //         System.out.println("0. Exit");

    //         option = Integer.parseInt(sc.nextLine());
    //         switch (option) {
    //             case 1:
    //                 // pupilManagement.initialize();
    //                 pointManagement.initialize();
    //                 System.out.println("Point Management List is now initialized!");
    //                 break;
    //             case 2:
    //                 // pupilManagement.display();
    //                 pointManagement.display();
    //                 break;
    //             case 3:
    //                 Helper.addPointToPointManagementList(pointManagement, sc);
    //                 break;
    //             case 4:
    //                 Helper.updatePointData(pointManagement, sc);
    //                 break;
    //             case 5:
    //                 Helper.deletePointData(pointManagement, sc);
    //                 break;
    //             case 6:
    //             Helper.searchPointData(pointManagement, sc);

    //                 break;
    //             case 7:

    //                 break;
              
    //             default:
    //                 System.out.println("Exited!");
    //                 break;
    //         }
    //     } while (option != 0);
    //     sc.close();
    // }
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
        System.out.println("10. Adding 1 or n teacher to");
        System.out.println("11. Update teacher information");
        System.out.println("12. Delete teacher");
        System.out.println("13. SearchTeacherID");
        System.out.println("14. SearchTeacherName");
        /*System.out.println("15. SearchTeacherClassroom");
        System.out.println("16. SearchTeacherMajor");*/
        System.out.println("17. Initialize Point data");
        System.out.println("18. Print out Point data");
        System.out.println("19. Adding 1 or n Point to");
        System.out.println("20. Update Point information");
        System.out.println("21. Delete point");
        System.out.println("22. Search Pupil-pointID");
        // System.out.println("14. SearchTeacherName");
        System.out.println("0. Exit");

        option = Integer.parseInt(sc.nextLine());
        switch (option) {
            // case 1:
            //     pupilManagement.initialize();
            //     System.out.println("Pupil Management List is now initialized!");
            //     break;
            // case 2:
            //     pupilManagement.display();
            //     break;
            // case 3:
            //     Helper.addPupilsToPupilManagementList(pupilManagement, sc);
            //     break;
            // case 4:
            //     Helper.updatePupilData(pupilManagement, sc);
            //     break;
            // case 5:
            //     Helper.deletePupilData(pupilManagement, sc);
            //     break;
            // case 6:

            //     break;
            // case 7:

            //     break;
            // case 8:
            //     teacherManagement.initialize();
            //     System.out.println("Teacher Management List is now initialized!");
            // break;
            // case 9:
            //     teacherManagement.display();
            //     break;
            // default:
            //     System.out.println("Exited!");
            //     break;
            // case 10:
            //     Helper.addTeachersToTeacherManagementList(teacherManagement, sc);
            //     break;
            // case 11:
            //     Helper.updateTeacherData(teacherManagement, sc);
            //     break;
            // case 12:
            //     Helper.deleteTeacherData(teacherManagement, sc);
            //     break;
            // case 13:
            //     Helper.searchTeacherID(teacherManagement, sc);
            //     break;
            // case 14:
            //     Helper.searchTeacherName(teacherManagement, sc);
            //     break;
            /*case 15:
                Helper.searchTeacherClassroom(teacherManagement, sc);
                break;
            case 16:
                Helper.searchTeacherMajor(teacherManagement, sc);
                break;*/
        
        case 17:
        pointManagement.initialize();
                    System.out.println("Point Management List is now initialized!");
                    break;
        case 18:
        pointManagement.display();
                    break;
        case 19:
        Helper.addPointToPointManagementList(pointManagement, sc);
                    break;
        case 20:
         Helper.updatePointData(pointManagement, sc);
                    break;
        case 21:
           Helper.deletePointData(pointManagement, sc);
                    break;
        case 22:
        Helper.searchPointData(pointManagement, sc);

                    break;
        }
    } while (option != 0);
    sc.close();
}

    public static void main(String[] args) {
        Menu();
    }
}
