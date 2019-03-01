package Exceptions;

public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}