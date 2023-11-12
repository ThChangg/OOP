package Classes.Points;

public class Conduct {
    private String rank;
    private int point;
    private boolean isDeleted=false;

    public Conduct() {
    }

    public Conduct(String rank, int point) {
        this.rank = rank;
        this.point = point;
    }

    public Conduct(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    @Override
    public String toString() {
        return rank;
    }

}
