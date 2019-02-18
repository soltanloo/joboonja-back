public class Skill{
    private String name = "";
    private int point = 0;

    public Skill() {}

    public Skill(String name, int point) {
        this.name = name;
        this.point = point;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}