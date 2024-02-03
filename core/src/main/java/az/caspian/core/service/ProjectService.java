package az.caspian.core.service;

import az.caspian.core.remote.Project;
import az.caspian.core.utils.Asserts;

public class ProjectService {
  public void saveProject(Project project) {
    Asserts.required(project, "project is required!");


  }
}
