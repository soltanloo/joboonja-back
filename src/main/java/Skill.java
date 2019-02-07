public class Skill{
    private String name = "";
    private int points = 0;

    public Skill() {}

    public Skill(String name, int points) {
        this.name = name;
        this.points = points;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}