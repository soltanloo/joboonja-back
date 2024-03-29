package Models;

import java.util.ArrayList;

public class Skill {
    private int id;
    private String name = "";
    private ArrayList<Integer> endorsers;
    private int point;

    public Skill() {
        this.endorsers = new ArrayList<>();
        this.point = 0;
    }

    public Skill(String name) {
        this.name = name;
        this.endorsers = new ArrayList<>();
        this.point = 0;
    }

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
        this.endorsers = new ArrayList<>();
        this.point = 0;
    }

    public Skill(int id, String name, int point) {
        this.id = id;
        this.name = name;
        this.endorsers = new ArrayList<>();
        this.point = point;
    }

    public Skill(String name, ArrayList<Integer> endorsers) {
        this.name = name;
        this.endorsers = new ArrayList<>();
        this.endorsers = endorsers;
        this.point = endorsers.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<Integer> getEndorsers() {
        return endorsers;
    }

    public void setEndorsers(ArrayList<Integer> endorsers) {
        this.endorsers = endorsers;
    }

    public void addEndorser(Integer id) {
        this.endorsers.add(id);
        this.point++;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }
}