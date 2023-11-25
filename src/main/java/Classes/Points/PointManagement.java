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

// import Classes.Pupils.Pupil;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;
import Main.Redux;

public class PointManagement implements IFileManagement, ICRUD {
    private Point listPoint[];
    private int currentIndex;

    public Point[] getListPoint() {
        return this.listPoint;
    }

    public void setPointList(Point listPoint[]) {
        this.listPoint = listPoint;
    }

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
                        String pointID = parts[0];
                        double literaturePoint = Double.parseDouble(parts[1]);
                        double mathPoint = Double.parseDouble(parts[2]);
                        double physicalEducationPoint = Double.parseDouble(parts[3]);
                        double englishPoint = Double.parseDouble(parts[4]);
                        int pointConductValue = Integer.parseInt(parts[5].trim());
                        Conduct conduct = new Conduct(pointConductValue);
                        Point point = new Point(pointID, literaturePoint, mathPoint, physicalEducationPoint,
                                englishPoint, conduct);
                        this.add(point);
                        calculateRank(point);
                        point.calculatePerformance();
                    } else {
                        System.out.println("Record does not have enough information");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist.");
        }
        calculateRank();
    }

    @Override
    public void display() {
        String headerFormat = "%-5s\t%-15s\t%-10s\t%-25s\t%-15s\t%-15s\t%-10s\t%-15s";
        String relativePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\Main\\output.txt";
        File file = new File(relativePath);
        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
                writer.write("Point Management List:");
                writer.newLine();
                writer.write(String.format(headerFormat, "ID", "LiteraturePoint", "MathPoint", "PhysicalEducationPoint",
                        "EnglishPoint", "PointConduct", "Rank", "Performance"));
                writer.newLine();
                for (int i = 0; i < currentIndex; i++) {
                    if (listPoint[i].getStatus()) {
                        Point point = listPoint[i];
                        writer.write(
                                point.toString() + "-" + point.getConduct().getRank() + "-" + point.getPerformance());
                        writer.newLine();
                    }
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + relativePath);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void calculateRank() {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getStatus()) {
                calculateRank(listPoint[i]); // Call the common method to calculate rank
            }
        }
        System.out.println("Ranks calculated.");
    }

    public void calculateRank(Point point) {
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

    @Override
    public void add(Object obj) {
        if (currentIndex < listPoint.length) {
            listPoint[currentIndex++] = (Point) obj;
            Point addedPoint = listPoint[currentIndex - 1]; // The recently added point
            calculateRank(addedPoint);
            addedPoint.calculatePerformance();
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
            boolean flag = true;
            do {
                Double literaturePoint = 0.0;
                do {
                    System.out.println("Old LiteraturePoint: " + point.getLiteraturePoint());
                    System.out.print("New LiteraturePoint(0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        literaturePoint = Double.parseDouble(input);
                        flag = Point.isPoint(literaturePoint);
                        if (flag) {
                            point.setLiteraturePoint(literaturePoint);
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        literaturePoint = point.getLiteraturePoint();
                    }
                } while (!flag);

                Double mathPoint = 0.0;
                do {
                    System.out.println("Old MathPoint: " + point.getMathPoint());
                    System.out.print("New MathPoint(0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        mathPoint = Double.parseDouble(input);
                        flag = Point.isPoint(mathPoint);
                        if (flag) {
                            point.setMathPoint(mathPoint);
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        mathPoint = point.getMathPoint();
                    }
                } while (!flag);

                Double physicalEducationPoint = 0.0;
                do {
                    System.out.println("Old PhysicalEducationPoint: " + point.getPhysicalEducationPoint());
                    System.out.print("New PhysicalEducationPoint(0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        physicalEducationPoint = Double.parseDouble(input);
                        flag = Point.isPoint(physicalEducationPoint);
                        if (flag) {
                            point.setPhysicalEducationPoint(physicalEducationPoint);
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        physicalEducationPoint = point.getPhysicalEducationPoint();
                    }
                } while (!flag);

                Double englishPoint = 0.0;
                do {
                    System.out.println("Old English: " + point.getEnglishPoint());
                    System.out.print("New English(0-10): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        englishPoint = Double.parseDouble(input);
                        flag = Point.isPoint(englishPoint);
                        if (flag) {
                            point.setEnglishPoint(englishPoint);
                        } else {
                            System.out.println("Point is invalid");
                        }
                    } else {
                        englishPoint = point.getEnglishPoint();
                    }
                } while (!flag);

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

                // Update record
                this.updateRecord(point.toString());
                System.out.println("Update successfully!");
                calculateRank(point);
                point.calculatePerformance();
            } while (!flag);
        } else {
            System.out.println("Point ID " + ID + " not found.");
        }
    }

    public Point getPointByID(String ID) {
        Point point = null;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPointID().equalsIgnoreCase(ID)) {
                if (listPoint[i].getStatus()) {
                    point = listPoint[i];
                    break;
                } else {
                    System.out.println("Point does not exist!");
                }
            }
        }
        return point;
    }

    @Override
    public void delete(String ID) {
        int index = this.getPointArrayIndex(ID);
        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    listPoint[i].setStatus(false);
                    Redux.add(listPoint[i]);
                }
            }
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Point with ID: " + ID + " is not found!");
        }
    }

    public Point searchPointByPupilID(String pointID) {
        Point point = null;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPointID().equalsIgnoreCase(pointID)) {
                point = listPoint[i];

            }
        }
        return point;
    }

    public String getLastPointID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {

            ID = listPoint[i].getPointID();

        }
        return ID;
    }

    public int getPointArrayIndex(String ID) {
        int index = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPointID().equalsIgnoreCase(ID)) {
                if (listPoint[i].getStatus()) {
                    index = i;
                    break;
                }
            }
        }
        return index;
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
        String pointID = updatedRecord.substring(0, 5);

        for (int i = 0; i < records.length; i++) {
            if (records[i].startsWith(pointID)) {
                records[i] = updatedRecord;
                break;
            }
        }

    

        StringBuilder updatedContent = new StringBuilder();
        for (int i = 0; i < records.length; i++) {
            updatedContent.append(records[i]);
            if (i < records.length - 1) {
                updatedContent.append("\n");
            }
        }

        writeDatabase(updatedContent.toString());
    }

    public static void deleteRecord(String record) {
        // Read existing records from the database file
        String existingRecords = readDatabase();

        // Check if the record is present in the existing records
        if (existingRecords.contains(record)) {
            // Remove the record from the existing records
            String updatedRecords = existingRecords.replaceAll(record + "(\\n|$)", "").trim();

            // Update the database with the modified records
            writeDatabase(updatedRecords);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found in the database. Deletion failed.");
        }
    }

    public static String readDatabase() {
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

    public static void writeDatabase(String records) {
        String relativePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

}
