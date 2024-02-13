package az.caspian.core.service;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.internal.ModuleManager;
import az.caspian.core.remote.Project;
import az.caspian.core.remote.Session;
import az.caspian.core.utils.Asserts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ProjectService {
  private static final Logger LOG = LogManager.getLogger(ProjectService.class);

  public void saveProject(Project project) {
    Asserts.required(project, "project is required!");

    /*
        1. Check project name
        2. Check if there is already a project with this name, if no continues, if yes error
        3. Create a folder and put required data into it.
     */
  }

  public void shareProject(String projectName) {
    Asserts.required(projectName, "projectName must not be null or empty!");

    try (Stream<Path> directoryStream = Files.list(FileConstants.APP_PATH)) {
      List<String> projectsName = directoryStream
        .filter(Files::isDirectory)
        .map(Path::getFileName)
        .map(Path::toString)
        .toList();

      if (!projectsName.contains(projectName.toLowerCase())) {
        throw new IllegalArgumentException("Project '" + projectName + "' does not exist!");
      }

      Session.setCurrentProject(projectName);

      //TODO: Check if there is already shared project or not, if user clicks second time.
      Runnable runServerModuleTask = () -> ModuleManager.runServerModule(Map.of("-p", projectName.toLowerCase()));
      new Thread(runServerModuleTask).start();
    } catch (IOException e) {
      LOG.error("Failed to share project");
    }
  }
}
