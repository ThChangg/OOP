package Classes.Points;

public class Point {
    private String pointID;
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

    public Point(String pointID, double literaturePoint, double mathPoint, double physicalEducationPoint,
            double englishPoint, Conduct conduct) {
        this.pointID = pointID;
        this.literaturePoint = literaturePoint;
        this.mathPoint = mathPoint;
        this.physicalEducationPoint = physicalEducationPoint;
        this.englishPoint = englishPoint;
        this.conduct = conduct;
        this.status = true;

    }
 

    public String getPointID() {
        return this.pointID;
    }

    public void setPointID(String pointID) {
        this.pointID = pointID;
    }

    public double getLiteraturePoint() {
        return this.literaturePoint;
    }

    public void setLiteraturePoint(double literaturePoint) {
        this.literaturePoint = literaturePoint;
    }

    public double getMathPoint() {
        return this.mathPoint;
    }

    public void setMathPoint(double mathPoint) {
        this.mathPoint = mathPoint;
    }

    public double getPhysicalEducationPoint() {
        return this.physicalEducationPoint;
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
        return this.conduct;
    }

    public void setConduct(Conduct conduct) {
        this.conduct = conduct;
    }

    public String getAcademics() {
        return this.academics;
    }

    public void setAcademics(String academics) {
        this.academics = academics;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getPerformance() {
        return this.performance;
    }
    public static boolean isPoint(double value) {
        return value >= 0 && value <= 10;
    }
   
 

    @Override
    public String toString() {
        return pointID + "-" + literaturePoint + "-" + mathPoint + "-" + physicalEducationPoint + "-" + englishPoint
                + "-" + conduct;
    }

  

   public void calculatePerformance() {
    String academicLevel = calculateAcademicLevel();
    String conductLevel = getConduct().getRank();

    // Combine academic and conduct levels to determine overall performance
    if (academicLevel.equals("Excellent") && conductLevel.equals("Very Good")) {
        setPerformance("Excellent");
    } else if (academicLevel.equals("Excellent") && conductLevel.equals("Good")) {
        setPerformance("Good");
    }
        else if (academicLevel.equals("Good") && conductLevel.equals("Very Good")) {
        setPerformance("Good");
    } else if (academicLevel.equals("Good") && conductLevel.equals("Good")) {
        setPerformance("Good");
    } else if (academicLevel.equals("Good") && conductLevel.equals("Average")) {
        setPerformance("Average");
         } else if (academicLevel.equals("Average") && conductLevel.equals("Very Good")) {
        setPerformance("Average");
        } else if (academicLevel.equals("Average") && conductLevel.equals("Good")) {
        setPerformance("Average");
    } else if (academicLevel.equals("Average") && conductLevel.equals("Average")) {
        setPerformance("Average");
    } else {
        setPerformance("Weak");
    }
}

public String calculateAcademicLevel() {
    

    boolean isExcellent = false;
    boolean isGood = false;
    boolean isAverage = false;
    boolean isWeak = false;

    boolean atLeastOneSubjectAbove8 = getMathPoint() > 8.0 || getLiteraturePoint() > 8.0;
    boolean atLeastOneSubjectAbove6_5 = getMathPoint() > 6.5 || getLiteraturePoint() > 6.5;
    boolean atLeastOneSubjectAbove5_0 = getMathPoint() > 5.0 || getLiteraturePoint() > 5.0;

   
        if (getPhysicalEducationPoint() > 6.5 && getEnglishPoint()>6.5 && getMathPoint()>6.5 && getLiteraturePoint()>6.5) {
            isExcellent = true;
        } else if (getPhysicalEducationPoint() > 5.0 && getEnglishPoint()>5.0 && getMathPoint()>5.0 && getLiteraturePoint()>5.0) {
            isGood = true;
        } else if (getPhysicalEducationPoint() > 3.5 && getEnglishPoint()>3.5  && getMathPoint()>3.5 && getLiteraturePoint()>3.5) {
            isAverage = true;
        } else {
            isWeak = true;
        }
    

    if (isExcellent && atLeastOneSubjectAbove8) {
        return "Excellent";
     } else if (isExcellent && !atLeastOneSubjectAbove8) {
        return "Good";
    } else if (isGood && atLeastOneSubjectAbove6_5) {
        return "Good";
        } else if (isGood && !atLeastOneSubjectAbove6_5) {
        return "Average";
    } else if (isAverage && atLeastOneSubjectAbove5_0) {
        return "Average";
    } else {
        return "Weak";
    }
}
}
