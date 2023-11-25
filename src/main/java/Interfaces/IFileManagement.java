package Interfaces;

public interface IFileManagement {
    public abstract void initialize();
    public abstract void display();
    // public abstract void insertIntoDatabase(String record);
    // public abstract void updateRecord(String updatedRecord);
    // public abstract void deleteRecord(String record);
    public static void readDatabase() {};
    public static void writeDatabase(String records) {};
}
