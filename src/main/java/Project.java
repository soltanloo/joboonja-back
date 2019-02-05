
import Skill.java;

public class Project{
    String title;
    int budget;
    ArrayList <Skill> prjSkill = new ArrayList <Skill>();

    public Project(String title, int budget, ArrayList<Skill> prjSkill) {
        this.title = title;
        this.budget = budget;
        this.prjSkill = prjSkill;
    }

    public int getBudget() {
        return budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}