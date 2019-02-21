public interface Viewer {
    public String view();
}

public class ProjectViewer implements Viewer{
    @java.lang.Override
    public String view(Project p) {
        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Project</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>id: " + p.getId() + "</li>\n" +
                "        <li>title: " + p.getTitle() + "</li>\n" +
                "        <li>description: " + p.getDescription() + "</li>\n" +
                "        <li>imageUrl: <img src=\"" + p.getImageURL + "\" style=\"width: 50px; height: 50px;\"></li>\n" +
                "        <li>budget: " + p.getBudget() + "</li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>"
        return response;
    }
}

public class UserViewer implements Viewer{
    @java.lang.Override
    public String view(User u){
        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>User</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>id: " + u.getId() + "</li>\n" +
                "        <li>first name: " + u.getFirstName() + "</li>\n" +
                "        <li>last name: " + u.getLastName() + "</li>\n" +
                "        <li>jobTitle: " + u.getJobTitle() + "</li>\n" +
                "        <li>bio: " + u.getBio() + "</li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>"
    }
}

public class ProjectsViewer implements Viewer{
    @java.lang.Override
    public String view(ArrayList<Project> projects){

        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Projects</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        td {\n" +
                "            min-width: 300px;\n" +
                "            margin: 5px 5px 5px 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>id</th>\n" +
                "            <th>title</th>\n" +
                "            <th>budget</th>\n" +
                "        </tr>\n" ;
                for(Project p: projects) {
                    response += "        <tr>\n" +
                            "            <td>" + p.getId() + "</td>\n" +
                            "            <td>" + p.getTitle() + "</td>\n" +
                            "            <td>" + p.getBudegt() + "</td>\n" +
                            "        </tr>\n" ;
                }
                response += "    </table>\n" +
                "</body>\n" +
                "</html>";
    }
}