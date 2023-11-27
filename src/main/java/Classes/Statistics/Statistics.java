package Classes.Statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Classes.Classroom.Grade;
import Classes.Pupils.Pupil;
import Classes.Pupils.PupilManagement;
import Classes.Redux.Redux;

public class Statistics {
    public static void statisticsOnTheNumberOfPupilsByGender(PupilManagement pupilManagement) {

        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {
                pupilManagement.findPupilsBy("male", "getGender", Pupil.class, null, true, false);
                int malePupilsCount = pupilManagement.getSearchResultLength();
                pupilManagement.findPupilsBy("female", "getGender", Pupil.class, null, true, false);
                int femalePupilsCount = pupilManagement.getSearchResultLength();
                int totalSchoolPupils = pupilManagement.getCurrentIndex();

                Double malePupilsPercentage = (1.0 * malePupilsCount / totalSchoolPupils * 100);
                Double femalePupilsPercentage = (1.0 * femalePupilsCount / totalSchoolPupils * 100);

                writer.write(
                        "========================= Statistics on the number of pupils by gender =========================");
                writer.newLine();
                writer.write("The number of male pupils is " + malePupilsCount + ".");
                writer.write(" It accounts for " + String.format("%.2f", malePupilsPercentage)
                        + "% of the total school population.");
                writer.newLine();
                writer.write("The number of female pupils is " + femalePupilsCount + ".");
                writer.write(" It accounts for " + String.format("%.2f", femalePupilsPercentage)
                        + "% of the total school population.");
                writer.newLine();
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public static void statisticsOnTheNumberOfPupilsByGrade(PupilManagement pupilManagement) {
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {

                int totalSchoolPupils = pupilManagement.getCurrentIndex();
                writer.write(
                        "========================= Statistics on the number of pupils by grade =========================");
                writer.newLine();
                for (int i = 0; i < 5; i++) {
                    int numberOfPupilInGrade = Grade.getNumberOfPupilsInGrade()[i];
                    Double percentageOfPupilInGrade = 1.0 * numberOfPupilInGrade / totalSchoolPupils * 100;
                    writer.write("The number of pupils in grade " + (i + 1) + " is " + numberOfPupilInGrade + ".");
                    writer.write(" It accounts for " + String.format("%.2f", percentageOfPupilInGrade)
                            + "% of the total school population.");
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public static void statisticsOnTheNumberOfPupilsByPerformances(PupilManagement pupilManagement) {
        File file = new File(Redux.getOutputRelativePath());

        if (file.exists()) {
            // File exists, you can work with it
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Redux.getOutputRelativePath(), true))) {

                int totalSchoolPupils = pupilManagement.getCurrentIndex();
                writer.write(
                        "========================= Statistics on the number of pupils by performance =========================");
                writer.newLine();
                for (int i = 0; i < 5; i++) {
                    int numberOfPupilInGrade = Grade.getNumberOfPupilsInGrade()[i];
                    Double percentageOfPupilInGrade = 1.0 * numberOfPupilInGrade / totalSchoolPupils * 100;
                    writer.write("The number of pupils in grade " + (i + 1) + " is " + numberOfPupilInGrade + ".");
                    writer.write(" It accounts for " + String.format("%.2f", percentageOfPupilInGrade)
                            + "% of the total school population.");
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

}
