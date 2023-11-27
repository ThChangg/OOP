package Classes.Points;

public class Conduct {
    private String rank;
    private int pointConduct;
    private boolean isDeleted=false;

    public Conduct() {
    }

    public Conduct(String rank, int pointConduct) {
        this.rank = rank;
        this.pointConduct = pointConduct;
       
    }

    public Conduct(String rank) {
        this.rank = rank;
    }
    public Conduct(int pointConduct){
        this.pointConduct=pointConduct;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getpointConduct() {
        return pointConduct;
    }

    public void setpointConduct(int pointConduct) {
        this.pointConduct = pointConduct;
    }
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    @Override
    public String toString() {
        return  pointConduct +"\t";
    }

    public  String getConductValue() {
        return pointConduct + "\t";
    }
 
}
