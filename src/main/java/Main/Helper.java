package Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Person.Address;
import Classes.Person.Date;
import Classes.Points.Conduct;
import Classes.Points.Point;
import Classes.Points.PointManagement;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Teachers.Teacher;
import Classes.Teachers.TeacherManagement;

public class Helper {
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
            String conduct = scanner.nextLine();
    
            Point point = new Point(pupilID, literaturePoint, mathPoint, physicalEducationPoint, englishPoint, new Conduct(conduct, option), conduct);
            pointManagement.add(point);
    
            System.out.println("Do you want to add more points? Yes(Y) : No(N)");
            option = scanner.nextLine().charAt(0);
        } while (option == 'y' || option == 'Y');
    }
    
    public static void updatePointData(PointManagement pointManagement, Scanner scanner) {
        System.out.print("Enter pupil ID: ");
        String ID = scanner.nextLine();
        pointManagement.update(ID);
        System.out.println("Update successfully!");
    }

   public static void deletePointData(PointManagement pointManagement, Scanner scanner) {
    System.out.print("Enter pupil ID: ");
    String ID = scanner.nextLine();
    pointManagement.delete(ID);
    System.out.println("Delete successfully!");
}
    public static void searchPointData(PointManagement pointManagement, Scanner scanner) {
    System.out.print("Enter pupil ID to search: ");
    String ID = scanner.nextLine();
    Point foundPoint = pointManagement.searchByPupilID(ID);

    if (foundPoint != null) {
        System.out.println("Point found: " + foundPoint.toString());
    } else {
        System.out.println("Point not found.");
    }
}}
