package Interfaces;

public interface IFileManagement {
    public abstract void initialize();
    public abstract void display();
    public static void insertIntoDatabase(String record) {};
    public static void updateRecord(String updatedRecord) {};
    public static void deleteRecord(String record) {};
    public static void readDatabase() {};
    public static void writeDatabase(String records) {};
}
