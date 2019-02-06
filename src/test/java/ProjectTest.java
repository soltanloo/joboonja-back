import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectTest {
    Project project = new Project();
    @Test
    public void getBudget() {

        int expBudget = 1000;
        project.setBudget(1000);
        int resultBudget = this.project.getBudget();
        assertEquals(expBudget,resultBudget);
    }

    @Test
    public void getTitle() {
        //Project project = new Project();
        String expTitle = "auction";
        project.setTitle("auction");
        int resultTitle = this.project.getTitle();
        assertEquals(expTitle,resultTitle);
    }

    @Test
    public void getBids() {
        //Project project = new Project();
        ArrayList <Bid‌> testBids = new ArrayList <Bid>();
        Bid expBid = new Bid();
        testBids.add(expBid);
        this.project.setBids(testBids);
        int resultBids = this.project.getBids();
        assertEquals(testBids.size(),resultBids.size());
    }

    @Test
    public void setTitle() {
        //Project project = new Project();
        String expTitle = "auction";
        this.project.setTitle(expTitle);
        assertEquals(this.project.getTitle(),expTitle);
    }

    @Test
    public void setBudget() {
        int expBudget = 1000;
        this.project.setBudget(expBudget);
        assertEquals(this.project.getBudget(),expBudget);
    }

    @Test
    public void setSkills() {
        ArrayList <Skill> testSkills = new ArrayList <Skill>();
        Skill expSkill = new Skill();
        testSkills.add(expSkill);
        this.project.setSkills(testSkills);
        assertEquals(this.project.getSkills().size(),testSkills.size());
    }

    @Test
    public void setBids() {
        ArrayList <Bid> testBids = new ArrayList <Bid>();
        Bid expBid = new Bid();
        testBids.add(expBid);
        this.project.setBids(testBids);
        assertEquals(this.project.getBids().size(),testBids.size());
    }

//    @Test
//    public void addBid() {
//    }

    @Test
    public void getSkills() {
        ArrayList <Skill> testSkills = new ArrayList <Skill>();
        Skill expSkill = new Skill();
        testSkills.add(expSkill);
        this.project.setSkills(testSkills);
        int resultSkills = this.project.getSkills();
        assertEquals(testSkills.size(),resultSkills.size());
    }
}