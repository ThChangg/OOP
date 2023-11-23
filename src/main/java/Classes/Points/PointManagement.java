package Classes.Points;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] parts = line.split("-");
                    if (parts.length >= 6) {
                        String pupilID = parts[0];
                        double literaturePoint = Double.parseDouble(parts[1]);
                        double mathPoint = Double.parseDouble(parts[2]);
                        double physicalEducationPoint = Double.parseDouble(parts[3]);
                        double englishPoint = Double.parseDouble(parts[4]);
                        int pointConductValue = Integer.parseInt(parts[5].trim());

                        Conduct conduct = new Conduct(pointConductValue);
                        Point point = new Point(pupilID, literaturePoint, mathPoint, physicalEducationPoint,
                                englishPoint, conduct);

                        this.add(point);
                    } else {
                        System.out.println("Record does not have enough information");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File exists.");

        } else {
            System.out.println("File does not exist.");
        }
        calculateRank();
    }

    @Override
    public void display() {
        String headerFormat = "%-5s\t%-15s\t%-10s\t%-25s\t%-15s\t%-15s\t%-10s\t%-15s";
        String dataFormat = "%-5s\t%-15.1f\t%-10.1f\t%-25.1f\t%-15.1f\t%-15d\t%-10s\t%-15s";

        String relativePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\Main\\output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
            writer.write("Point Management List:");
            writer.newLine();
            writer.write(String.format(headerFormat, "ID", "LiteraturePoint", "MathPoint", "PhysicalEducationPoint",
                    "EnglishPoint", "PointConduct", "Rank", "Performance"));
            writer.newLine();

            for (int i = 0; i < currentIndex; i++) {
                if (listPoint[i].getStatus()) {
                    Point point = listPoint[i];
                    calculateRank(point);

                    point.calculatePerformance();

                    writer.write(String.format(dataFormat, point.getPupilID(), point.getLiteraturePoint(),
                            point.getMathPoint(), point.getPhysicalEducationPoint(), point.getEnglishPoint(),
                            point.getConduct().getpointConduct(), point.getConduct().getRank(),
                            point.getPerformance()));
                    writer.newLine();
                }
            }
            writer.write("================================================================");
            writer.newLine();
            System.out.println("Data written to " + relativePath);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private void calculateRank() {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getStatus()) {
                calculateRank(listPoint[i]); // Call the common method to calculate rank
            }
        }
        System.out.println("Ranks calculated.");
    }

    @Override
    public void add(Object obj) {
        if (currentIndex < listPoint.length) {
            listPoint[currentIndex++] = (Point) obj;
            updateDataFile(); // Update the data file after adding a new point
        } else {
            System.out.println("Point List is full. Cannot add more.");
        }
    }

    public static boolean isPoint(double value) {
        return value >= 0 && value <= 10;
    }

    @Override
    public void update(String ID) {
        Scanner sc = new Scanner(System.in);
        Point point = getPointByID(ID);
        Conduct conduct = point.getConduct();
        if (point != null) {
            do {
                System.out.println("Old LiteraturePoint: " + point.getLiteraturePoint());
                System.out.print("New LiteraturePoint(0-10): ");
                String input = sc.nextLine().trim(); // Read the entire line including whitespaces

                if (input.isEmpty()) {
                    // User pressed Enter without entering anything
                    break;
                }
                while (!sc.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    sc.next(); // Consume the invalid input
                }
                double newLiteraturePoint = sc.nextDouble();

                if (isPoint(newLiteraturePoint)) {
                    point.setLiteraturePoint(newLiteraturePoint);
                } else {
                    System.out.print("New LiteraturePoint(0-10): ");
                    newLiteraturePoint = sc.nextDouble();
                }
            } while (!isPoint(point.getLiteraturePoint()));
            do {
                System.out.println("Old MathPoint: " + point.getMathPoint());
                System.out.print("New MathPoint(0-10): ");
                String input = sc.nextLine().trim(); // Read the entire line including whitespaces

                if (input.isEmpty()) {
                    // User pressed Enter without entering anything
                    break;
                }

                while (!sc.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    sc.next(); // Consume the invalid input
                }
                double newMathPoint = sc.nextDouble();

                if (isPoint(newMathPoint)) {
                    point.setMathPoint(newMathPoint);
                } else {
                    System.out.print("New MathPoint(0-10): ");
                    newMathPoint = sc.nextDouble();
                }
            } while (!isPoint(point.getMathPoint()));
            do {
                System.out.println("Old PhysicalEducationPoint: " + point.getPhysicalEducationPoint());
                System.out.print("New PhysicalEducationPoint(0-10): ");
                String input = sc.nextLine().trim(); // Read the entire line including whitespaces

                if (input.isEmpty()) {
                    // User pressed Enter without entering anything
                    break;
                }
                while (!sc.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    sc.next(); // Consume the invalid input
                }
                double newPhysicalEducationPoint = sc.nextDouble();

                if (isPoint(newPhysicalEducationPoint)) {
                    point.setPhysicalEducationPoint(newPhysicalEducationPoint);
                } else {
                    System.out.print("New PhysicalEducationPoint(0-10): ");
                    newPhysicalEducationPoint = sc.nextDouble();
                }
            } while (!isPoint(point.getPhysicalEducationPoint()));
            do {
                System.out.println("OldEnglishPoint: " + point.getEnglishPoint());
                System.out.print("New EnglishPointPoint(0-10): ");
                String input = sc.nextLine().trim(); // Read the entire line including whitespaces

                if (input.isEmpty()) {
                    // User pressed Enter without entering anything
                    break;
                }
                while (!sc.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    sc.next(); // Consume the invalid input
                }
                double newEnglishPoint = sc.nextDouble();

                if (isPoint(newEnglishPoint)) {
                    point.setEnglishPoint(newEnglishPoint);
                } else {
                    System.out.print("New EnglishPointPoint(0-10): ");
                    newEnglishPoint = sc.nextDouble();
                }
            } while (!isPoint(point.getEnglishPoint()));
        } else {
            System.out.println("Student with ID " + ID + " not found.");
        }

        System.out.println("Old PointConduct: " + conduct.getpointConduct());
        System.out.print("New PointConduct(0-100): ");
        
        String conductInput = sc.nextLine().trim();
        
        if (!conductInput.isEmpty()) {
            try {
                int newPointConduct = Integer.parseInt(conductInput);

                if (newPointConduct >= 0 && newPointConduct <= 100) {
                    point.getConduct().setpointConduct(newPointConduct);
                } else {
                    System.out.println("Invalid input. Please enter an integer value between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer value.");
            }
        }
        updateRecord(point.toString());
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
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    listPoint[i].setStatus(false);
                }
            }
            System.out.println("Delete successfully!");

            // Update the data file after deletion
            updateDataFile();
        } else {
            System.out.println("Point of Pupil with ID: " + ID + " is not found!");
        }
    }

    public Point searchPointByPupilID(String pupilID) {
        Point point = null;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPupilID().equalsIgnoreCase(pupilID)) {
                point = listPoint[i];
            }
        }
        return point;
    }

    public String getLastPointID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getStatus()) {
                ID = listPoint[i].getPupilID();
            }
        }
        return ID;
    }

    public int getPointArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPupilID().equalsIgnoreCase(ID)) {
                if (listPoint[i].getStatus()) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private void calculateRank(Point point) {
        int pointConductValue = point.getConduct().getpointConduct();
        String rank;
        if (pointConductValue >= 80) {
            rank = "Very Good";
        } else if (pointConductValue >= 65 && pointConductValue <= 79) {
            rank = "Good";
        } else if (pointConductValue >= 50 && pointConductValue <= 64) {
            rank = "Average";
        } else {
            rank = "Weak";
        }
        point.getConduct().setRank(rank);
    }

    public void insertIntoDatabase(String record) {
        // Read existing records from the database file
        String existingRecords = readDatabase();

        // Check if the new record is not present in the existing records
        if (!existingRecords.contains(record)) {
            // Append the new record to the existing records
            writeDatabase(existingRecords + "\n" + record);
        } else {
            System.out.println("Record already exists in the database. Not added.");
        }
    }

    public void updateRecord(String updatedRecord) {
        String databaseContent = readDatabase();
        String[] records = databaseContent.split("\n");
        String pupilID = updatedRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(pupilID)) {
                records[i] = updatedRecord;
                break;
            }
        }

        StringBuilder updatedContent = new StringBuilder();
        for (String record : records) {
            updatedContent.append(record).append("\n");
        }

        writeDatabase(updatedContent.toString());
    }

    public void deleteRecord(String record) {
        // Read existing records from the database file
        String existingRecords = readDatabase();

        // Check if the record is present in the existing records
        if (existingRecords.contains(record)) {
            // Remove the record from the existing records
            String updatedRecords = existingRecords.replace(record, "").trim();

            // Update the database with the modified records
            writeDatabase(updatedRecords);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found in the database. Deletion failed.");
        }
    }

    private String readDatabase() {
        StringBuilder records = new StringBuilder();
        String relativePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                records.append(scanner.nextLine()).append("\n");

            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
        return records.toString().trim();
    }

    private void writeDatabase(String records) {
        String relativePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records + "\n");
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

    private void updateDataFile() {
        String relativePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i < currentIndex; i++) {
                if (listPoint[i].getStatus()) {
                    writer.write(listPoint[i].toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
