package Classes.Points;

public class Conduct {
    private String rank;
    private int point;

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

    @Override
    public String toString() {
        return rank;
    }

}
