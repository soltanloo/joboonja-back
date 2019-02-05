public class Skill{
    String sname;
    int points;

    public Skill(String sname, int points) {
        this.sname = sname;
        this.points = points;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}