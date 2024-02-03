package az.caspian.core.service;

import az.caspian.core.remote.Project;
import az.caspian.core.utils.Asserts;

public class ProjectService {
  public void saveProject(Project project) {
    Asserts.required(project, "project is required!");
  }

  public void shareProject(String projectName) {
    Asserts.required(projectName, "projectName must not be null or empty!");

    /*
        1. Check the project with this name exists, if yes continue, otherwise exception.
        2. Run Server.java file and pass projectName as a method argument.
     */
  }
}
