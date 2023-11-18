package Classes.Points;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
        String relativePath = System.getProperty("user.dir")
                + "\\Pupil-Management-OOP\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(relativePath), "UTF-8"))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 6) {
                        String pupilID = parts[0];
                        double literaturePoint = Double.parseDouble(parts[1]);
                        double mathPoint = Double.parseDouble(parts[2]);
                        double physicalEducationPoint = Double.parseDouble(parts[3]);
                        double englishPoint = Double.parseDouble(parts[4]);
                        int pointConductValue = Integer.parseInt(parts[5]);

                        Conduct conduct = new Conduct(pointConductValue);
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
        calculateRank();
    }

    @Override
    public void display() {
        String headerFormat = "%-5s\t%-15s\t%-10s\t%-25s\t%-15s\t%-15s\t%-10s\t%-15s";
        String dataFormat = "%-5s\t%-15.1f\t%-10.1f\t%-25.1f\t%-15.1f\t%-15d\t%-10s\t%-15s";

        String relativePath = System.getProperty("user.dir")
                + "\\Pupil-Management-OOP\\src\\main\\java\\Main\\output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
            writer.write("Point Management List:");
            writer.newLine();
            writer.write(String.format(headerFormat, "ID", "LiteraturePoint", "MathPoint", "PhysicalEducationPoint",
                    "EnglishPoint", "PointConduct", "Rank", "Performance"));
            writer.newLine();

            for (int i = 0; i < currentIndex; i++) {
                if (listPoint[i].getStatus()) {
                    Point point = listPoint[i];
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
                    point.calculatePerformance();

                    writer.write(String.format(dataFormat, point.getPupilID(), point.getLiteraturePoint(),
                            point.getMathPoint(),
                            point.getPhysicalEducationPoint(), point.getEnglishPoint(), pointConductValue,
                            point.getConduct().getRank(),
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

    @Override
    public void add(Object obj) {
        if (currentIndex < listPoint.length) {
            listPoint[currentIndex++] = (Point) obj;
            updateDataFile(); // Update the data file after adding a new point
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
            System.out.println("Old PointConduct " + conduct.getpointConduct());
            System.out.println("New PointConduct: ");
            int newPointConduct = sc.nextInt();
            point.getConduct().setpointConduct(newPointConduct);
            updateRecord(point.toString());
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

    private void calculateRank() {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getStatus()) {
                Point point = listPoint[i];
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
        }
        System.out.println("Ranks calculated.");
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
                + "\\Pupil-Management-OOP\\src\\main\\java\\Data\\points.txt";
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
                + "\\Pupil-Management-OOP\\src\\main\\java\\Data\\points.txt";
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
                + "\\Pupil-Management-OOP\\src\\main\\java\\Data\\points.txt";
        File file = new File(relativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i < currentIndex; i++) {
                if (listPoint[i].getStatus()) {
                    writer.write(listPoint[i].toString() + "\n");
                }
            }
            System.out.println("Data file updated after deletion.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
