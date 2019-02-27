import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;

public class JoboonjaContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Manager.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}