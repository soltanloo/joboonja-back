package DataLayer.DataMappers.Project;

import DataLayer.DBCPDBConnectionPool;
import DataLayer.DataMappers.Mapper;
import Models.Bid;
import Models.Project;
import Models.Skill;

import java.sql.*;
import java.util.ArrayList;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {

    private static final String COLUMNS = " id, lastname, firstname, gpa ";


    public ProjectMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();

        st.executeUpdate("CREATE TABLE IF NOT EXISTS Project" +
                "(id TEXT NOT NULL UNIQUE," +
                "title TEXT," +
                "description TEXT," +
                "imageURL TEXT," +
                "budget INTEGER," +
                "deadline INTEGER," +
                "creationDate INTEGER," +
                "winnerId INTEGER," +
                "FOREIGN KEY(winnerId) REFERENCES User(id)," +
                "PRIMARY KEY(id)" +
                ");");

        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT *" +
                " FROM Project" +
                " WHERE id = ?";
    }

    protected String getFindLastProjectStatement() {
        return "SELECT *" +
                " FROM Project" +
                " ORDER BY creationDate DESC" +
                " LIMIT 1";
    }

    @Override
    public String getCheckExistsStatement() {
        return "SELECT (count(*) > 0) as found FROM Project WHERE id = ?";
    }

    public String getFindAllStatement() {
        return "SELECT * FROM Project ORDER BY creationDate DESC";
    }

    public String getFindPaginatedStatement() {
        return "SELECT * FROM Project ORDER BY creationDate DESC LIMIT ? OFFSET ?";
    }

    public String getFindQueriedStatement(String query) {
        return "SELECT * FROM Project" +
                " WHERE title LIKE '%" + query +
                "%' or description LIKE '%" + query + "%'";
    }

    public String getFindRequiredSkillsStatement() {
        return "SELECT * From ProjectRequirement" +
                " WHERE projectId = ?";
    }

    public String getFindBidsStatement() {
        return "SELECT * From Bid" +
                " WHERE projectId = ?";
    }

    public String getAddStatement() {
        return "INSERT OR IGNORE INTO Project" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String getAddRequirementStatement() {
        return "INSERT OR IGNORE INTO ProjectRequirement" +
                " VALUES (?, ?, ?)";
    }

    public String getAddBidStatement() {
        return "INSERT OR IGNORE INTO Bid" +
                " VALUES (?, ?, ?)";
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Project(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                getRequiredSkills(rs.getString(1)),
                getProjectBids(rs.getString(1)),
                rs.getInt(5),
                rs.getLong( 6),
                rs.getLong(7),
                rs.getInt(8)
        );
    }

    public ArrayList<Skill> getRequiredSkills(String projectId) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindRequiredSkillsStatement())
        ) {
            ResultSet resultSet;
            try {
                st.setString(1, projectId);
                resultSet = st.executeQuery();
                ArrayList<Skill> skills = new ArrayList<>();
                while (resultSet.next()) {
                    Skill skill = new Skill(resultSet.getString(2));
                    skill.setPoint(resultSet.getInt(3));
                    skills.add(skill);
                }
                return skills;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getRequiredSkills query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Project getLastProject() {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindLastProjectStatement())
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getAllUsers query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Bid> getProjectBids(String projectId) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindBidsStatement())
        ) {
            ResultSet resultSet;
            try {
                st.setString(1, projectId);
                resultSet = st.executeQuery();
                ArrayList<Bid> bids = new ArrayList<>();
                while (resultSet.next()) {
                    Bid bid = new Bid(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3)
                    );
                    bids.add(bid);
                }
                return bids;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getProjectBids query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean projectExists(String id) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getCheckExistsStatement())
        ) {
            st.setString(1, id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.projectExists query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addProject(Project project) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddStatement())
        ) {
            st.setString(1, project.getId());
            st.setString(2, project.getTitle());
            st.setString(3, project.getDescription());
            st.setString(4, project.getImageUrl());
            st.setInt(5, project.getBudget());
            st.setLong(6, project.getDeadline());
            st.setLong(7, project.getCreationDate());
            st.setInt(8, project.getWinnerId());
            try {
                st.executeUpdate();
                for (Skill s:
                        project.getSkills()) {
                    addRequirement(s, project.getId());
                }
                for (Bid b:
                        project.getBids()) {
                    addBid(b);
                }
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.addProject query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRequirement(Skill skill, String projectId) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddRequirementStatement())
        ) {
            st.setString(1, projectId);
            st.setString(2, skill.getName());
            st.setInt(3, skill.getPoint());

            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.addRequirement query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBid(Bid b) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddBidStatement())
        ) {
            st.setInt(1, b.getUserId());
            st.setString(2, b.getProjectId());
            st.setInt(3, b.getBidAmount());
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.addBid query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Project> getAllProjects(){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement())
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                ArrayList<Project> projects = new ArrayList<>();
                while (resultSet.next()) {
                    Project project = convertResultSetToDomainModel(resultSet);
                    projects.add(project);
                }
                return projects;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getAllUsers query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Project> getPaginatedProjects(Integer userId, Integer page, Integer size){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindPaginatedStatement())
        ) {
            ResultSet resultSet;
            try {
                st.setInt(1, size);
                st.setInt(2, (page - 1) * size);
                resultSet = st.executeQuery();
                ArrayList<Project> projects = new ArrayList<>();
                while (resultSet.next()) {
                    Project project = convertResultSetToDomainModel(resultSet);
                    projects.add(project);
                }
                return projects;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getAllUsers query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Project> getProjectsByQuery(Integer userId, String query){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindQueriedStatement(query))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                ArrayList<Project> projects = new ArrayList<>();
                while (resultSet.next()) {
                    Project project = convertResultSetToDomainModel(resultSet);
                    projects.add(project);
                }
                return projects;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getAllUsers query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
