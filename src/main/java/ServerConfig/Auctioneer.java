package ServerConfig;

import Joboonja.Database;
import Joboonja.ProjectManager;
import Models.Project;
import Models.User;

import java.util.ArrayList;

public class Auctioneer implements Runnable {

    @Override
    public void run() {
        ArrayList<Project> unauctionedProjects = Database.projectMapper.getUnauctionedProjects();
        for (Project p:
             unauctionedProjects) {
            User winner = ProjectManager.auction(p);
            Database.projectMapper.setWinner(p.getId(), winner.getId());
        }

    }
}
