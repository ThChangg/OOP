package Classes.Points;

public class Point {
    private String pupilID;
    private double literaturePoint;
    private double mathPoint;
    private double physicalEducationPoint;
    private double englishPoint;
    private Conduct conduct;
    private String academics;
    private String performance;
    private boolean status;

    public Point() {
    }

    public Point(String pupilID, double literaturePoint, double mathPoint, double physicalEducationPoint,
            double englishPoint, Conduct conduct) {
        this.pupilID = pupilID;
        this.literaturePoint = literaturePoint;
        this.mathPoint = mathPoint;
        this.physicalEducationPoint = physicalEducationPoint;
        this.englishPoint = englishPoint;
        this.conduct = conduct;
        this.status = true;

    }

    public String getPupilID() {
        return pupilID;
    }

    public void setPupilID(String pupilID) {
        this.pupilID = pupilID;
    }

    public double getLiteraturePoint() {
        return literaturePoint;
    }

    public void setLiteraturePoint(double literaturePoint) {
        this.literaturePoint = literaturePoint;
    }

    public double getMathPoint() {
        return mathPoint;
    }

    public void setMathPoint(double mathPoint) {
        this.mathPoint = mathPoint;
    }

    public double getPhysicalEducationPoint() {
        return physicalEducationPoint;
    }

    public void setPhysicalEducationPoint(double physicalEducationPoint) {
        this.physicalEducationPoint = physicalEducationPoint;
    }

    public double getEnglishPoint() {
        return englishPoint;
    }

    public void setEnglishPoint(double englishPoint) {
        this.englishPoint = englishPoint;
    }

    public Conduct getConduct() {
        return conduct;
    }

    public void setConduct(Conduct conduct) {
        this.conduct = conduct;
    }

    public String getAcademics() {
        return academics;
    }

    public void setAcademics(String academics) {
        this.academics = academics;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getPerformance() {
        return performance;
    }

    @Override
    public String toString() {
        return pupilID + "\t" + literaturePoint + "\t" + mathPoint + "\t" + physicalEducationPoint + "\t" + englishPoint
                + "\t" + conduct;
    }

    public String initialize() {
        return null;
    }

    public void calculatePerformance() {
        String academicLevel = calculateAcademicLevel();
        String conductLevel = getConduct().getRank();

        // Combine academic and conduct levels to determine overall performance
        if (academicLevel.equals("Excellent") && (conductLevel.equals("Good") || conductLevel.equals("Very Good"))) {
            setPerformance("Excellent");
        } else if (academicLevel.equals("Good") && conductLevel.equals("Very Good")) {
            setPerformance("Good");
        } else if (academicLevel.equals("Good") && conductLevel.equals("Good")) {
            setPerformance("Good");
        } else if (academicLevel.equals("Good") && conductLevel.equals("Average")) {
            setPerformance("Average");
        } else {
            setPerformance("Weak");
        }
    }

    private String calculateAcademicLevel() {
        double[] subjectPoints = { getMathPoint(), getLiteraturePoint(), getEnglishPoint(),
                getPhysicalEducationPoint() };

        boolean isExcellent = true;
        boolean isGood = true;

        for (double point : subjectPoints) {
            if (point < 5.0) {
                isGood = false;
            }
            if (point < 8.0) {
                isExcellent = false;
            }
        }

        if (isExcellent) {
            return "Excellent";
        } else if (isGood) {
            return "Good";
        } else {
            return "Weak";
        }
    }
}
