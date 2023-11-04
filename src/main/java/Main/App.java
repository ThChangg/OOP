package Main;

import java.util.Scanner;

import Classes.Pupils.PupilManagement;

public class App {
    public static void Menu(Object obj) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("==========================Menu==========================");
            System.out.println("Please select: ");
            System.out.println("1. Initialize Pupil Management List");
            System.out.println("2. Print out the Pupil Management List");
            System.out.println("3. Adding 1 or n pupil into  Pupil Management List");
            System.out.println("4. Update the pupil information");
            System.out.println("5. Delete the pupil");
            System.out.println("6. Searching for the pupil information");
            System.out.println("7. Statistics");
            System.out.println("0. Exit");

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    ((PupilManagement) obj).initialize();
                    System.out.println("Pupil Management List is now initialized!");
                    break;
                case 2:
                    ((PupilManagement) obj).display();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

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
        PupilManagement pupilManagement = new PupilManagement();
        Menu(pupilManagement);
    }
}
