package Classes.Pupils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Person.Address;
import Classes.Person.Date;
import Interfaces.ICRUD;
import Interfaces.IFileManagement;

public class PupilManagement implements IFileManagement, ICRUD {
    private Pupil pupilManagement[];
    private int currentIndex;

    public PupilManagement() {
        pupilManagement = new Pupil[100];
        currentIndex = 0;
    }

    @Override
    public void initialize() {
        String filePath = "pupil-management-oop\\src\\main\\java\\Data\\pupils.txt";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String parts[] = line.split("-");
                if (parts.length >= 5) {
                    String pupilID = parts[0];
                    String fullName = parts[1];
                    String dobString = parts[2];
                    String classID = parts[4];

                    String dobParts[] = dobString.split("/");
                    String date = dobParts[0];
                    String month = dobParts[1];
                    String year = dobParts[2];

                    Date dob = new Date(date, month, year);

                    String addressPart = parts[3];
                    String addressRegex = "(\\d+),\\s(.*),\\sPhường\\s(.*),\\sQuận\\s(.*),\\sThành phố\\s(.*$)";
                    Pattern pattern = Pattern.compile(addressRegex);
                    Matcher matcher = pattern.matcher(addressPart);
                    if (matcher.matches()) {
                        String streetNumber = matcher.group(1);
                        String streetName = matcher.group(2);
                        String ward = matcher.group(3);
                        String district = matcher.group(4);
                        String city = matcher.group(5);

                        Address address = new Address(streetNumber, streetName, ward, district, city);
                        Pupil pupil = new Pupil(pupilID, fullName, dob, address);
                        this.add(pupil);
                    } else {
                        System.out.println("Your address is invalid!");
                    }
                } else {
                    System.out.println("Record does not have enough information");
                }
            }
        } catch (IOException e) {
            ((Throwable) e).printStackTrace();
        }
    }

    @Override
    public void display() {
        System.out.println("Pupil Management List:");
        System.out.println("ID\tFullname\t\tBirthDate");
        for (Pupil pupil : pupilManagement) {
            if (pupil != null) {
                System.out.println(pupil.toString());
            }
        }
    }

    @Override
    public void add(Object obj) {
        if (currentIndex < pupilManagement.length) {
            pupilManagement[currentIndex++] = (Pupil) obj;
        } else {
            System.out.println("Pupil Management List is full. Cannot add more.");
        }
    }

    @Override
    public void update(Object obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Object obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
