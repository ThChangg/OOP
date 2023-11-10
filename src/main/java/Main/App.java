package Main;

import java.util.Scanner;

import Classes.Points.Point;
import Classes.Points.PointManagement;
import Classes.Pupils.PupilManagement;

public class App {

    public static void Menu() {
        PupilManagement pupilManagement = new PupilManagement();
        PointManagement pointManagement = new PointManagement();
       
        Scanner sc = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("==========================Menu==========================");
            System.out.println("Please select: ");
            System.out.println("1. Initialize data");
            System.out.println("2. Print out data");
            System.out.println("3. Adding 1 or n point to");
            System.out.println("4. Update point ");
            System.out.println("5. Delete point");
            System.out.println("6. Searching for the pupil information");
            System.out.println("7. Statistics");
            
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    // pupilManagement.initialize();
                    pointManagement.initialize();
                    System.out.println("Point Management List is now initialized!");
                    break;
                case 2:
                    // pupilManagement.display();
                    pointManagement.display();
                    break;
                case 3:
                    Helper.addPointToPointManagementList(pointManagement, sc);
                    break;
                case 4:
                    Helper.updatePointData(pointManagement, sc);
                    break;
                case 5:
                    Helper.deletePointData(pointManagement, sc);
                    break;
                case 6:
                Helper.searchPointData(pointManagement, sc);

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

    public static void main(String[] args) {
        Menu();
    }
}
