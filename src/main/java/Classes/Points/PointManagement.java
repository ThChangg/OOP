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

import Classes.Redux.Redux;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class PointManagement implements IFileManagement, ICRUD {
    private Point listPoint[];
    private Point searchResult[];
    private int currentIndex;
    private int searchResultLength;
    private static String inputRelativePath = System.getProperty("user.dir") + "\\src\\main\\java\\Data\\points.txt";

    public Point[] getListPoint() {
        return this.listPoint;
    }

    public void setPointList(Point listPoint[]) {
        this.listPoint = listPoint;
    }

    public PointManagement() {
        listPoint = new Point[100];
        currentIndex = 0;
        searchResult = new Point[100];
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Point[] getSearchResult() {
        return this.searchResult;
    }

    public void setSearchResult(Point searchResult[]) {
        this.searchResult = searchResult;
    }

    public int getSearchResultLength() {
        return this.searchResultLength;
    }

    public void setSearchResultLength(int searchResultLength) {
        this.searchResultLength = searchResultLength;
    }

    @Override
    public void initialize() {
        File file = new File(inputRelativePath);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputRelativePath), "UTF-8"))) {
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
        String headerFormat = "%-5s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s";

        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                writer.write("Point Management List:");
                writer.newLine();
                writer.write(String.format(headerFormat, "ID", "Literature", "Math", "PE",
                        "English", "Conduct", "Rank", "Performance"));
                writer.newLine();

                for (int i = 0; i < currentIndex; i++) {
                    if (listPoint[i].getStatus()) {
                        writer.write(listPoint[i].toString());
                        writer.newLine();
                    }
                }
                writer.write("================================================================");
                writer.newLine();
                System.out.println("Data written to " + Redux.getOutputRelativePath());
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    // Display method for searching points by pupilID
    public void display(int arrayLength) {
        File file = new File(Redux.getOutputRelativePath());
        if (file.exists()) {
            try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                bufferedWrite.write("Classroom Search List:");
				bufferedWrite.newLine();
				bufferedWrite.write(String.format("%-5s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s", "ID", "Literature", "Math", "PE",
                        "English", "Conduct", "Rank", "Performance"));
				bufferedWrite.newLine();
				for (int i = 0; i < arrayLength; i++) {
					if (searchResult[i].getStatus()) {
						bufferedWrite.write(searchResult[i].toString());
						bufferedWrite.newLine();
					}
				}
				bufferedWrite.write("================================================================");
				bufferedWrite.newLine();
				System.out.println("Data written to " + Redux.getOutputRelativePath());
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        }
    }

    public void calculateRank() {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getStatus()) {
                calculateRank(listPoint[i]); // Call the common method to calculate rank
            }
        }
    }

    public void calculateRank(Point point) {
        int pointConductValue = point.getConduct().getPointConduct();
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
    public void update(Object obj) {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPointID().equalsIgnoreCase(((Point) obj).getPointID())) {
                listPoint[i] = (Point) obj;
                break;
            }
        }
    }

    @Override
    public void delete(String ID) {
        int index = this.getPointArrayIndex(ID);
        if (index >= 0) {
            for (int i = 0; i < currentIndex; i++) {
                if (i == index) {
                    listPoint[i].setStatus(false);
                    Redux.addToRecycleBin(listPoint[i]);
                }
            }
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Point with ID: " + ID + " is not found!");
        }
    }

    public String getLastPointID() {
        String ID = "";
        for (int i = 0; i < currentIndex; i++) {

            ID = listPoint[i].getPointID();

        }
        return ID;
    }

    public Point getPointByID(String ID) {
        Point point = null;
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getPointID().equalsIgnoreCase(ID)) {
                point = listPoint[i];
                break;
            }
        }
        return point;
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

    public static void insertIntoDatabase(String record) {
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

    public static void updateRecord(String updatedRecord) {
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
        File file = new File(inputRelativePath);
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
        File file = new File(inputRelativePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(records);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

    public void findPointByPupilID(String pupilID) {
        for (int i = 0; i < currentIndex; i++) {
            if (listPoint[i].getStatus()) {
                if (listPoint[i].getPupil().getStatus()) {
                    if (listPoint[i].getPupil().getPupilID().toLowerCase().contains(pupilID.toLowerCase())) {
                        searchResult[searchResultLength++] = listPoint[i];
                    }
                }
            }
        }
    }

}