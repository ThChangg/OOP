package Classes.Points;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class PointManagement implements IFileManagement, ICRUD {
    private Point listPoint[];
    private int currentIndex;

    public PointManagement() {
        listPoint = new Point[100];
        currentIndex = 0;

    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    @Override
    public void initialize() {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("-");
                    if (parts.length >= 6) {
                        String pupilID = parts[0];
                        double literaturePoint = Double.parseDouble(parts[1]);
                        double mathPoint = Double.parseDouble(parts[2]);
                        double physicalEducationPoint = Double.parseDouble(parts[3]);
                        double englishPoint = Double.parseDouble(parts[4]);
                        String rank = parts[5];

                        Conduct conduct = new Conduct(rank);
                        Point point = new Point(pupilID, literaturePoint, mathPoint, physicalEducationPoint,
                                englishPoint, conduct);

                        this.add(point);
                    } else {
                        System.out.println("Record does not have enough information");
                    }
                }
            } catch (IOException e) {
                ((Throwable) e).printStackTrace();
            }
            System.out.println("File exists.");
        } else {
            System.out.println("File does not exist.");
        }

    }

    @Override
    public void display() {
        String relativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Main\\output.txt";

        File file = new File(relativePath);
        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Point Management List:");
                writer.newLine();
                writer.write(String.format("%-5s\t%-10s\t%-10s\t%-20s\t%-20s\t%-15s", "ID", "LiteraturePoint",
                        "MathPoint", "PhysicalEducationPoint", "EnglishPoint", "Conduct"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    writer.write(listPoint[i].toString());
                    writer.newLine();
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + relativePath);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
            }
            System.out.println("File exists.");
        } else {
            System.out.println("File does not exist.");
        }
    }

    @Override
    public void add(Object obj) {
        if (currentIndex < listPoint.length) {
            listPoint[currentIndex++] = (Point) obj;
        } else {
            System.out.println("Point List is full. Cannot add more.");
        }
    }

    @Override
    public void update(String ID) {
        Scanner sc = new Scanner(System.in);
        Point point = getPointByID(ID);
        Conduct conduct = point.getConduct();
        if (point != null) {
            System.out.println("Old LiteraturePoint " + point.getLiteraturePoint());
            System.out.println("New LiteraturePoint: ");
            double literaturePoint = Double.parseDouble(sc.nextLine());
            point.setLiteraturePoint(literaturePoint);
            System.out.println("Old MathPoint " + point.getMathPoint());
            System.out.println("New MathPoint: ");
            double mathPoint = Double.parseDouble(sc.nextLine());
            point.setMathPoint(mathPoint);

            System.out.println("Old PhysicalEducationPoint " + point.getPhysicalEducationPoint());
            System.out.println("New PhysicalEducationPoint: ");
            double physicalEducationPoint = Double.parseDouble(sc.nextLine());
            point.setPhysicalEducationPoint(physicalEducationPoint);
            System.out.println("Old EnglishPoint " + point.getEnglishPoint());
            System.out.println("New EnglishPoint : ");
            double englishPoint = Double.parseDouble(sc.nextLine());
            point.setEnglishPoint(englishPoint);
            System.out.println("Old Conduct " + conduct.getRank());
            System.out.println("New Conduct: ");
            String newConductRank = sc.nextLine();
            point.getConduct().setRank(newConductRank);
        }
    }

    private Point getPointByID(String ID) {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPupilID().equalsIgnoreCase(ID)) {
                return listPoint[i];
            }
        }
        System.out.println("Point with ID: " + ID + " is not found!");
        return null;
    }

    @Override
    public void delete(String ID) {
        int index = this.getPointArrayIndex(ID);
        if (index >= 0) {
            for (int i = index; i < currentIndex - 1; i++) {
                this.listPoint[i] = this.listPoint[i + 1];
            }
            this.currentIndex--;
        } else {
            System.out.println("Point with ID: " + ID + " is not found!");
        }

    }

    public Point searchByPupilID(String pupilID) {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPupilID().equalsIgnoreCase(pupilID)) {
                return listPoint[i];
            }
        }
        return null;
    }

    public String getLastPointID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            ID = listPoint[i].getPupilID();
        }
        return ID;
    }

    public int getPointArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPupilID().equalsIgnoreCase(ID)) {
                index = i;
                break;
            }
        }
        return index;
    }

}
